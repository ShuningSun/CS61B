package ngordnet.main;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.*;

public class WordNetTest {

    @Test
    public void test_innerJoin_127_to_129() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets14.txt";
        String hyponymFile = "./data/wordnet/hyponyms14.txt";
        Set<String> set1 = new HashSet<>(Arrays.asList("cat", "dog", "rabbit", "racoon"));
        Set<String> set2 = new HashSet<>(Arrays.asList("cat", "dog", "lion", "tiger"));
        Set<String> expected = new HashSet<>(Arrays.asList("cat", "dog"));
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        // Action
        Set<String> actual = wn.innerJoins(set1, set2);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getAllHyponyms_alteration() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets18.txt";
        String hyponymFile = "./data/wordnet/hyponyms18.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        //Set<String> expected = new HashSet<>(Arrays.asList("change", 3, 4, 5, 11, 12, 13, 16, 17, 18));
        Set<String> expected = new HashSet<>(Arrays.asList("change", "alteration", "modification", "adjustment",
                "transition", "increase", "jump", "leap", "saltation", "conversion", "mutation", "type_conversion",
                "evolution", "devolution"));

        // Action
        Set<String> actual = wn.getAllHyponyms("alteration");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getAllHyponyms_bowl() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        List<String> expected = new ArrayList<>(Arrays.asList("Amphitheatrum_Flavium", "Colosseum", "amphitheater",
                "amphitheatre", "arena", "ballpark", "bowl", "bowlful", "bowling_ball", "bullring", "cereal_bowl",
                "circus", "coliseum", "covered_stadium", "dome", "domed_stadium", "finger_bowl", "fish_bowl",
                "fishbowl", "football_stadium", "goldfish_bowl", "hippodrome", "jorum", "mazer", "mixing_bowl",
                "park", "pipe_bowl", "porringer", "punch_bowl", "roll", "salad_bowl", "skybox", "slop_basin",
                "slop_bowl", "soup_bowl", "sports_stadium", "stadium", "toilet_bowl", "trough"));
        Collections.sort(expected);

        // Action
        List<String> actual = wn.handle(Arrays.asList("bowl"));

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_getAllHyponyms_gallery() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        List<String> expected = new ArrayList<>(Arrays.asList("amphitheater", "amphitheatre", "art_gallery",
                "choir_loft", "drift", "gallery", "heading", "lanai", "organ_loft", "picture_gallery", "salon",
                "veranda", "verandah"));
        Collections.sort(expected);

        // Action
        List<String> actual = wn.handle(Arrays.asList("gallery"));

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_handle_bowl_gallery() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        List<String> words = new ArrayList<>(Arrays.asList("occurrence", "change"));
        List<String> expected = new ArrayList<>(Arrays.asList("alteration", "change", "increase", "jump", "leap",
                "modification", "saltation", "transition"));
        Collections.sort(expected);

        // Action
        List<String> actual = wn.handle(words);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void test_handle_energy_light_sparkle() {
        // Arrange
        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        List<String> words = new ArrayList<>(Arrays.asList("energy", "light", "sparkle"));
        List<String> expected = new ArrayList<>(Arrays.asList("light", "scintillation", "spark", "sparkle", "twinkle"));
        Collections.sort(expected);

        // Action
        List<String> actual = wn.handle(words);

        // Assert
        assertEquals(expected, actual);
    }
}
