/**
 * @author 621810cc
 *
 * Creates a class FruitBasket which has a budget, prices for apples and oranges and the amount of apples and oranges
 * stored in the FruitBasket. Provides methods for buying apples and oranges.
 */


public class FruitBasket {
    private int budget;
    private int amountApples;
    private int amountOranges;
    private int applePrice;
    private int orangePrice;

    /**
     * Provides the constructor to create objects of the class FruitBasket. The value of the amount of oranges and
     * apples starts at 0.
     *
     * @param budget sets the budget that can be spent on the fruit basket
     * @param applePrice sets the price of apples
     * @param orangePrice sets the price of oranges
     */

    public FruitBasket(int budget, int applePrice, int orangePrice) {

        this.budget = budget;
        this.applePrice = applePrice;
        this.orangePrice = orangePrice;

    }

    /**
     * Checks whether there is enough budget to buy an apple. If this is the case, an apple is bought and the amount
     * of apples in the fruit basket is updated.
     *
     * @return true if an apple has been bought, false if an apple couldn't be bought.
     */

    public boolean buyApple() {
        if (this.applePrice <= this.budget) {

            this.budget -= this.applePrice;
            amountApples++;

            return true;
        }

        return false;
    }

    /**
     * Checks whether there is enough budget to buy an orange. If this is the case, an orange is bought and the amount
     * of oranges in the fruit basket is updated.
     *
     * @return true if an orange has been bought, false if an orange couldn't be bought.
     */

    public boolean buyOrange() {

        if (this.orangePrice <= this.budget) {

            this.budget -= this.orangePrice;
            this.amountOranges++;

            return true;
        }

        return false;
    }

    /**
     *
     * @return returns a text with information on the status of the budget that is left and the amount of apples
     *         and oranges in the fruit basket.
     */

    public String getStatus() {

        return "Budget: " + this.budget + " Apples: " + this.amountApples + "  Oranges: " + this.amountOranges;

    }

    /**
     *
     * @return returns the amount of apples that has been bought
     */

    public int getAmountApples() {

        return amountApples;
    }

}
