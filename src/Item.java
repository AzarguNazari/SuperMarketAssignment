


import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable, Comparable<Item>{
    
    private static final long serialVersionUID = 23L;
    protected int itemId;
    protected  String name;
    protected double price;
    protected int quantity;

    public Item(int itemId, String name, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

    @Override
    public boolean equals(Object o1) {
        return this.hashCode() == ((Item) o1).hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.itemId;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 17 * hash + this.quantity;
        return hash;
    }

    @Override
    public String toString() {
        return String.format("\t%-20s:%-20d\n", "Item ID", itemId) + 
               String.format("\t%-20s:%-20s\n", "Item Name", name) + 
               String.format("\t%-20s:%-20.2f\n", "Item Price", price) + 
               String.format("\t%-20s:%-20d\n", "Item Quantity", quantity); 
    }

    @Override
    public int compareTo(Item o) {
        return (int) (this.getPrice() - o.getPrice());
    }
}
