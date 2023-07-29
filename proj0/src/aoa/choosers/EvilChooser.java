package aoa.choosers;

import java.util.*;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;
    public EvilChooser(int wordLength, String dictionaryFile) {
        if(wordLength < 1){
            throw new IllegalArgumentException();
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile,wordLength);
        if(wordPool.size() == 0){
            throw new IllegalStateException();
        }
    }

    @Override
    public int makeGuess(char letter) {
        Map<String,List<String>> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                for(int i = 0;i < o1.length();i++){
                    if(o1.charAt(i) != o2.charAt(i)){
                        if(o2.charAt(i) == '-'){
                            return 1;
                        }
                        if(o1.charAt(i) == '-'){
                            break;
                        }
                        if(o1.charAt(i) > o2.charAt(i)){
                            return 1;
                        }
                    }
                }
                if(o1.equals(o2)){
                    return 0;
                }
                return -1;
            }
        });
        for(int i = 0;i < wordPool.size();i++){
            String str = wordPool.get(i);
            String pa = "";
            for(int j = 0;j < str.length();j++){
                if(str.charAt(j) != letter){
                    if(pattern != null){
                        pa += pattern.charAt(j);
                    }else{
                        pa += '-';
                    }
                }else{
                    pa += letter;
                }
            }
            if(!map.containsKey(pa)){
                List<String> a = new ArrayList<>();
                a.add(wordPool.get(i));
                map.put(pa,a);
            }else{
                map.get(pa).add(wordPool.get(i));
            }
        }
        int[] order = new int[map.size()];
        String[] patterns = new String[map.size()];
        int i = 0;
        for(Map.Entry<String,List<String>> entry : map.entrySet()){
            order[i] = entry.getValue().size();
            patterns[i] = entry.getKey();
            i++;
        }
        for(int m = 0;m < map.size()-1;m++){
            for(int j = 0;j < map.size()-1-m;j++){
                if(order[j] < order[j+1]){
                    int temp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = temp;
                    String tempS = patterns[j];
                    patterns[j] = patterns[j+1];
                    patterns[j+1] = tempS;
                }
            }
        }
        pattern = patterns[0];
        int count = 0;
        for(int m = 0;m < pattern.length();m++){
            if(pattern.charAt(m) == letter){
                count++;
            }
        }
        wordPool = map.get(pattern);
        return count;
    }

    @Override
    public String getPattern() {
        if(pattern == null){
            String str = "-";
            pattern = str.repeat(wordPool.get(0).length());
        }
        return pattern;
    }

    @Override
    public String getWord() {
        int numWords = wordPool.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        String chosenWord = wordPool.get(randomlyChosenWordNumber);
        return chosenWord;
    }
}
