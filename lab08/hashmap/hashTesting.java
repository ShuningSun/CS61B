package hashmap;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class hashTesting {
    MyHashMap<String, Integer> b = new MyHashMap<>();

    @Test
    public void testIter() {
        List<Integer> range100 = IntStream.range(0,50).boxed().collect(Collectors.toList());
        List<Integer> resultList = new ArrayList<>();
        for (int i : range100) {
            b.put("Test" + i, i);
        }
        for (String i : b) {
            resultList.add(b.get(i));
        }
        Collections.sort(resultList);

        resultList.stream().forEach(System.out::println);
    }
}
