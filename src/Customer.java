


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Customer extends Person implements Serializable{

    private static final long serialVersionUID = 24L;
    private final List<Purchase> purchases = new ArrayList<>();

    public Customer(String fullname, String email, String password) {
        super(fullname, email, password);
    }

    public void displayHistory(){
        
    }
    
    public void purchase(List<Item> items){
        App.input.nextLine();
        Purchase tempPur = new Purchase();
        outerLoop:
        while(true){
            System.out.println();
            System.out.println("\t<><><><><><><><><><><><><><>>");
            System.out.println("\t<><><>    Purchase   <><><><>");
            System.out.println("\t<><><><><><><><><><><><><><>>");
            System.out.println("\tEnter 1: Add Item to Basket: ");
            System.out.println("\tEnter 2: Back\n");
            System.out.print("\tEnter Your Option: ");
            int option = App.input.nextInt();
            switch(option){
                case 1:
                    System.out.print("\t\tEnter Item ID to purchase: ");
                    int itemID = App.input.nextInt();
                    Item tempItem = findItem(items, itemID);
                    
                    if(tempItem != null){
                            System.out.print("\t\tEnter Quantity: ");
                            int quantity = App.input.nextInt();
                            if(tempItem.getQuantity() > quantity){
                               tempItem.setQuantity(tempItem.quantity - quantity);
                               if(tempItem instanceof Fruit){
                                   tempPur.addItem(new Fruit(itemID, tempItem.getName(), tempItem.getPrice(), quantity));
                               }
                               else if(tempItem instanceof Bakery){
                                   tempPur.addItem(new Bakery(itemID, tempItem.getName(), tempItem.getPrice(), quantity));
                               }
                               else{
                                   tempPur.addItem(new Drink(itemID, tempItem.getName(), tempItem.getPrice(), quantity));
                               }
                               System.out.println("\n\t\t " + quantity + " " + tempItem.name + " added to basket\n"); 
                               purchases.add(tempPur);
                            }
                            else{
                                System.out.println("\t\tSorry, the " + tempItem.getName() + " stock is not enough");
                            }
                        }
                        else{
                            System.out.println("\n" + Market.searchNotFound + "\n");
                        }
                    break;
                case 2:
                    break outerLoop;
                    default:
                        System.out.println("\n\t\tWrong Option!!!!!!!!\n");
            }
        }
        if(!tempPur.getItems().isEmpty()){
            tempPur.invoice();
        }
    }
    
    private Item findItem(List<Item> items, int id){
        for(Item i : items) if(i.itemId == id) return i;
        return null;
    }
    
    
    /**
     * To Search the items
     * @param item
     * @return
     */
    private Map<String, List<Item>> searchItems(List<Item> items, String item) {
        List<Item> tempItems = items.stream().filter(i -> i.getName().toLowerCase().contains(item.toLowerCase())).collect(Collectors.toList());
        Map<String, List<Item>> itemsCatagories = tempItems.stream().collect(Collectors.groupingBy(x -> x.getClass().getName()));
        return itemsCatagories;
    }

    
    public void purchaseFromHistory(){
        System.out.println();
        System.out.println("\t***********************************");
        System.out.println("\t********* Purchase History ********");
        System.out.println("\t***********************************");
        if(purchases.isEmpty()){
            System.out.println("\t\tEmpty");
        }
        else{
           purchases.forEach(x -> System.out.println("\t\t\t" + x));
            System.out.println(); 
        }
        
    }
    
    public void updateProfile(Market market){
        System.out.println();
        System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\t~~~~~~~ Update Profile ~~~~~~~~~~");
        System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\tEnter 1: Change Name");
        System.out.println("\tEnter 2: Change Email");
        System.out.println("\tEnter 3: Change Password");
        System.out.println("\tEnter 4: Return back");
        System.out.print("\tEnter Option: ");
        int option = App.input.nextInt();
        switch(option){
            case 1:
                App.input.nextLine();
                System.out.print("\tEnter New Name: ");
                String name = App.input.nextLine();
                while(name.isEmpty()){
                    System.out.println("\tYour name cannot be empty");
                    System.out.print("\tEnter New Name: ");
                    name = App.input.nextLine();
                }
                this.fullname = name;
                System.out.println("\t****** Name is updated ********");
                break;
            case 2:
                System.out.print("\tEnter New Email Address: ");
                String email = App.input.next();
                while(market.emailExists.test(email) || market.testEmail.test(email)){
                    System.out.println("\tEither this email is already used or is not corret");
                    System.out.print("\tEnter New Name: ");
                    email = App.input.nextLine();
                }
                this.email = email;
                System.out.println("\t****** Email is updated ********");
                break;
            case 3:
                System.out.print("\tEnter New Password: ");
                String password = App.input.next();
                while(password.length() < 6){
                    System.out.print("\t[Password length should be at least 6 characters] Enter again: ");
                    password = App.input.nextLine();
                }
                this.password = password;
                System.out.println("\t****** Password is updated ********");
                break;
            case 4: return;
            default:
                System.out.println();
                System.out.println("\tWrong Option!");
                System.out.println();
        }
    }
    
    public void checkProfile(){
        System.out.println();
        System.out.println("\t##########################################");
        System.out.println("\t##########################################");
        System.out.println("\t##          " + fullname + " Profile ");
        System.out.println("\t##  Name          : " + fullname);
        System.out.println("\t##  Email         : " + email);
        System.out.println("\t##  Password      : " + password);
        System.out.println("\t##  Total Purchase: " + purchases.size());
        System.out.println("\t##########################################");
        System.out.println("\t##########################################");
    }
    
    public void purchaseHistory(){
        System.out.println("-------- Purchase History --------");
        purchases.forEach(System.out::println);
    }
    
    @Override
    public String toString(){
        return "{Name: " + fullname + ", Email: " + email + ", " + password + "}\n";
    }
}
