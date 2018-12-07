

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App{

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        
        System.out.println("\t\t------------ Welcome to E-Shopping Basket ------------");
        Market market = new Market("E-Shopping Basket", "Azargul Nazari", "nazariazargul@gmail.com", "jamshid");
        
        //market.printAll();
        
        topOuterLoop:
        while(true){
            switch(mainMenu()){
                case 1:
                    market.signin();
                    break;
                case 2:
                    market.registeration();
                    market.printAll();
                    break;
                case 3:
                    market.searchItems();
                    
                    break;
                case 4: break topOuterLoop;
                default:
                    System.out.println("Sorry, please enter a correct option");
            }
        }

        market.saveBackup();
        
    }
    
    private static int mainMenu(){
        System.out.println("\t--------------------------");
        System.out.println("\t|       Main Page ");
        System.out.println("\t--------------------------");
        System.out.println("\tEnter 1: Sign In");
        System.out.println("\tEnter 2: Register");
        System.out.println("\tEnter 3: Search item");
        System.out.println("\tEnter 4: Exit\n");
        System.out.print("\tEnter your option: ");
        return input.nextInt();
    }
    
    
    
}
