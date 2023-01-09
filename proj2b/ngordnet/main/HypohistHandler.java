package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.hugbrowsermagic.NgordnetQuery;

import java.util.Arrays;

public class HypohistHandler extends NgordnetQueryHandler {
    WordNet wn;

    NGramMap ngm;

    HistoryHandler historyHandler;

    HyponymHandler hyponymHandler;

    public HypohistHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
        historyHandler =  new HistoryHandler(ngm);
        hyponymHandler = new HyponymHandler(wn, ngm);
    }

    @Override
    public String handle (NgordnetQuery q) {
        String hyponyms =  hyponymHandler.handle(q);
        hyponyms = hyponyms.substring(1, hyponyms.length() - 1);
        return historyHandler.handle(
                new NgordnetQuery(Arrays.asList(hyponyms.split(",")), q.startYear(), q.endYear(), q.k()));
    }


}
