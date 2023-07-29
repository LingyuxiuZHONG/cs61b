package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> matchW = keepOnlyWordsThatMatchPattern(pattern);
        Map<Character,Integer> map = getFreqMapThatMatchesPattern(matchW);
        for(int i = 0;i < guesses.size();i++){
            map.remove(guesses.get(i));
        }
        if(map.isEmpty()){
            return '?';
        }
        char[] letter = new char[map.size()];
        int[] order = new int[map.size()];
        int m = 0;
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            letter[m] = entry.getKey();
            order[m] = entry.getValue();
            m++;
        }
        for(int i = 0;i < map.size()-1;i++){
            for(int j = 0;j < map.size()-1-i;j++){
                if(order[j] < order[j+1]){
                    int temp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = temp;
                    char tempC = letter[j];
                    letter[j] = letter[j+1];
                    letter[j+1] = tempC;
                }
            }
        }
        return letter[0];
    }
    private Map<Character,Integer> getFreqMapThatMatchesPattern(List<String> matchW){
        Map<Character,Integer> map = new TreeMap<>();
        for(int i = 0;i < matchW.size();i++){
            String a = matchW.get(i);
            for(int j = 0;j < a.length();j++){
                Character ch = a.charAt(j);
                if(map.containsKey(ch)){
                    map.replace(ch,map.get(ch)+1);
                }else{
                    map.put(ch,1);
                }
            }
        }
        return map;
    }
    /** pattern -ood guesses {'d','o'}*/
    private List<String> keepOnlyWordsThatMatchPattern(String pattern){
        List<String> matchW = new ArrayList<>();
        boolean flag = true;
        for(int i = 0;i < words.size();i++){
            if(words.get(i).length() == pattern.length()){
                for(int j = 0;j < pattern.length();j++){
                    if(pattern.charAt(j) != '-' && pattern.charAt(j) != words.get(i).charAt(j)){
                        flag = false;
                    }

                }
                if(flag){
                    matchW.add(words.get(i));
                }
                flag = true;
            }
        }
        return matchW;
    }
    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}