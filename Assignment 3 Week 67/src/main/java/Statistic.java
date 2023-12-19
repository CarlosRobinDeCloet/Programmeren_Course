import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.inference.TTest;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * This class provides a statistical tool which can compare DataVectors and return the p-values according to the Student T test
 * and the pearsons correlation
 *
 * @author 621810cc Carlos de Cloet
 */



public class Statistic implements DataFrameStatistics {

    private final DataFrame<Double> df;
    private final TTest t;

    /**
     * Constructor for the statistical tool. Gives a Ttest class to the statistical tool.
     *
     * @param df Dataframe which holds the data we want to use statistical tests on.
     */

    public Statistic(DataFrame<Double> df) {

        this.df = df;
        this.t = new TTest();
    }

    /** Performs an unpaired T test that tells if the observed values are different from the given mean
     *
     * @param var the variable for which the test statistic will be computed
     * @param mu  the mean to compare against
     * @return the p-value of the test
     */

    public double tTest(String var, double mu){

        DataVector<Double> test = df.getColumn(var);
        double[] observation = test.getValues().stream().mapToDouble(d -> d).toArray();
        return t.tTest(mu, observation);
    }

    /**
     * Performs an unpaired T test that test if the observed values from the colums differ from each other
     * @param var1 the name of the first column to compare
     * @param var2 the name of the second column to compare
     * @return the p-value of the test
     */

    public double tTest(String var1, String var2){

        DataVector<Double> test1 = df.getColumn(var1);
        DataVector<Double> test2 = df.getColumn(var2);

        double[] observations1 = test1.getValues().stream().mapToDouble(d -> d).toArray();
        double[] observations2 = test2.getValues().stream().mapToDouble(d -> d).toArray();

        return t.tTest(observations1,observations2);
    }

    /**
     * Calculates the pearson correlation between the columns
     *
     * @param var1 the name of the first column
     * @param var2 the name of the second column
     * @return the pearson correlation between the columns
     */

    public double pearsonsCorrelation(String var1, String var2){

        DataVector<Double> test1 = df.getColumn(var1);
        DataVector<Double> test2 = df.getColumn(var2);

        double[] observations1 = test1.getValues().stream().mapToDouble(d -> d).toArray();
        double[] observations2 = test2.getValues().stream().mapToDouble(d -> d).toArray();

        PearsonsCorrelation pc = new PearsonsCorrelation();
        return pc.correlation(observations1, observations2);
    }

    /**
     * Gives a number of statistics for the provided column
     * @param var the name of the column for which the descriptive statistics must
     *            be computed
     * @return a number of statistics for the provided column
     */

    public DescriptiveStatistics describe(String var){

        DataVector<Double> test = df.getColumn(var);
        double[] observation = test.getValues().stream().mapToDouble(d -> d).toArray();
        return new DescriptiveStatistics(observation);
    }

    /**
     * Estimates a linear model with a single dependent variable and multiple independent variables.
     *
     * @param dep   the dependent variable
     * @param indep a list of independent variables
     * @return a Map with values of the estimated coefficients
     */

    @Override
    public Map<String, Double> estimateLinearModel(String dep, List<String> indep) {

        // Initializes data gathered from the dataframe using datavectors.

        DataVector<Double> depVector = df.getColumn(dep);
        double[] depData = depVector.getValues().stream().mapToDouble(d -> d).toArray();

        List<DataVector<Double>> indeps = new ArrayList<>();
        indep.forEach(x -> indeps.add(df.getColumn(x)));

        // makes a new matrix for values of the independent columns and puts the values in the matrix

        double[][] indepData = new double[indeps.get(0).getValues().size()][indep.size()];

        for(int j = 0; j < indeps.size(); j++){
            List<Double> valuesOfColumn = indeps.get(j).getValues();

            for(int i = 0; i < indeps.get(0).getValues().size(); i++){
                indepData[i][j] = valuesOfColumn.get(i);
            }
        }

        // Creates a new OLSMultipleLinearRegression object and gives it the sample data. Estimates the coefficients and
        // puts it in a map.

        OLSMultipleLinearRegression lr = new OLSMultipleLinearRegression();
        lr.newSampleData(depData, indepData);

        double[] regressionParameters = lr.estimateRegressionParameters();
        Map<String, Double> coefficients = new LinkedHashMap<>();
        coefficients.put("intercept",regressionParameters[0]);

        int k =1;
        for(String column: indep){
            coefficients.put(column, regressionParameters[k]);
            k++;
        }

        return coefficients;
    }
}
