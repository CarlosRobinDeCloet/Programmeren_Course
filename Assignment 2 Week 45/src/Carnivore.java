/**
 * This class provides the object Carnivore, which is an extension of Creature. Carnivores have an initial energy of 30
 * and a sight of 2. Carnivores eat Herbivores as sustenance.
 *
 * @author 621810cc Carlos de Cloet
 */

public class Carnivore extends Creature
{

    private final static int CARNIVORE_SIGHT = 2;
    private final static int CARNIVORE_ENERGY = 10;


    /**
     * This constructor first calls the super constructor with the sight of a Carnivore as input. Then it changes its
     * energy with 10 to give the Carnivore an initial energy of 30.
     */
    public Carnivore(){

        super(CARNIVORE_SIGHT);
        this.changeEnergy(CARNIVORE_ENERGY);
    }

    /**
     * Moves the Carnivore to the best Cell in its sight, which is the Cell with the most Herbivores. If two Cells have
     * the most Herbivores, it moves to the cell with the Herbivore with the biggest size.
     */

    public void move(){
        if(this.isAlive()){

            // Calls the findBestCell() method to find the best Cell to move towards
            Cell moveToThisCell = this.findBestCell();

            if(moveToThisCell != null) {
                this.moveTo(moveToThisCell);
            }

        }
    }

    /**
     * Makes the Carnivore eat the largest Herbivore that is inhabiting the same Cell. The sustenance the Herbivore provides
     * to the Carnivore equals the energy the eaten Herbivore has.
     */

    public void act(){

        Creature toEat = null;
        int maxSizeOfHerb = 0;

        // Loops through all Creatures on the cell. Checks if a Creature is an instance of Herbivore, if that is the case it looks for the biggest Herbivore
        for(Creature creature: this.getCurrentCell().getCreatures()){
            if( creature instanceof Herbivore){
                if(((Herbivore) creature).getSize() > maxSizeOfHerb){
                    toEat = creature;
                    maxSizeOfHerb = ((Herbivore) creature).getSize();
                }
            }
        }

        // If the largest Herbivore is found, its energy is transferred and the eaten Herbivore dies
        if(toEat != null){
            this.changeEnergy(toEat.getEnergy());
            toEat.die();
        }

        // Metabolism
        this.changeEnergy(-6);
    }

    /**
     * Finds the best Cell for the Carnivore to move to, which is the Cell with the most Herbivores. If two cells both
     * have the most Herbivores, moves to the cell with the largest Herbivore.
     *
     * @return the Cell which has the most Herbivores, or if both Cell have the most Herbivores the Cell which also has the largest Herbivore
     */

    public Cell findBestCell(){

        // Loops through all Cells within sight, and calculates the amount of Herbivores and largest Herbivore of each Cell
        for( Cell cell: this.getVisibleCells()){
            cell.countHerbivores();
            cell.maxSizeHerbivore();
        }

        Cell moveToThisCell = null;
        int herbsOnCell = 0;
        int maxSizeOfHerb = 0;

        // Loops through all Cells to find the Cells with the most herbivores
        for( Cell cell: this.getVisibleCells()){

            // If a Cell has more Herbivores than previously found, updates the Cell to move towards as the current Cell
            // and updates the most Herbivores and largest Herbivore already encountered
            if( cell.countHerbivores() > herbsOnCell){

                herbsOnCell = cell.countHerbivores();
                maxSizeOfHerb = cell.maxSizeHerbivore();
                moveToThisCell = cell;
            }

            // If a Cell has equal amount of Herbivores, checks if the Cell contains a larger Herbivore. If this is the case
            // it updates the Cell to move towards as the Current Cell and updates the largest Herbivore already encountered
            if( cell.countHerbivores() == herbsOnCell){
                if(cell.maxSizeHerbivore() > maxSizeOfHerb){

                    maxSizeOfHerb = cell.maxSizeHerbivore();
                    moveToThisCell = cell;
                }
            }
        }

        return moveToThisCell;
    }
}