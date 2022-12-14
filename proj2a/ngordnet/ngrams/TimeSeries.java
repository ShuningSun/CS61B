package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        ts.forEach((k,v) ->
        {
            if (k >= startYear && k <= endYear) {
            this.put(k, v);
            }
        }
        );
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        return new ArrayList<>(this.navigableKeySet());
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {

        return new ArrayList<>(this.values());
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        newTS.putAll(ts);
        this.forEach(
                (key, value) -> newTS.merge(key, value, (v1, v2) ->
                        v1 + v2));
        return newTS;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries newTS = new TimeSeries();
         newTS.putAll(ts);
         this.forEach(
                 (key, value) -> newTS.merge(key, value, (v1, v2) ->
                         v2 / v1));
         return newTS;
    }
}
