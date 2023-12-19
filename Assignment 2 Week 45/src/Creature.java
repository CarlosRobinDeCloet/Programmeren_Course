import java.util.ArrayList;
import java.util.List;

/**
 * This class provides an abstract Creature object. A Creature exists on a Cell, a Creature can also move, act and die.
 * A Creature also has a sight, which influences which surrounding Cells a Creature can see and an initial energy of 20.
 *
 * @author 621810cc Carlos de Cloet
 */

public abstract class Creature
{
	private final int sight;
	private Cell currentCell;
	private int energy;
	public final static int START_ENERGY = 20;

	/**
	 * This constructor takes as input the sight of the Creature. Furthermore, a Creature starts with 20 energy and the
	 * Cell it is inhabiting is set to null.
	 *
	 * @param sight sets the amount of surrounding Cells a Creature can see
	 */

	public Creature(int sight)
	{
		this.sight = sight;
		this.energy = START_ENERGY;
		this.currentCell = null;
	}

	/**
	 * @return the sight of the Creature
	 */

	public final int getSight(){
		return this.sight;
	}

	/**
	 * Checks if the Creature is already on a cell, if this is the case it removes the Creature from its current Cell.
	 * Then it adds the Creature to its new Cell, unless the new Cell is null.
	 *
	 * @param newCell Cell to which the Creature moves
	 */

	public final void moveTo(Cell newCell){

		if(this.isAlive()) {
			this.currentCell.removeCreature(this);
		}
		this.currentCell = newCell;

		if(!(newCell == null)){
			this.currentCell.addCreature(this);
		}
	}

	/**
	 * @return the current Cell the Creature is inhabiting
	 */

	public final Cell getCurrentCell(){
		return this.currentCell;
	}

	/**
	 * Defines an abstract method for a Creature to move. Is implemented by Herbivore and Carnivore.
	 */

	public abstract void move();

	/**
	 * Defines an abstract method for a creature to act. Is implemented by Herbivore and Carnivore.
	 */

	public abstract void act();

	/**
	 * Checks if a Creature is alive, if this is the case it removes the Creature from its current Cell and sets the
	 * current Cell to null.
	 */

	public final void die(){

	if(this.isAlive()) {

		this.currentCell.removeCreature(this);
		this.moveTo(null);
		}
	}

	/**
	 * Checks if a Creature is alive by checking if the current Cell it is inhabiting is null
	 * @return true if the current Cell a creature is on is not null
	 */

	public final boolean isAlive() {
		return !(this.getCurrentCell() == null);
	}

	/**
	 * @return the energy the Creature has
	 */

	public int getEnergy() {
		return this.energy;
	}

	/**
	 * Changes the energy of a Creature. If the energy drops below zero, the Creature dies.
	 *
	 * @param amount gives the amount by which the energy of the Creature is changed
	 */

	public void changeEnergy(int amount){
		this.energy += amount;
		if(this.energy <= 0){
			this.die();
		}
	}

	/**
	 * Gives a list of all Cells which are visible to the Creature, according to its sight,
	 *
	 * @return a list of all visible Cells
	 */

	public final List<Cell> getVisibleCells(){

		ArrayList<Cell> visibleCells = new ArrayList<>();

		// Loops through all the Cells on the World the Creature is on
		for(Cell cell: this.currentCell.getWorld().getCellList()){

			// Calculates the distance by dist(c1,c2) = max(|x_c1 - x_c2|,|y_c1 - y_c2|) and checks if its equal or less than the sight of the Creature
			if(Math.max(Math.abs(this.currentCell.getX() - cell.getX()),Math.abs(this.currentCell.getY() - cell.getY())) <= this.getSight()){
				visibleCells.add(cell);
			}
		}

		return visibleCells;
	}
}