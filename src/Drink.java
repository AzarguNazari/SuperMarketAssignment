

import java.io.Serializable;

public class Drink extends Item implements Serializable{
    private static final long serialVersionUID = 27L;
    public Drink(int itemID, String name, double price, int stock) {
        super(itemID, name, price, stock);
    }
}
