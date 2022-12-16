package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;


public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String s : words) {
            response += s + ": "+ ngm.weightHistory(s, startYear, endYear) + "\n";
        }
        return response;
    }

    public HistoryTextHandler(NGramMap ngm) {
        super();
        this.ngm = ngm;
    }

}
