import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the world for the creatures to live in. A world is a 2-dimensional object with cells
 * consisting of cells. The world has a width and a height
 *
 * @author 621810cc Carlos de Cloet
 */

public class World
{

    private final int height;
    private final int width;
    private final Cell[][] grid;

    /**
     * This constructor takes as input a width and height, and creates cells according to this input.
     * The cells are stored in a Cell list with 2 inputs, namely for the x-coordinate and y-coordinate for the cell
     *
     *
     * @param width the width of the world
     * @param height the height of the world
     */

    public World(int width, int height){

        this.height = height;
        this.width = width;

        // Creates a list of size width and height
        this.grid = new Cell[width][height];

        // Constructs the cells according to their x and y coordinates
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Cell c = new Cell(this, x, y);
                this.grid[x][y] = c;
            }
        }

    }

    /**
     * @return the height of the world
     */

    public int getHeight() {
        return height;
    }

    /**
     * @return the width of the world
     */

    public int getWidth() {
        return width;
    }

    /**
     * Gives the cell according to the input coordinates
     *
     * @param x is the x-coordinate of the cell which is being searched for
     * @param y is the y-coordinate of the cell which is being searched for
     * @return the cell which relates to the given coordinates
     * @throws IllegalArgumentException when the given coordinates are not the coordinates of a cell
     */

    public Cell getCell(int x, int y) throws IllegalArgumentException{

        if(x<0 || x >= this.width || y < 0 || y >= this.height){
            throw new IllegalArgumentException("Not a valid coordinate");
        }
        return this.grid[x][y];
    }

    /**
     * @return a list which contains all the cells in the world
     */

    public List<Cell> getCellList(){

        // creates an empty ArrayList to which the cells in the world are added
        ArrayList<Cell> listOfCells = new ArrayList<>();

        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){

                listOfCells.add(this.grid[x][y]);
            }
        }

        return listOfCells;
    }

    /**
     * @return a list of all the creatures in the world
     */

    public List<Creature> getCreatures(){

        ArrayList<Creature> creaturesOnWorld = new ArrayList<>();

        // Loops through all cells to get the creatures on the cell, and adds them to the list of creatures on the world
        for (Cell cell: this.getCellList()){
            for(Creature creature: cell.getCreatures()){
                creaturesOnWorld.add(creature);
            }
        }

        return creaturesOnWorld;
    }
}