// Code written by Peter Ye.
import java.util.*;

public class WordTable extends MergeSort {
    HashMap<String, WordEntry> wordEntries = new HashMap<>();
    List<WordEntry> rankedWordEntries = new ArrayList<>();
    private int nlevel;

    WordTable(int nlevel) {
        this.nlevel = nlevel;
    }

    WordEntry findEntry(String key) {
        return wordEntries.get(key);
    }

    WordEntry recordWord(List<String> doNotEditWordFound) {
        // doNotEditWordFound is a sublist of the whole text so I should not edit it.
        List<String> wordFound = new ArrayList<>(doNotEditWordFound);
        // checks if there is already a wordEntry for the word.
        if (findEntry(wordFound.get(0)) != null) {
            String newWord = wordFound.remove(0);
            wordEntries.get(newWord).addWord(wordFound);
            return wordEntries.get(newWord);
        } else {
            String newWord = wordFound.remove(0);
            WordEntry newEntry = new WordEntry(newWord, wordFound, nlevel);
            wordEntries.put(newWord, newEntry);
            return wordEntries.get(newWord);
        }
    }

    void processEntries() {
        // Basically saying only process entries if you have words in your wordEntries. This is the condition to stop the recursion.
        if (wordEntries.size() > 0) {
            int totalWords = 0;
            Iterator wordEntryIterator = wordEntries.values().iterator();
            while (wordEntryIterator.hasNext()) {
                rankedWordEntries.add((WordEntry) wordEntryIterator.next());
                totalWords = totalWords + rankedWordEntries.get(rankedWordEntries.size() - 1).getFrequency();
            }
            rankedWordEntries = mergeSort(rankedWordEntries);
            for (int x = 0; x < rankedWordEntries.size(); x++) {
                rankedWordEntries.get(x).updateProbability(((double) rankedWordEntries.get(x).getFrequency()) / totalWords);
            }
            double cumulativeSum = 0;
            for (int x = 0; x < rankedWordEntries.size(); x++) {
                rankedWordEntries.get(x).updateCumulativeProbability(cumulativeSum + rankedWordEntries.get(x).getProbability());
                cumulativeSum = cumulativeSum + rankedWordEntries.get(x).getProbability();
                // This is the recursive part of the program.
                rankedWordEntries.get(x).returnWordTable().processEntries();
            }
        }
    }

    String displayCommonWords(int numWords, int depthlevel) {
        String commonWords = "";
        String spacing = "";
        for (int x = 0; x < depthlevel; x++) {
            spacing += "    ";
        }
        for (int x = 0; x < numWords && x < rankedWordEntries.size(); x++) {
            commonWords = commonWords + "\n" + spacing + rankedWordEntries.get(x).toString();
        }
        return commonWords;
    }

    WordEntry giveRandomWordEntry() {
        double randomNumber = Math.random();
        WordEntry output;
        for (int y = 0; y < rankedWordEntries.size(); y++) {
            if (rankedWordEntries.get(y).getCumulativeProbability() > randomNumber) {
                output = rankedWordEntries.get(y);
                return output;
            }
        }
        return null;
    }

    String generateRandomText(int lengthOfText) {
        String randomText = "";
        int totalWords = 0;
        LinkedList<String> previousWords = new LinkedList<>();
        WordEntry currentWordEntry = giveRandomWordEntry();
        while (totalWords < lengthOfText) {
            if (previousWords.size() < nlevel) {
                previousWords.add(currentWordEntry.getWord());
                randomText = randomText + " " + previousWords.getLast();
                totalWords++;
                currentWordEntry = currentWordEntry.returnWordTable().giveRandomWordEntry();
            } else {
                if (totalWords % 13 == 0)
                    randomText = randomText + "\n";
                previousWords.removeFirst();
                // Size will be 0 if it is a unigram. Basically the unigram version is going to be a bit different.
                if (previousWords.size() > 0) {
                    currentWordEntry = findEntry(previousWords.get(0));
                    for (int x = 1; x < previousWords.size(); x++) {
                        currentWordEntry = currentWordEntry.returnWordTable().findEntry(previousWords.get(x));
                    }
                    previousWords.add(currentWordEntry.returnWordTable().giveRandomWordEntry().getWord());
                    randomText = randomText + " " + previousWords.getLast();
                    totalWords++;
                } else {
                    previousWords.add(giveRandomWordEntry().getWord());
                    randomText = randomText + " " + previousWords.getLast();
                    totalWords++;
                }
            }
        }
        return randomText;
    }

}