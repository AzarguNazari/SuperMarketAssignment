

import java.io.Serializable;

public class Fruit extends Item implements Serializable{
    private static final long serialVersionUID = 26L;
    public Fruit(int itemID, String name, double price, int stock) {
        super(itemID, name, price, stock);
    }
}
