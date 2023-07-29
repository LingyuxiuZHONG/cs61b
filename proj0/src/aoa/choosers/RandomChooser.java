package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;


import java.util.ArrayList;
import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if(wordLength < 1){
            throw new IllegalArgumentException();
        }
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile,wordLength);
        if(words.size() == 0){
            throw new IllegalStateException();
        }
        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
        String str = "-";
        pattern = str.repeat(chosenWord.length());
    }

    @Override
    public int makeGuess(char letter) {
        int count = 0;
        for(int i = 0;i < chosenWord.length();i++){
            if(chosenWord.charAt(i) == letter){
                count++;
                pattern = pattern.substring(0,i) + letter + pattern.substring(i+1);
            }
        }
        return count;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
