


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Purchase implements Serializable{
    private static final long serialVersionUID = 30L;
    private final ArrayList<Item> items;
    private final LocalDate purchaseDate;
    
    public Purchase(){
        items = new ArrayList<>();
        purchaseDate = LocalDate.now();
    }
    
    public LocalDate getDate(){
        return purchaseDate;
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public void invoice(){
        System.out.println();
        System.out.println("\t------------------- Invoice ----------------------");
        double total = 0;
        total = items.stream().map((i) -> {
            System.out.println(i);
            return i;
        }).map((i) -> i.getPrice()).reduce(total, (a, b) -> a + b);
        System.out.println("\t--------------------------------------------------");
        System.out.println("\tTotal              : " + total + "\n");
    }
    
    public ArrayList<Item> getItems(){
        return (ArrayList<Item>) items.clone();
    }
    
    @Override
    public String toString(){
        StringBuilder temp = new StringBuilder();
        temp.append("------- Purchase on ").append(this.purchaseDate.toString()).append("\n");
        double total = 0;
        total  = items.stream().map(x -> {
            temp.append(x);
            return x;
        }).map(i -> i.price).reduce(total, (a, b) -> a + b);
        temp.append("\n\t\t\tTotal Cost: ").append(total);
        return temp.toString();
    }
}
