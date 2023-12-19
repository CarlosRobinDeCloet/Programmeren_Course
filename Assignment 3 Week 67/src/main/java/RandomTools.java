import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.util.List;

/**
 * This class provides tools to create a dataframe with random generated values
 *
 * @author 621810cc Carlos de Cloet
 */

public class RandomTools
{
    private RealDistribution dist;

    /**
     * Adds a distribution to the class, by which random data can be generated
     *
     * @param dist distribution given to the tool
     */

    public RandomTools(RealDistribution dist){

        this.dist = dist;
    }

    /**
     * Generates values according to the distribution of the random tool, and creates a DataFrame using the random generated values
     *
     * @param seed seed used to generate random data
     * @param rows amount of rows the dataframe should have
     * @param colNames list of all names of the columns
     * @return DataFrame with random generated data
     */

    public DataFrame<Double> generate(long seed, int rows, List<String> colNames){

        this.dist.reseedRandomGenerator(seed);
        double[][] values = new double[rows][colNames.size()];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < colNames.size(); j++){

                values[i][j] = this.dist.sample();
            }
        }

        DoubleDataFrame df = new DoubleDataFrame(colNames,values);
        return df;
     }

     /**
     * Provides an uniform distribution
     *
     * @param lb lower bound of the uniform distribution
     * @param ub upper bound of the uniform distribution
     * @return the uniform distribution with lower and upper bound
     */

     public static RandomTools uniform(double lb, double ub){

        UniformRealDistribution uni = new UniformRealDistribution(lb,ub);
        RandomTools uniformDist = new RandomTools(uni);
        return uniformDist;
     }

     /**
     * Provides a normal distribution
     *
     * @param mu mean given to the normal distribution
     * @param sigma variance given to the normal distribution
     * @return the normal distribution with mean and variance
     */

     public static RandomTools gaussian(double mu, double sigma){

         NormalDistribution gaussian = new NormalDistribution(mu,sigma);
         RandomTools gaussianDist = new RandomTools(gaussian);
         return gaussianDist;
     }

     /**
     * Provides an exponential distribution
     *
     * @param mean mean given to the exponential distribution
     * @return the exponential distribution with mean
     */

     public static RandomTools exponential(double mean){

         ExponentialDistribution exp = new ExponentialDistribution(mean);
         RandomTools expDist = new RandomTools(exp);
         return expDist;
     }


}