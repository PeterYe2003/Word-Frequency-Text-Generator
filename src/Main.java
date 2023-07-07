// I used stack overflow for decimal format and I also talked with Seth about this project. Code written by Peter Ye.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int n = 4;
        ArrayList<String> allWords = new ArrayList<>();
        WordTable bigWordTable = new WordTable(n);
        try {
            FileReader file = new FileReader("TextFiles/Great Gatsby.txt");
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null) {
                // do something with the line
                for (String word : line.split(" ")) {
                    word = word.trim();
                    if (word.length() > 0) {
                        allWords.add(word);
                    }
                }
                line = reader.readLine();
            }

        } catch (IOException ioexception) {
            System.out.println("Ack!  We had a problem: " + ioexception.getMessage());
        }
        for (int x = 0; x < allWords.size() - n; x++) {
            // Creates n-length subLists.
            bigWordTable.recordWord(allWords.subList(x, x + n));
        }
        bigWordTable.processEntries();
        System.out.println(bigWordTable.displayCommonWords(4, 0));
        System.out.println(bigWordTable.generateRandomText(100));
    }
}