import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a Cell object. A cell object is an element of a World object with an x and y coordinate,
 * specifying its location in the world. Additionally, cells contains plants, which can be used by creatures residing on a cell for sustenance. A cell can have
 * a maximum of 100 plants and a minimum of zero plants.
 *
 * @author 621810cc Carlos de Cloet
 */

public class Cell implements Comparable<Cell>
{
    // World components
    private final World world;
    private final int x;
    private final int y;

    // Plant components
    private int plants;
    public static final int MAX_PLANTS = 100;

    // Creature components
    private ArrayList<Creature> creaturesOnCell;

    /**
     * This constructor takes as input a World object which it is an element of and its x and y coordinate in the World.
     * The constructor also creates an empty ArrayList containing Creature objects to keep track of the creatures on the cell.
     *
     * @param w the World the cell is and element of
     * @param x the x coordinate of the cell in its World
     * @param y the y coordinate of the cell in ints World
     */

    public Cell(World w, int x , int y){

        this.world = w;
        this.x = x;
        this.y = y;
        this.creaturesOnCell = new ArrayList<>();
    }

    /**
     * @return the x-coordinate of the cell
     */

    public int getX(){
        return this.x;
    }

    /**
     * @return the y-coordinate of the cell
     */

    public int getY(){
        return this.y;
    }

    /**
     * @return the World the cell is an element of
     */

    public World getWorld(){
        return world;
    }

    /**
     * @return the amount on plants on the cell
     */

    public int getPlants(){
        return plants;
    }

    /**
     * Changes the amount of plants that are on a cell, can't be more than the maximum plants and
     * less than the minimum plants a cell can contain.
     *
     * @param amount gives the amount of plants that is added to the amount of plants in the cell
     * @throws IllegalArgumentException if the amount of plants becomes negative
     */
    public void changePlants(int amount) throws IllegalArgumentException{
        if(this.plants + amount < 0){
            throw new IllegalArgumentException("Amount of plants becomes negative");
        }

        if(this.getPlants() + amount > MAX_PLANTS){
            this.plants = MAX_PLANTS;
        } else {
            this.plants += amount;
        }
    }

    /**
     * Adds a creature to the cell.
     *
     * @param c gives the Creature which is added to the cell
     */

    public void addCreature(Creature c){
        this.creaturesOnCell.add(c);
    }

    /**
     * Removes a creature from the cell
     *
     * @param c gives the Creature which is removed from the cell
     */

    public void removeCreature(Creature c){

        this.creaturesOnCell.remove(c);
    }

    /**
     * Counts the amount of Herbivores that are on a cell by checking if a Creature on the cell is an instance of Herbivore.
     *
     * @return the amount of Herbivores on the cell
     */

    public int countHerbivores(){

        int count = 0;
        for(Creature creature: this.getCreatures()){
            if (creature instanceof Herbivore){
                count ++;
            }
        }

        return count;
    }

    /**
     * @return the size of the biggest Herbivore on the cell
     */

    public int maxSizeHerbivore(){
        int maxSize = 0;

        // Loops through all Creatures on the cell, which are then checked if they are of instance Herbivore

        for(Creature creature: this.getCreatures()){
            if (creature instanceof Herbivore){
                if(((Herbivore) creature).getSize() > maxSize){
                    maxSize = ((Herbivore) creature).getSize();
                }
            }
        }

        return maxSize;
    }

    /**
     * @return a list of all the Creatures on the cell
     */

    public List<Creature> getCreatures(){

        List<Creature> copy = new ArrayList<>(this.creaturesOnCell);
        return copy;
    }

    /**
     * Orders the cells from best to worst, with cells with more plants being better, and if the plants in a cell equal
     * each other the preferred cell is the cell with the least amount of Creatures
     *
     *
     * @param other the other cell that is being compared
     * @return a -1 or 1 depending on if the amounts of plants on a Cell is larger than on other plants.
     * If they equal then it returns -1, 0 or 1 depending on if the amount of Creatures on a Cell is larger than on other cells
     */

    @Override
    public int compareTo(Cell other) {

        if(this.getPlants() != other.getPlants()){
            return other.getPlants() - this.getPlants();
        }

        return this.getCreatures().size() - other.getCreatures().size();
    }
}