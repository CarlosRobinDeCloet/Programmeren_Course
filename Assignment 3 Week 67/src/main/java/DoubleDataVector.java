import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class provides a DataVector of type Double, which represent a single vector of data. Data is stored in a
 * linked HashMap. A DataVector can be considered a row or a column DataVector and has a name according to its row index or variable name.
 *
 * @author 621810cc Carlos de Cloet
 */

public class DoubleDataVector implements DataVector<Double>
{

    private Map<String, Double> vector;
    private String name;

    /**
     * Constructs an empty DataVector. Values of the DataVector are added in the DataFrame class using the provided methods.
     */

    public DoubleDataVector(){

        this.vector = null;
    }

    /**
     * sets the vector with all values as the LinkedHashMap that is provided as argument.
     *
     * @param other provided LinkedHashMap which holds all values for the DataVector
     */

    public void setVector(Map<String,Double> other){
        this.vector = other;
    }

    /**
     * Sets the boolean isRow according to whether a DataVector is constructed via row or column data from the DataFrame
     *
     * @param isTrue if it is considered a row vector
     */

    public void setIsRow(boolean isTrue) {
    }

    /**
     * Gives the index number of the row from the DataFrame to the DataVector
     *
     * @param number index number of the row in the DataFrame
     */

    public void setRow(int number){
    }

    /**
     * Gives the name of the column in the DataFrame to the DataVector
     *
     * @param column name of the column in the DataFrame
     */

    public void setColumn(String column) {
    }

    /**
     * @return the name of the DataVector
     */

    public String getName(){

        return this.name;
    }

    /**
     * @return all names of the values of the DataVector via the keys of the LinkedHashMap
     */

    @Override
    public List<String> getEntryNames() {

        List<String> entryNames = new ArrayList<>(this.vector.keySet());
        return entryNames;
    }

    /**
     * Returns the value of the DataVector according to the provided argument.
     *
     * @param entryName the name of the entry to extract
     * @return double value of the LinkedHashMap according to its key
     */

    public Double getValue(String entryName){

       return this.vector.get(entryName);

    }

    /**
      * @return all values of the DataVector
     */


    @Override
    public List<Double> getValues() {

        List<Double> values = new ArrayList<>();
        for(String key: this.vector.keySet()){
                values.add(this.vector.get(key));
        }
        return values;
    }

    /**
      * @return all values of the DataVector, but as a map
     */


    @Override
    public Map<String, Double> asMap() {
        Map<String,Double> copy = null;
        copy = this.vector;
        return copy;
    }

    /**
     * Sets the name of the DataVector
     *
     * @param name the name given to the DataVector
     */

    public void setName(String name){
        this.name = name;
    }

    /**
     * @return the LinkedHashMap which holds all values of the DataVector
     */

    public Map<String, Double> getVector() {
        return vector;
    }

    /**
     * @return A String with all values of the DataVector
     */

    @Override
    public String toString() {
        return "DoubleDataVector{" +
                "vector=" + vector +
                '}';
    }
}


