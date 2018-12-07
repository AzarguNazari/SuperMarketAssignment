

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Market implements Serializable{

    private static final long serialVersionUID = 25L; 
    private final String marketName;
    public List<Item> items = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Pattern pattern;
    private Matcher matcher;
    private final Admin admin;

    // Constructor
    public Market(String marketName, String adminFullName, String email, String pass) throws IOException, ClassNotFoundException {
        this.marketName = marketName;   // setting the market name
        admin = new Admin(adminFullName, marketName, email, pass);
        
        // If the items and customers file doesn't exist, create new one
        File itemRecords = new File("item.txt"), customersRecords = new File("users.txt");
        if(!itemRecords.exists()) itemRecords.createNewFile();
        if(!customersRecords.exists()) customersRecords.createNewFile();
        
        // To load the items and customers records into List<Item> and List<Customer>

        try(ObjectInputStream users = new ObjectInputStream(new FileInputStream(customersRecords))){
            customers = (List<Customer>) users.readObject();
        }
        catch(EOFException ex){}
        
        try(ObjectInputStream allItems = new ObjectInputStream(new FileInputStream(itemRecords))){                     
            items = (List<Item>) allItems.readObject(); 
        }
        catch(EOFException ex){}
        
        
    }

    
    /**
     * This method is to register a new customer 
     */
    public void registerCustomer(){
        Person p = registerationForm();
        Customer customer = new Customer(p.fullname, p.email, p.password);
        this.customers.add(customer);
        System.out.println("----- New Customer is added --- ");
        System.out.println(customer);
    }
    
    private Person registerationForm(){
        App.input.nextLine();
        System.out.println("----------- User Register --------------");
        System.out.print("Enter Full Name: ");
        String name = App.input.nextLine();
        while (!testName.test(name)) {
            System.out.print("Wrong, Enter again your Full Name: ");
            name = App.input.nextLine();
        }
        System.out.print("Enter Email address: ");
        String email = App.input.next();
        while (!testEmail.test(email) && emailExists.test(email)) {
            System.out.println("Wrong, The email is either wrong or already exists in system");
            System.out.print("Enter correct Email address: ");
            email = App.input.next();
        }
        System.out.print("Enter password: ");
        String password = App.input.next();
        while (password.length() < 6) {
            System.out.print("Wrong, Enter again password[at least 6 characters]: ");
            password = App.input.next();
        }
        return new Person(name, email, password);
    }

    public void signin(){
        System.out.println();
        System.out.println("\t####################################");
        System.out.println("\t############ Sign In ###############");
        System.out.println("\t####################################");
        System.out.print("\tEnter your email   : ");
        String email = App.input.next();
        System.out.print("\tEnter your password: ");
        String password = App.input.next();
        if(admin.getEmail().equalsIgnoreCase(email) && admin.getPassword().equalsIgnoreCase(password)){
            admin.adminPanel(this, items, customers);
        }
        else{
            Customer newCustomer = null;
            for(Customer c : customers) {
                if(c.getEmail().equalsIgnoreCase(email) && c.getPassword().equalsIgnoreCase(password)){
                    newCustomer = c; break;
                }
            }
                    
            if(newCustomer == null){
                System.out.println();
                System.out.println(Market.searchNotFound);
                System.out.println();
            }
            else{
                customerPanel(newCustomer);
            }
        }
    }


    public void printAllItems() throws FileNotFoundException, IOException, ClassNotFoundException {
        Map<Class, List<Item>> itemsCatagories = items.stream().collect(Collectors.groupingBy(x -> x.getClass()));
        this.items.stream().forEach(System.out::println);
    }

    public Item findItem() {
        System.out.print("Enter item Name: ");
        String itemName = App.input.nextLine();
        List<Item> filteredList = items.stream().filter(x -> x.getName().contains(itemName)).collect(Collectors.toList());
        if(filteredList.size() > 0){
            for(int x = 0; x < filteredList.size(); x++){
                System.out.println("Enter " + x + ": to select " + filteredList.get(x));
            }
            System.out.print("Enter the number: ");
            int number = App.input.nextInt();
            while(number < 0 || number >= filteredList.size()){
                System.out.println("Wrong, Please enter the correct number");
                number = App.input.nextInt();
            }
            return filteredList.get(number);
        }
        else{
            System.out.println("Sorry, there is no such item");
            return null;
        }
    }
    
    public void customerPanel(Customer customer){
        while(true){
            System.out.println();
            System.out.println("\t#########################################");
            System.out.println("\t###### " + customer.fullname + " Account #######");
            System.out.println("\t#########################################");
            System.out.println("\tEnter 1: Purchase");
            System.out.println("\tEnter 2: Check Purchases History");
            System.out.println("\tEnter 3: Update Profile");
            System.out.println("\tEnter 4: Check Profile");
            System.out.println("\tEnter 5: Sign Out\n");
            System.out.print("\tEnter Your Choice: ");
            int choice = App.input.nextInt();
            switch(choice){
                case 1:
                    customer.purchase(items);
                    break;
                case 2:
                    customer.purchaseFromHistory();
                    break;
                case 3:
                    customer.updateProfile(this);
                    break;
                case 4:
                    customer.checkProfile();
                    break;
                case 5: return;
            }
        }
        
    }

    public void shopping(Customer customer) throws IOException {
        while (true) {
            switch (menu()) {
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:
                    return;
            }
        }
    }

    private int menu() {
        System.out.println("Enter 1 for search");
        System.out.println("Enter 2 for adding new item");
        System.out.println("Enter 3 for purchasing item");
        System.out.println("Enter 4 for exit");
        System.out.print("Enter your option: ");
        int option = App.input.nextInt();
        while (option > 4 || option < 1) {
            System.out.println("Sorry, please enter a correct option");
            option = App.input.nextInt();
        }
        return option;
    }


    public void searchItems(){
        System.out.println("--------------- Research Item -------------------");
        System.out.print("Enter item name: ");
        String item = App.input.next();
        List<Item> tempItems = items.stream().filter(i -> i.getName().toLowerCase().contains(item.toLowerCase())).collect(Collectors.toList());
        Map<String, List<Item>> itemsCatagories = tempItems.stream().collect(Collectors.groupingBy(x -> x.getClass().getName()));
        if(itemsCatagories.isEmpty()){
            System.out.println("--------- Search Not Found -------------");
        }
        else{
            System.out.println("======= Found Search =========");
            itemsCatagories.forEach((k, v) -> {
                System.out.println(k + " Items");
                System.out.println("");
                System.out.println(v);
            });
            System.out.println("------------------- If You want to Purcase Please Fisrt Sign In-------------------");
        }
    }
    
    public void registeration(){
        Person p = registerationForm();
        Customer customer = new Customer(p.fullname, p.email, p.password);
        customers.add(customer);
        System.out.println("---------------- Successfully Registered ------------------");
        System.out.printf("%12s%s%n", "Name:", customer.fullname);
        System.out.printf("%12s%s%n", "Email:", customer.email);
        System.out.printf("%12s%s%n", "Password:", customer.password);
        System.out.println("-----------------------------------------------------------");
    }
    
    public void saveBackup() throws FileNotFoundException, IOException, IOException{
        try (ObjectOutputStream saveItems = new ObjectOutputStream(new FileOutputStream("item.txt"));ObjectOutputStream saveCustomers = new ObjectOutputStream(new FileOutputStream("users.txt"))){
            saveCustomers.writeObject(customers);
            saveItems.writeObject(items);
        }
        System.out.println("\n\n\t\t#############################################################");
        System.out.println("\t\t###   Thank You for using " + this.marketName);
        System.out.println("\t\t###   All the data is backuped");
        System.out.println("\t\t#############################################################");
    }
    
    public void printAll(){
        customers.forEach(System.out::println);
        items.forEach(System.out::println);
    }
    
     /**
     * These are the utility predicates which are used during checking
     */
    
    // to test the name
    public Predicate<String> testName = name -> {
        pattern = Pattern.compile("[a-zA-Z]*\\w+");
        matcher = pattern.matcher(name);
        return matcher.find();
    };

    // to test the email address
    public final Predicate<String> testEmail = email -> {
        pattern = Pattern.compile("[\\w._]+@\\w+(.\\w+)+");
        matcher = pattern.matcher(email);
        return matcher.find();
    };

    public final Predicate<String> emailExists = email -> this.customers.stream().anyMatch(c -> c.email.equalsIgnoreCase(email));
    public final Predicate<Integer> itemSearch = itemID -> items.stream().anyMatch(i -> i.itemId == itemID);
    
    public static String searchNotFound = "\t##     ## ######### #########   ######### ######### ##     ## ##     ## ######\n" + 
                                          "\t##     ## ######### #########   ######### ######### ##     ## ##     ## #######\n" +
                                          "\t##     ## ##     ##    ##       ##        ##     ## ##     ## ##     ## ##   ####\n" +
                                          "\t## ##  ## ##     ##    ##       ######### ##     ## ##     ## ## ##  ## ##     ##\n" + 
                                          "\t## ##  ## ##     ##    ##       ######### ##     ## ##     ## ## ##  ## ##     ##\n" + 
                                          "\t##   #### ##     ##    ##       ##        ##     ## ##     ## ##   #### ##     ##\n" +
                                          "\t##    ### #########    ##       ##        ######### ######### ##    ### #########\n" +
                                          "\t##     ## #########    ##       ##        ######### ######### ##     ## ######";
}
