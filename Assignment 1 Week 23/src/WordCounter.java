/**
 * This class counts how many times a focus word appears in a document and how many words there are total in a document.
 * It can differentiate between documents classified as spam and as not spam.
 *
 * @author 621810cc Carlos de Cloet
 */

public class WordCounter {

    private String focusWord;
    private int amountWordsInSpam;
    private int amountWordsInNoSpam;
    private int amountFocusWordInSpam;
    private int amountFocusWordInNoSpam;

    /**
     * This constructor stores the focus word which needs to be counted.
     * By default, the amount of words counted is zero.
     *
     * @param focusWord certain word that the word counter counts how many times it appears in documents
     */

    public WordCounter(String focusWord){

        this.focusWord = focusWord;
    }

    /**
     * Provides the focus word of the word counter, that was provided during the creation of this word counter object.
     *
     * @return the focus word of the word counter.
     */

    public String getFocusWord(){
        return this.focusWord;
    }

    /**
     * Processes a document that has been given as a string. Counts how many words and focus words appear in the document,
     * and checks whether a document is classified as spam or not. Updates the amount of words and focus words the word
     * counter has seen in spam and not spam documents.
     *
     * @param document provides the document that needs to be processed by the method.
     */

    public void addSample(String document){

        String[] input = document.split(" ");

        int amountWords = input.length - 1;
        int amountFocusWords = 0;

        for(int i = 1; i < input.length; i++){
            if(input[i].equals(this.focusWord)){
                amountFocusWords ++;
            }
        }

        // checks if document is classified as spam
        if(input[0].equals("0")){
            this.amountWordsInNoSpam += amountWords;
            this.amountFocusWordInNoSpam += amountFocusWords;
        } else {
            this.amountWordsInSpam += amountWords;
            this.amountFocusWordInSpam += amountFocusWords;
        }
    }

    /**
     * Checks if a word counter has seen enough spam documents and not spam documents to be considered properly trained.
     *
     * @return a boolean value whether the word counter has seen enough spam and not spam documents to be considered trained.
     */

    public boolean isCounterTrained(){
        return this.amountWordsInSpam > 0 && this.amountWordsInNoSpam > 0 &&
                (this.amountFocusWordInSpam > 0 || this.amountFocusWordInNoSpam > 0)
        ;
    }

    /**
     * estimates the probability that a word is the focus word, conditional on the fact that the document is classified
     * as not spam.
     *
     * @return the estimate of the conditional probability of a word being the focus word
     * @throws IllegalStateException if the word counter has not been properly trained
     */

    public double getConditionalNoSpam() throws IllegalStateException
    {
        if(!(isCounterTrained())){
            throw new IllegalStateException("WordCounter has not been trained");
        }

        return (double) this.amountFocusWordInNoSpam / this.amountWordsInNoSpam;
    }

    /**
     * estimates the probability that a word is the focus word, conditional on the fact that the document is classified
     * as spam.
     *
     * @return the estimate of the conditional probability of a word being the focus word
     * @throws IllegalStateException if the word counter has not been properly trained
     */

    public double getConditionalSpam() throws IllegalStateException
    {
        if(!(isCounterTrained())){
            throw new IllegalStateException("WordCounter has not been trained");
        }
        return (double) this.amountFocusWordInSpam / this.amountWordsInSpam;
    }
}