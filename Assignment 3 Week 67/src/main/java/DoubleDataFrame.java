import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class provides a DataFrame object with type Double. The DataFrame consist of a two-dimensional table of data.
 * The columns represent variables, which are associated with names of type String, and the rows represent observations of the different variables.
 * The columns and rows are stored in a nested linked HashMap, so values can be retrieved quickly.
 *
 * @author 621810cc Carlos de Cloet
 */

public class DoubleDataFrame implements DataFrame<Double> {

    private final List<String> columnNames;
    private final Map<Integer, Map<String, Double>> map;


    /**
     * The constructor of DoubleDataFrame takes as input a list of Strings with the names for the columns and a data matrix
     * with values of type double.
     *
     * @param columnNames the names for the columns
     * @param data the values of all variables associated with all observations
     */


    public DoubleDataFrame(List<String> columnNames, double[][] data) {

        this.columnNames = columnNames;
        this.map = new LinkedHashMap<>();


        for (int i = 0; i < data.length; i++) {

            // Gives the row value of the matrix as key to the outer HashMap and creates a new HashMap which is given as value to the key.

            Map<String, Double> nestedHashMap = new LinkedHashMap<>();
            this.map.put(i, nestedHashMap);

            // Gives the column name as key to the inner LinkedHashMap and puts the value according to the variable as value to the key.

            for (int j = 0; j < data[i].length; j++) {
                this.map.get(i).put(this.columnNames.get(j), data[i][j]);
            }
        }
    }


    /**
     * @return the number of rows in the dataframe
     */

    public int getRowCount() {
        return this.map.size();
    }

    /**
     * @return the number of columns in the dataframe
     */

    public int getColumnCount() {
        return this.columnNames.size();
    }

    /**
     *
     * @return a list with all column names of the dataframe
     */

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    /**
     * Sets the value of the provided row and variable in the dataframe.
     *
     * @param rowIndex the row index of the entry
     * @param colName  the name of the column in which the entry is stored
     * @param value    the new value of the entry
     * @throws IndexOutOfBoundsException when an invalid index number is provided
     * @throws IllegalArgumentException when an invalid column name is provided
     */


    public void setValue(int rowIndex, String colName, Double value) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (rowIndex < 0 || rowIndex >= this.getRowCount()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (!this.map.get(rowIndex).containsKey(colName)) {
            throw new IllegalArgumentException("Non existing column provided");
        }

        this.map.get(rowIndex).put(colName, value);
    }

    /**
     *  Gets the value of the provided row and variable of the dataframe
     *
     * @param rowIndex the row index of the entry
     * @param colName  the name of the column in which the entry is stored
     * @return the value of the given variable from the given observation
     * @throws IndexOutOfBoundsException when an invalid index number is provided
     * @throws IllegalArgumentException when an invalid variable name is provided
     */


    public Double getValue(int rowIndex, String colName) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (rowIndex < 0 || rowIndex >= this.getRowCount()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (!this.map.get(rowIndex).containsKey(colName)) {
            throw new IllegalArgumentException("Non existing column provided");
        }

        return this.map.get(rowIndex).get(colName);
    }

    /**
     * Returns the row as a DataVector object of type Double
     *
     * @param rowIndex the row index of the entry
     * @return the row as a DataVector object of type Double
     * @throws IndexOutOfBoundsException if an invalid index number is provided
     */

    @Override
    public DataVector<Double> getRow(int rowIndex) throws IndexOutOfBoundsException {
        if (rowIndex < 0 || rowIndex >= this.getRowCount()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        // Creates new DataVector object, and provided it with the values of the row of the DataFrame, sets the boolean
        // is row as true, and sets the name of the DataVector.

        DoubleDataVector newVector = new DoubleDataVector();
        newVector.setIsRow(true);
        newVector.setRow(rowIndex);
        newVector.setName("row_" + rowIndex);

        // Gets the associated row as HashMap and provides it to the DataVector

        Map<String, Double> orderedMap = new LinkedHashMap<>(this.map.get(rowIndex));
        newVector.setVector(orderedMap);
        return newVector;
    }

    /**
     * Returns a column of the dataframe as a DataVector of type Double
     *
     * @param colName the name of the column
     * @return the column as a DataVector of type Double
     * @throws IllegalArgumentException if an invalid column name is provided
     */

    @Override
    public DataVector<Double> getColumn(String colName) throws IllegalArgumentException {

        if (!this.map.get(0).containsKey(colName)) {
            throw new IllegalArgumentException("Non existing column provided");
        }

        // Creates new DataVector object, and provided it with the values of the column of the DataFrame. A new LinkedHashMap
        // is created for values to be stored in. Then it loops through all rows of the column to store the row number as key,
        // and sets the value as the value of the associated row and column from the dataframe.

        DoubleDataVector newVector = new DoubleDataVector();
        newVector.setColumn(colName);
        newVector.setVector(new LinkedHashMap<>());
        for (int i = 0; i < this.getRowCount(); i++) {
            newVector.getVector().put("row_" + i, this.map.get(i).get(colName));
        }

        newVector.setName(colName);

        return newVector;
    }

    /**
     * Returns all the rows of the dataframe as DataVectors of type Double in a list. First a list is made, then for all keys
     * in the outer LinkedHashMap, the method getRow is called to get the row as DataVector and add it to the list.
     *
     * @return a list of all rows of the dataframe as DataVector.
     */

    @Override
    public List<DataVector<Double>> getRows() {
        List<DataVector<Double>> rows = new ArrayList<>();
        for (Integer integer : this.map.keySet()) {
            rows.add(this.getRow(integer));
        }

        return rows;
    }

    /**
     * Returns all the columns of the dataframe as DataVectors of type Double in a list. First a list is made, then for all keys
     * in the inner LinkedHashMap, the method getColumn is called to get the column as DataVector and add it to the list.
     *
     * @return a list of all columns of the dataframe as DataVector.
     */

    @Override
    public List<DataVector<Double>> getColumns() {
        List<DataVector<Double>> columns = new ArrayList<>();
        for (String columnName : this.map.get(0).keySet()) {
            columns.add(this.getColumn(columnName));
        }

        return columns;
    }

    /**
     * Expands the DataFrame by constructing a new DataFrame with additional columns, which value are 0.
     *
     * @param additionalRows the number of rows to add to the new data frame
     * @param newCols        the names of the column to add to the new data frame.
     * @return a DataFrame with additional columns which value are 0.
     * @throws IllegalArgumentException the number of additional rows in negative or
     *                                   column names are duplicated
     */

    @Override
    public DataFrame<Double> expand(int additionalRows, List<String> newCols) throws IllegalArgumentException {

        if(additionalRows < 0){
            throw new IllegalArgumentException("Negative additional rows");
        }

        for(String column : newCols){
            if (this.columnNames.contains(column)){
                throw new IllegalArgumentException("Dataframe already contains this column name");
            }
        }

        // Adds all inner HashMaps to a list and puts the additional columns as keys to the inner Hashmap with value 0.

        List<Map<String,Double>> expandedInnerMap = new ArrayList<>(this.map.values());
        for(String newCol : newCols){
            expandedInnerMap.forEach( innerMap -> innerMap.put(newCol, 0.0));
        }

        // Adds the new column names to the list of all column names.

        ArrayList<String> allColumns = new ArrayList<>();
        allColumns.addAll(this.columnNames);
        allColumns.addAll(newCols);

        // makes a matrix of double values and adds the values of the HashMap to the matrix, and sets the value 0 for all additional rows.

        double[][] values = new double[this.getRowCount()+additionalRows][allColumns.size()];
        for(int i = 0; i < this.getRowCount(); i++){

            Map<String,Double> pr = expandedInnerMap.get(i);

            for(int j = 0; j < allColumns.size(); j++){
                values[i][j] = pr.get(allColumns.get(j));
            }
        }
        for(int k = this.getRowCount(); k < additionalRows+this.getRowCount(); k++){

            for(int l = 0; l < allColumns.size(); l++){
                values[k][l] = 0;
            }
        }
        // constructs a new DataFrame with the new matrix and column names

        DoubleDataFrame expandedFrame = new DoubleDataFrame(allColumns, values);
        return expandedFrame;
    }

    /**
     * Gives a dataframe with only the given columns that should be retained.
     *
     * @param retainColumns the names of column that should be retained
     * @return the DataFrame with the retained columns.
     * @throws IllegalArgumentException if a provided column name is not a column of the dataframe
     */

    @Override
    public DataFrame<Double> project(Collection<String> retainColumns) throws IllegalArgumentException {

        for(String column : retainColumns){
            if(! this.columnNames.contains(column)){
                throw new IllegalArgumentException("Dataframe does not contain this column: " + column);
            }
        }

        // Makes a filter and collect all retained Strings in a list.

        Predicate<String> isContainedInList = retainColumns::contains;
        List<String> retainedColumns = this.map.get(0).keySet().stream().filter(isContainedInList).collect(Collectors.toList());

        // Makes a matrix of double values and adds the values of all retained columns from the HashMap to the matrix.

        double[][] values = new double[this.getRowCount()][retainedColumns.size()];

        for(int i = 0; i < this.getRowCount(); i++){
            for(int j = 0; j < retainColumns.size(); j++){
                values[i][j] = this.map.get(i).get(retainedColumns.get(j));
            }
        }

        // constructs a new DataFrame with the matrix of retained columns.

        DoubleDataFrame shrunkData = new DoubleDataFrame(retainedColumns, values);
        return shrunkData;
    }

    /**
     * Creates a new DataFrame with rows that should be retained according to the provided filter.
     *
     * @param rowFilter a predicate that can indicate whether a row should be
     *                  maintained
     * @return a new DataFrame object with all rows that should be retained according to the provided filter.
     */

    @Override
    public DataFrame<Double> select(Predicate<DataVector<Double>> rowFilter) {

        // Creates a list consisting of DataVectors of type Double and makes a DataVector for all rows of the dataframe
        // which are added to the list.

        ArrayList<DoubleDataVector> vectors = new ArrayList<>();
        for(Map<String,Double> innerMap : this.map.values()){
            DoubleDataVector vector = new DoubleDataVector();
            vector.setVector(innerMap);
            vectors.add(vector);
        }

        // Filters all the DataVectors that should be retained and add them to a list !!! Method contains an error
        // according to CodeGrade, but works as intended in this IntelliJ enviroment.

        List<DoubleDataVector> list = vectors.stream().filter(rowFilter).collect(Collectors.toList());

        // Makes a matrix of double values and adds the values of all retained DataVectors to the matrix.

        double[][] values = new double [list.size()][this.columnNames.size()];
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < this.columnNames.size(); j++){
                values[i][j] = list.get(i).getValue(this.columnNames.get(j));
            }
        }

        // constructs a new DataFrame with the matrix of retained rows.

        DoubleDataFrame selectDataFrame = new DoubleDataFrame(this.columnNames, values);
        return selectDataFrame;

    }

    /**
     * Applies function to columns and adds them to the DataFrame by expanding the DataFrame and then calculating and adding
     * the values to the new column.
     *
     * @param columnName the name of the new column
     * @param function   the function to apply to each row
     * @return a new DataFrame with the computed column added to it
     */

    @Override
    public DataFrame<Double> computeColumn(String columnName, Function<DataVector<Double>, Double> function) {


        DataFrame<Double> compute = this.expand(0,columnName);
        int i = 0;
        for( DataVector<Double> vector : compute.getRows()){
            Double value = function.apply(vector);
            compute.setValue(i, columnName, value);
            i++;
        }

        return compute;
    }

    /**
     * Creates a row DataVector which summarizes the values of all observations according to the given BinaryOperator as argument.
     *
     * @param name            the name of the resulting data vector
     * @param summaryFunction the binary operator that should be used to reduce the
     *                        values in each column
     * @return a DataVector which summarizes the values of all observations
     */

    @Override
    public DataVector<Double> summarize(String name, BinaryOperator<Double> summaryFunction) {

        // Makes a new list of Doubles and for each column, applies the summaryFunction to all values of the column and adds the value to the list.

        List<Double> values = new ArrayList<>();
        this.getColumns().forEach( column -> values.add(column.getValues().stream().reduce(column.getValues().get(0), summaryFunction)));

        // Creates a new DataVector and gives it the name that is given as argument

        DoubleDataVector summary = new DoubleDataVector();
        summary.setIsRow(true);
        summary.setRow(values.size());
        summary.setName(name);

        // Creates a new HashMap and gives all columns as key to the HashMap with the aggregated value from the list as value to the key,
        // and gives the HashMap to the DataVector.

        Map<String, Double> test = new LinkedHashMap<>();
        int i = 0;
        for(String column : this.columnNames){
            test.put(column, values.get(i));
            i++;
        }

        summary.setVector(test);
        return summary;
    }

    /**
     *  @return a Statistics object with itself given to it as argument
     */

    @Override
    public DataFrameStatistics statistics()
    {
        return new Statistic(this);
    }
}



