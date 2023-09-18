package cz.cvut.fel.pjv.engine.model.object.entity.possession;

/**
 * Handles actions with coins.
 * Coins are accepted in the shop.
 */
public class Coins {
    private int availableCoins;

    public Coins(int availableCoins) {
        this.availableCoins = availableCoins;
    }

    /**
     * Increases the number of coins.
     *
     * @param income Coins to add.
     */
    public void earnCoins(int income) {
        availableCoins += income;
    }

    /**
     * Decreases the number of coins.
     *
     * @param cost Coins for subtraction.
     */
    public void spendCoins(int cost) {
        availableCoins -= cost;
    }

    /**
     * Checks whether there are enough coins to make a purchase.
     *
     * @param cost Cost of the product in the shop.
     */
    public boolean isEnough(int cost) {
        return cost <= availableCoins;
    }

    /**
     * @return Number of available coins.
     */
    public int getAvailableCoins() {
        return availableCoins;
    }
}
