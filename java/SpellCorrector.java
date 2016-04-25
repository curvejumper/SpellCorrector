
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SpellCorrector {
    // TODO(student): Feel free define private variables here to store whatever
    // you trained.

    private final HashMap<String, Integer> dictionary = new HashMap<>();

    SpellCorrector() {
        // TODO(student): Contructor to initialize your data-structure
    }

    public void train(final String word) {
        // TODO(student): Implement your own logic to model spell corrector using
        // edit distance. This method will be called for every possible known valid
        // word from corpus. Learn your model from this.
        dictionary.put(word, dictionary.containsKey(word) ? dictionary.get(word) + 1 : 1);
    }

    public String correct(final String mispelled_word) {
        // TODO(student): Use the model learnt in above method and return correct
        // spelling for given word. Given a mispelled word, return your best
        // predicted corrected word.
        if (dictionary.containsKey(mispelled_word)) {
            return mispelled_word;
        }
        ArrayList<String> list = edits(mispelled_word);
        HashMap<Integer, String> candidates = new HashMap<>();
        for (String s : list) {
            if (dictionary.containsKey(s)) {
                candidates.put(dictionary.get(s), s);
            }
        }
        if (candidates.size() > 0) {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        for (String s : list) {
            for (String w : edits(s)) {
                if (dictionary.containsKey(w)) {
                    candidates.put(dictionary.get(w), w);
                }
            }
        }
        return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : mispelled_word;
    }

    private ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < word.length(); ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
            }
        }
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    // TODO(student): Feel free to define private utility methods here.
}
