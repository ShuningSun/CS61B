package ngordnet.main;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class GraphTest {


    @Test
    public void test_synSet() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets14.txt";
        String hyponymFile = "./data/wordnet/hyponyms14.txt";
        List<String> expected = Arrays.asList("happening", "occurrence", "occurrent" , "natural_event");

        // Action
        Graph underTest = new Graph(synsetFile, hyponymFile);
        List<String> actual = underTest.synSet.get(1);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_hyponySet() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";
        List<Integer> expected = Arrays.asList(12, 13);

        // Action
        Graph underTest = new Graph(synsetFile, hyponymFile);
        List<Integer> actual = underTest.hyponySet.get(11);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_wordSet() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets14.txt";
        String hyponymFile = "./data/wordnet/hyponyms14.txt";
        List<Integer> expected = Arrays.asList(2, 8);

        // Action
        Graph underTest = new Graph(synsetFile, hyponymFile);
        List<Integer> actual = underTest.wordSet.get("change");

        // Assert
        assertEquals(expected, actual);
    }


}
