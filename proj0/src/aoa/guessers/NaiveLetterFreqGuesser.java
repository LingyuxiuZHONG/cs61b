package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b!
     *  words = [ally, beta, cool, deal, else, flew, good, hope, ibex]
     *  map = {a: 3, b: 2, c: 1, d: 2, e: 7, f: 1, g: 1, h: 1, i: 1, l: 6,
     *        o: 5, p: 1, s: 1, t: 1, w: 1, x: 1, y: 1} */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character,Integer> map = new TreeMap<>();
        for(String word : this.words){
            for(int i = 0;i < word.length();i++){
                Character a = word.charAt(i);
                if(!map.containsKey(a)){
                    map.put(a,1);
                }else{
                    map.replace(a,map.get(a)+1);
                }
            }
        }
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character,Integer> map = getFrequencyMap();
        for (Character guess : guesses){
            map.remove(guess);
        }
        int[] order = new int[map.size()];
        char[] letter = new char[map.size()];
        int i = 0;
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            order[i] = entry.getValue();
            letter[i] = entry.getKey();
            i++;
        }
        for(int a = 0;a < map.size() - 1;a++){
            for(int j = 0;j < map.size()-1-a;j++){
                if(order[j] < order[j+1]){
                    int temp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = temp;
                    char temp_c = letter[j];
                    letter[j] = letter[j+1];
                    letter[j+1] = temp_c;
                }
            }
        }
        if(map != null){
            return letter[0];
        }
        return '?';

    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
