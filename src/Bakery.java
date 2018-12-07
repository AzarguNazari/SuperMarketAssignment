import java.io.Serializable;

public class Bakery extends Item implements Serializable{
    private static final long serialVersionUID = 28L;
    public Bakery(int itemID, String name, double price, int stock) {
        super(itemID, name, price, stock);
    }
}
