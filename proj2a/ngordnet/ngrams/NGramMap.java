package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private Map<String,TimeSeries> words = new HashMap<>();
    private TimeSeries counts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(wordsFilename));
        String line;
        while((line = reader.readLine()) != null){
            String[] a = line.split("\t");
            int year = Integer.parseInt(a[1]);
            double times = Double.parseDouble(a[2]);
            if(words.containsKey(a[0])){
                words.get(a[0]).put(year,times);
            }else{
                TimeSeries ts = new TimeSeries();
                ts.put(year,times);
                words.put(a[0],ts);
            }
        }
        reader = new BufferedReader(new FileReader(countsFilename));
        while((line = reader.readLine()) != null){
            String[] items = line.split(",");
            counts.put(Integer.parseInt(items[0]),Double.parseDouble(items[1]));
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries(words.get(word),startYear,endYear);
        return ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = new TimeSeries(words.get(word),MIN_YEAR,MAX_YEAR);
        return ts;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries(counts,MIN_YEAR,MAX_YEAR);
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries(words.get(word),startYear,endYear);
        List<Double> values = ts.data();
        TimeSeries sum = totalCountHistory();
        for(Map.Entry<Integer,Double> entry : ts.entrySet()){
            ts.put(entry.getKey(),entry.getValue() / sum.get(entry.getKey()));
        }
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = new TimeSeries(words.get(word),MIN_YEAR,MAX_YEAR);
        List<Double> values = ts.data();
        TimeSeries sum = totalCountHistory();
        for(Map.Entry<Integer,Double> entry : ts.entrySet()){
            ts.put(entry.getKey(),entry.getValue() / sum.get(entry.getKey()));
        }
        return ts;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        TimeSeries sum = totalCountHistory();
        for(String word : words){
            ts = new TimeSeries(this.words.get(word),startYear,endYear).plus(ts);
        }
        for(Map.Entry<Integer,Double> entry : ts.entrySet()){
            ts.put(entry.getKey(),entry.getValue() / sum.get(entry.getKey()));
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for(String word : words){
            ts = this.words.get(word).plus(ts);
        }
        return ts;
    }

}
