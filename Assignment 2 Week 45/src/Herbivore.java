import java.util.Collections;
import java.util.List;

/**
 * This class provides the object Herbivore, which is an extension of Creature. A Herbivore has a size, sight of 1, and
 * eats plants as sustenance.
 *
 * @author 621810cc Carlos de Cloet
 */

public class Herbivore extends Creature
{
    private final int size;
    private final static int HERBIVORE_SIGHT = 1;

    /**
     * This constructor takes as input the size of the Herbivore. It calls the Creature constructor with the sight of a
     * Herbivore, then it gives the Herbivore its size.
     *
     * @param size the size the Herbivore has
     * @throws IllegalArgumentException if the size is non-positive
     */

    public Herbivore(int size) throws IllegalArgumentException{

        super(HERBIVORE_SIGHT);

        if(size < 1){
            throw new IllegalArgumentException("Size is not positive");
        }

        this.size = size;
    }

    /**
     * @return the size of the Herbivore
     */

    public int getSize() {
        return size;
    }

    /**
     *  Moves the Herbivore to the best cell in his sight, which is the cell with the most plants. If two cells both
     *  have the most plants, moves to the cell with the least Creatures.
     */

    public void move(){

        if(this.isAlive()) {

            List<Cell> cells = this.getVisibleCells();

            // Natural order of the Cells align with the preference of the Herbivore. Cells are sorted from best to worst.
            Collections.sort(cells);
            this.moveTo(cells.get(0));
        }
    }

    /**
     *  Makes the Herbivore eat plants for sustenance on the Cell it's standing on.
     */

    public void act(){
        if(this.isAlive()){

            // Herbivore eats 2*size/(1+size) amount of plants. If less plants are available on the Cell, it eats the amount of plants on the Cell.
            int amountEaten = Math.min(this.getCurrentCell().getPlants(), 2*this.getSize()*this.getSize()/(1+this.getSize()));
            this.changeEnergy(amountEaten);
            this.getCurrentCell().changePlants(-1 * amountEaten);

            // Metabolism
            this.changeEnergy(-1 * this.size);
        }
    }
}