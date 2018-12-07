
import java.util.List;
import java.util.stream.Collectors;

public class Admin extends Person {

    public Admin(String fullname, String username, String email, String password) {
        super(fullname, email, password);
    }

    public void adminPanel(Market market, List<Item> items, List<Customer> customers) {
        
        while (true) {
            System.out.println("------------------------------------");
            System.out.println("|          Admin Panel              |");
            System.out.println("------------------------------------");
            System.out.println("Enter 1: Adding new Item");
            System.out.println("Enter 2: Removing Item");
            System.out.println("Enter 3: Increase Item Stock");
            System.out.println("Enter 4: Sign Out");
            System.out.print("Enter your option: ");
            int option = App.input.nextInt();
            switch (option) {
                case 1:
                    addNewItem(items, market);
                    break;
                case 2:
                    System.out.print("Enter item number: ");
                    int itemId = App.input.nextInt();
                    
                    if(items.removeAll(items.stream().filter(x -> x.itemId == itemId).collect(Collectors.toList()))){
                        System.out.println("Item " + itemId + " is removed");
                    }
                    else{
                        System.out.println("-----------------------");
                        System.out.println("Item " + itemId + " is not in database");
                        System.out.println("-------------------------");
                    }
                    break;
                case 3:
                    System.out.println("---------- Item Quantity Increase -------------");
                    System.out.print("Enter Item Number: ");
                    itemId = App.input.nextInt();
                    if(market.itemSearch.test(itemId)){
                        System.out.print("How many quantity should increase?: ");
                        int quantity = App.input.nextInt();
                        items.stream().filter(x -> x.itemId == itemId).forEach(x -> {
                            x.setQuantity(x.quantity + quantity);
                            System.out.println("******** " + quantity + " " + x.name + " added into stock");
                        });
                    }
                    else{
                        System.out.println("Item " + itemId + " is not in database");
                    }
                    break;
                case 4:
                    return;
            }
        }
    }
    
    private void addNewItem(List<Item> items, Market market){
    while(true){
            System.out.println("----------------------------");
            System.out.println("|       New Item Option     |");
            System.out.println("----------------------------");
            System.out.println("Enter 1: Add new Fruit");
            System.out.println("Enter 2: Add new Bakery");
            System.out.println("Enter 3: Add new Drink");
            System.out.println("Enter 4: Return Back");
            System.out.print("\tEnter option: ");
            int option = App.input.nextInt();
            
            if(option == 4) return;
            
            if(option < 1 || option > 4){
                System.out.println("--------- Wrong Input ---------");
                System.out.println("Please Enter Choose the Correct Option");
                System.out.println("--------------------------------------");   
            }
            else{
                System.out.print("Enter Item ID: ");
                int itemId = App.input.nextInt();
                while(market.itemSearch.test(itemId)){
                    System.out.println("Sorry, " + itemId + " is already used");
                    System.out.print("\tEnter a different ID: ");
                    itemId = App.input.nextInt();
                }
                System.out.print("Enter item name: ");
                App.input.nextLine();
                String itemName = App.input.nextLine();
                System.out.print("Enter item price: ");
                double price = App.input.nextDouble();
                System.out.print("Enter quantity: ");
                int quantity = App.input.nextInt();
                Item temp = null;
                switch(option){
                    case 1:
                        temp = new Fruit(itemId, itemName, price, quantity);
                        break;
                    case 2:
                        temp = new Bakery(itemId, itemName, price, quantity);
                        break;
                    case 3:
                        temp = new Drink(itemId, itemName, price, quantity);
                        break;
                }
                items.add(temp);
                System.out.println("----- New Item Added -----");
                System.out.println(temp);
            }
            
        }
    }
    
}
