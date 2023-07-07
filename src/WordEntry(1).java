// Code written by Peter Ye.
import java.text.DecimalFormat;
import java.util.List;

public class WordEntry implements Comparable {

    private String word;
    private int frequency = 0;
    private double probability;
    private double cumulativeProbability;
    private WordTable wordTable;
    private int depthLevel;
    // df is for sigfigs. I don't want a super long double for my cumulative probability so I'm cutting it off.
    private DecimalFormat df;


    WordEntry(String word, List<String> nextWords, int nlevel) {
        df = new DecimalFormat("#.###");
        this.word = word;
        // DepthLevel tells how much to indent. I chose to make each wordEntry calculate their own depthLevel instead of directly passing in the depthLevel(I think passing in depthLevel could also work).
        depthLevel = nlevel - nextWords.size();
        wordTable = new WordTable(nlevel);
        addWord(nextWords);

    }

    void addWord(List<String> nextWords) {
        frequency++;
        if (nextWords.size() > 0) {
            wordTable.recordWord(nextWords);
        }
    }


    int getFrequency() {
        return frequency;
    }

    String getWord() {
        return word;
    }

    void updateProbability(double newProbability) {
        probability = newProbability;
    }

    double getProbability() {
        return probability;
    }

    double getCumulativeProbability() {
        return cumulativeProbability;
    }

    void updateCumulativeProbability(double newCumulativeProbability) {
        cumulativeProbability = newCumulativeProbability;
    }

    boolean hasWordTable(){
        return wordTable.rankedWordEntries.size()>0;
    }

    WordTable returnWordTable(){
        return wordTable;
    }


    @Override
    public int compareTo(Object o) {
        return ((WordEntry) o).getFrequency() - frequency;
    }

    @Override
    public String toString() {
        if (wordTable.rankedWordEntries.size() > 0) {
            return
                    "word='" + word + '\'' +
                    ", frequency=" + frequency +
                    ", cumulativeProb=" + df.format(cumulativeProbability) +
                    wordTable.displayCommonWords(4, depthLevel);
        }
        else{
            return "word='" + word + '\'' +
                    ", frequency=" + frequency +
                    ", cumulativeProb=" + df.format(cumulativeProbability);
        }
    }
}
