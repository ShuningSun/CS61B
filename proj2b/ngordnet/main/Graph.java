package ngordnet.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.TimeSeries;


public class Graph {
    HashMap<Integer, List<String>> synSet;

    HashMap<String, List<Integer>> wordSet;
    HashMap<Integer, List<Integer>> hyponySet;
    public Graph(String synsetFile, String hyponymFile) {
        synSet = new HashMap<>();
        hyponySet = new HashMap<>();
        wordSet = new HashMap<>();
        synsetsReadIn(synsetFile);
        hyponySet(hyponymFile);
    }

    private void synsetsReadIn(String synsetFile) {
        In synInput = new In(synsetFile);
        while (synInput.hasNextLine()) {
            String holder = synInput.readLine();
            String[] splited = holder.split(",");
            int id = Integer.parseInt(splited[0]);
            List<String> nouns = Arrays.asList(splited[1].split("\\s+"));
            synSet.put(id, nouns);
            for (String s : nouns) {
                wordSet.computeIfAbsent(s, v -> new ArrayList<>(List.of(id))).add(id);
            }
        }
    }
    private void hyponySet(String hyponymFile) {
        In hyponyInput = new In(hyponymFile);
        while (hyponyInput.hasNextLine()) {
            String holder = hyponyInput.readLine();
            String[] splited = holder.split(",");
            int id = Integer.parseInt(splited[0]);
            List<Integer> hyponyms =
                    Arrays.stream(splited).map(Integer::valueOf).skip(1).collect(Collectors.toList());
            hyponySet.computeIfAbsent(id, v ->hyponyms).addAll(hyponyms);
        }
    }
}
