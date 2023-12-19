import java.io.*;
import java.util.ArrayList;

/**
 * This class provides a machine learning tool to calculate the probability that a document should be considered as
 * spam or not. The machine learning tool can be trained by providing documents which it can read. When the tool has
 * been trained it can classify new documents as spam or not by comparing the conditional probabilities if the document
 * is considered as spam or not spam.
 *
 * @author 621810cc Carlos de Cloet
 */

public class NaiveBayes {

    private ArrayList<WordCounter> listOfSpamWords;
    private int numberOfSpamDocuments;
    private int numberOfNoSpamDocuments;

    /**
     * This constructor creates an ArrayList. Then it takes a list of strings with focus words and creates new
     * WordCounter objects with the focus words, which the constructor stores in the ArrayList.
     * By default, the number of spam and not spam documents the objects has seen is zero.
     *
     * @param focusWords a list of certain words of interest that appear a lot in spam documents.
     */

    public NaiveBayes(String[] focusWords){

        this.listOfSpamWords = new ArrayList<>();

        for(String focusWord : focusWords){
            WordCounter wc = new WordCounter(focusWord);
            this.listOfSpamWords.add(wc);
        }
    }

    /**
     * Checks whether a document is considered as spam or not. Loops through all the WordCounter stores in the ArrayList
     * to check whether the words in the document appear as focus words of the WordCounters, by making use of WordCounter
     * methods.
     *
     * @param document provides the document that needs to be processed.
     */

    public void addSample(String document){

        String[] wordsInDocument = document.split(" ");

        // checks if document is classified as spam
        if(wordsInDocument[0].equals("0")){

            this.numberOfNoSpamDocuments ++;
        } else {
            this.numberOfSpamDocuments ++;
        }

        for (WordCounter spamWord: this.listOfSpamWords){
            spamWord.addSample(document);
        }
    }

    /**
     * Classifies a document that has not been classified as spam or not spam. Does so by calculating the probabilities
     * of a document being spam or not, conditional on the fact if a document is spam or not, and comparing the
     * probabilities.
     *
     * @param unclassifiedDocument provides a document that has not been classified yet
     * @return boolean value whether the probability that a document is not spam is smaller than that the document is spam
     * @throws IllegalArgumentException if the NaiveBayes object has not been trained
     */

    public boolean classify(String unclassifiedDocument) throws IllegalArgumentException{
        if(this.numberOfSpamDocuments == 0 || this.numberOfNoSpamDocuments == 0){
            throw new IllegalArgumentException("Has not been trained");
        }


        double spamScore = (double) this.numberOfSpamDocuments / (this.numberOfSpamDocuments + this.numberOfNoSpamDocuments);
        double noSpamScore = (double) this.numberOfNoSpamDocuments / (this.numberOfSpamDocuments + this.numberOfNoSpamDocuments);

        String[] wordsInUnclassifiedDocument = unclassifiedDocument.split(" ");
        for(String word : wordsInUnclassifiedDocument){
            for(WordCounter wordCounter : this.listOfSpamWords){
                if(word.equals(wordCounter.getFocusWord())){
                    spamScore = spamScore * wordCounter.getConditionalSpam();
                    noSpamScore = noSpamScore * wordCounter.getConditionalNoSpam();
                }
            }
        }

        return noSpamScore < spamScore;
    }

    /**
     * Trains the NaiveBayes object with a file, by reading all the lines of a file, considering them as documents
     * and processing the documents with the addSample method.
     *
     * @param trainingFile provides a file with documents which can be processed by the addSample method
     * @throws IOException when a file is not readable or not found
     */

    public void trainClassifier(File trainingFile) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(trainingFile)))
        {
            String line = reader.readLine();

            while(line != null){
                this.addSample(line);
                line = reader.readLine();
            }
        }
    }

    /**
     * Classifies a file, by reading a file, considering all lines as unclassified documents, classifying the documents
     * with the classify method and writing a new file with all now classified documents as lines in the new file.
     *
     * @param input file with unclassified documents
     * @param output file with classified documents
     * @throws IOException when a file is not readable or found
     */

    public void classifyFile(File input, File output) throws IOException{

        try(BufferedReader reader = new BufferedReader(new FileReader(input));PrintWriter printer = new PrintWriter(output))
        {
            String line = reader.readLine();

            while(line != null){
                if(this.classify(line)){
                    printer.println("1");
                } else {
                    printer.println("0");
                }

                line = reader.readLine();
            }
        }
    }

    /**
     * Checks the accuracy of the computations of the NaiveBayes object by providing a training set and splitting it
     * between a training and testing part. First it trains the NaiveBayes object then it estimates whether documents
     * should be classified as spam or not. Then it checks the estimated values with the true values, and creates a
     * ConfusionMatrix object with the true negatives and positives and false negatives and positives.
     *
     * @param testdata provides a file with classified documents
     * @return a ConfusionMatrix which holds the values of the true negatives/positives and false negatives/positives
     * @throws IOException if a file cannot be read or be found
     */

    public ConfusionMatrix computeAccuracy(File testdata) throws IOException{

        int trueNegative = 0;
        int truePositive = 0;
        int falseNegative = 0;
        int falsePositive = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(testdata)))
        {


            String line = reader.readLine();

            while(line != null){

                boolean prediction = this.classify(line);
                String[] wordsInDocument = line.split(" ");
                String classification = wordsInDocument[0];

                if(prediction){

                    if(classification.equals("1")){
                        truePositive ++;
                    } else {
                        falsePositive ++;
                    }
                }

                if(!(prediction)){
                    if(classification.equals("1")){
                        falseNegative ++;
                    } else {
                        trueNegative ++;
                    }
                }

                line = reader.readLine();
            }
        }

        ConfusionMatrix cm = new ConfusionMatrix(trueNegative, truePositive, falseNegative, falsePositive);

        return cm;
    }
}