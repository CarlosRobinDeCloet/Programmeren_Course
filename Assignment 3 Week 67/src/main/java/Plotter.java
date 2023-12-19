import org.knowm.xchart.XYChart;

/**
 * Constructs plots for the dataframe
 *
 * @author 621810cc Carlos de Cloet
 */

public class Plotter implements DataFramePlotting {

    private DataFrame<Double> df;

    public Plotter(DataFrame<Double> df){

        this.df = df;

    }

    @Override
    public XYChart scatter(String title, String xVar, String yVar) {

        XYChart chart = new XYChart(df.getColumn(xVar).getValues().size(), df.getColumn(yVar).getValues().size());

        double[] xVariables = df.getColumn(xVar).getValues().stream().mapToDouble(d -> d).toArray();
        double[] yVariables = df.getColumn(yVar).getValues().stream().mapToDouble(d -> d).toArray();

        chart.addSeries(title, xVariables, yVariables);
        chart.setXAxisTitle( xVar);
        chart.setYAxisTitle( yVar);
        return null;
    }
}
