
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SpellCorrector {
    // TODO(student): Feel free define private variables here to store whatever
    // you trained.

    private final HashMap<String, Integer> dictionary;

    SpellCorrector() {
        dictionary = new HashMap<>();
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
        HashMap<Integer, String> possibleEdits = new HashMap<>();
        for (String s : list) {
            if (dictionary.containsKey(s)) {
                //Put string from dictionary into the value section
                //Insert the dictionary value into the key section
//                System.out.println("Here is the possible value: " + s);
                possibleEdits.put(dictionary.get(s), s);
            }
        }
        if (possibleEdits.size() > 0) {
            //Return the higest key size. That will be the most likely edit
            return possibleEdits.get(Collections.max(possibleEdits.keySet()));
        }
        for (String s : list) {
            for (String w : edits(s)) {
                if (dictionary.containsKey(w)) {
                    possibleEdits.put(dictionary.get(w), w);
                }
            }
        }
        return possibleEdits.size() > 0 ? possibleEdits.get(Collections.max(possibleEdits.keySet())) : mispelled_word;
    }

    private ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < word.length(); ++i) {
            //adds substrings of the word **will not work well, gives word a
            //a chance to be many other, incorrect, choices
            //result.add(word.substring(0, i));
            
            //Removes single characters to see if that is a popular word
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; ++i) {
            //Removes two characters
//            result.add(word.substring(0, i) + word.substring(i + 2));

            //Switches two characters to see if more popular word
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        //replaces a letter **making it look more like another word, thus, increases incorrectness
//        for (int i = 0; i < word.length(); i++) {
//            for (char c = 'a'; c <= 'z'; c++) {
//                    result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
//            }
//        }
        //adds an extra letter
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; c++) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    // TODO(student): Feel free to define private utility methods here.
}
