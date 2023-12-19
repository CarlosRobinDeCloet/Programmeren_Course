/**
 * This class provides a matrix with the values of the true negatives and positives and false negatives and positives
 * a NaiveBayes object gives.
 *
 * @author 621810cc Carlos de Cloet
 */

public class ConfusionMatrix {

    private int trueNegative;
    private int falseNegative;
    private int truePositive;
    private int falsePositive;

    /**
     * This constructor takes the parameter values given to the constructor, and sets the values of the instance
     * variables to the values given to the according parameter of the constructor.
     *
     * @param trueNegative provides the amount of true negatives that the NaiveBayes object calculates
     * @param truePositive provides the amount of true positives that the NaiveBayes object calculates
     * @param falseNegative provides the amount of false negatives that the NaiveBayes object calculates
     * @param falsePositive provides the amount of false positives that the NaiveBayes object calculates
     */

    public ConfusionMatrix(int trueNegative, int truePositive, int falseNegative, int falsePositive){

        this.falseNegative = falseNegative;
        this.falsePositive = falsePositive;
        this.trueNegative = trueNegative;
        this.truePositive = truePositive;

    }

    /**
     * Returns the amount of false negatives the ConfusionMatrix holds.
     *
     * @return the value of the false negatives the ConfusionMatrix holds
     */

    public int getFalseNegatives() {
        return falseNegative;
    }

    /**
     * Returns the amount of false positives the ConfusionMatrix holds.
     *
     * @return the value of the false positives the ConfusionMatrix holds
     */

    public int getFalsePositives() {
        return falsePositive;
    }

    /**
     * Returns the amount of true negatives the ConfusionMatrix holds.
     *
     * @return the value of the true negatives the ConfusionMatrix holds
     */


    public int getTrueNegatives() {
        return trueNegative;
    }

    /**
     * Returns the amount of true positives the ConfusionMatrix holds.
     *
     * @return the value of the true positives the ConfusionMatrix holds
     */

    public int getTruePositives() {
        return truePositive;
    }
}
