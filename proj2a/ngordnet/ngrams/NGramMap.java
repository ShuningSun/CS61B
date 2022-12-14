package ngordnet.ngrams;

import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> wordData;
    TimeSeries countData;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordData  = new HashMap<> ();
        countData = new TimeSeries();
        wordReadIn(wordsFilename);
        countsReadIn(countsFilename);
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        TimeSeries newTS = new TimeSeries();
        newTS.putAll(wordData.get(word));
        return newTS;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = wordData.get(word);
        return new TimeSeries(ts, startYear, endYear);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries newTS = new TimeSeries();
        newTS.putAll(countData);
        return newTS;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        return wordData.get(word).dividedBy(totalCountHistory());
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return new TimeSeries(weightHistory(word), startYear, endYear);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries SWH = null;
        for (String i : words) {
            SWH = SWH == null ? weightHistory(i) : weightHistory(i).plus(SWH);
        }
        return SWH;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        TimeSeries SWH = null;
        for (String i : words) {
            SWH = SWH == null ? weightHistory(i, startYear, endYear) : weightHistory(i, startYear, endYear).plus(SWH);
        }
        return SWH;
    }

    private void wordReadIn(String wordsFilename) {
        In wordInput = new In(wordsFilename);
        while (wordInput.hasNextLine()) {
            String holder = wordInput.readLine();
            String[] splited = holder.split("\\s+");
            String word = splited[0];
            int year = Integer.parseInt(splited[1]);
            double counts = Double.parseDouble(splited[2]);

            if (wordData.containsKey(word)) {
                wordData.get(word).put(year, counts);
            }
            else {
                wordData.put(word, new TimeSeries());
                wordData.get(word).put(year, counts);
            }

        }
    }

    private void countsReadIn(String countsFilename) {
        In countsInput = new In(countsFilename);
        while (countsInput.hasNextLine()) {
            String holder = countsInput.readLine();
            String[] splited = holder.split(",");
            int year = Integer.parseInt(splited[0]);
            double counts = Double.parseDouble(splited[1]);
            countData.put(year, counts);
        }
    }


}
