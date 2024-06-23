import java.util.Scanner;
public class Player extends Helpers {
    int money;
    Scanner scanner;
    boolean hasCar;
    boolean hasPhone;
    boolean hasHouse;

    /* Player object Constructor
        @peram {int} gMoney: how much money the player starts with
    */
    public Player(int gMoney) {
        money = gMoney; // how much money the player starts with
        System.out.println("You have $" + money);
        scanner = new Scanner(System.in); // scanner to get input

        // tracks if the player can sell their ____
        hasCar = true; 
        hasPhone = true;
        hasHouse = true;
    }

    // Gets what move the player wants to make, in a loop to ensure a valid move
    // @peram {Game} game: game instance so the printHandStatus() method can be called 
    // @peram {Hand} hand: hand instance so the printHandStatus() method can be called, which takes the hand
    public int getMove(Hand hand) {
        /*
            Stand: 0
            Hit: 1
            Double Down: 2
            Split: 3
            Insurance: 4
        */ 
        String input;

        while (true) {
            System.out.println("Enter your move: ");
            input = scanner.nextLine();
            clearConsole();
            if (input.equalsIgnoreCase("S")){
                return 0;
            }
            else if (input.equalsIgnoreCase("H")){
                return 1;
            }
            else if (input.equalsIgnoreCase("DD")){
                return 2;
            }
            else if (input.equalsIgnoreCase("Spt")){
                return 3;
            }
            else if (input.equalsIgnoreCase("I")){
                return 4;
            }
            else {
                System.out.println(input + " is not a valid move, please refer to the options below.");
                showOptions();
                Game.printHandStatus(hand);
            }
        }
    }
    
    /* Gets the amount the player wants to bet with a scanner, in loop to ensure valid input
        @peram {String} betType: The name of the bet that the player is making
    */
    public int getBetInput(String betType) {
        while (true) {
            System.out.println("Please place the amount you would like to bet on " + betType);
            if (scanner.hasNextInt()) {
                int betAmount = scanner.nextInt();
                scanner.nextLine();
                return betAmount;
            } else {
                System.out.println("Invalid input. Please enter an integer value.");
                scanner.next(); // clear invalid input
            }
        }
    }

    /* Changes the players money
        @peram {int} amount: how much the player gets (can be negative to remove money)
    */
    public void giveMoney(int amount){
        money = money + amount;
        System.out.println("You have $" + money);
    }

    // Player Object money getter method
    public int getMoney(){
        return money;
    }

    // Shows the possible moves
    public void showOptions() {
        System.out.println("====================================\n" +
                           "|          Blackjack Moves         |\n" +
                           "====================================\n" +
                           "| H: Hit                           |\n" +
                           "| S: Stand                         |\n" +
                           "| DD: Double Down                  |\n" +
                           "| Spt: Split                       |\n" +
                           "| I: Insurance                     |\n" +
                           "====================================");
    }

    // Asks the player if they want to continue or exit the game
    public boolean askPlayAgain() {
        System.out.println("Would you like to play again (y/n):");
        return scanner.nextLine().equalsIgnoreCase("y") ? true : false;
    }

    // function to give the player more money, is called when the player has no money
    public void playerNeedsMoney() {
        System.out.println("\nYou are out of money.");
        while (getMoney() <= 0) {
            System.out.print("\nWhat are you going to sell: ");
            if (hasCar) {
                System.out.print("Your car(c), ");
            }
            if (hasHouse) {
                System.out.print("Your house(h), ");
            }
            if (hasPhone) {
                System.out.print("Your phone(p)");
            }
            System.out.println("");
            String input = scanner.nextLine();
            clearConsole();
            if (input.equalsIgnoreCase("c") && hasCar) {
                System.out.println("You sold you car for $30,000, taxis aren't that bad.");
                giveMoney(30000);
                hasCar = false;
            }
            else if (input.equalsIgnoreCase("h") && hasHouse) {
                System.out.println("You sold you house for $1,000,000, hope you have a sleeping bag!");
                giveMoney(1000000000);
                hasHouse = false;
            }
            else if (input.equalsIgnoreCase("p") && hasPhone) {
                System.out.println("You sold you phone for $500, did you really need it anyways?");
                giveMoney(500);
                hasPhone = false;
            }
            else {
                System.out.println("Invalid Input, press enter to continue or q to quit");
                if (scanner.nextLine().equalsIgnoreCase("q")) {
                    System.exit(0);
                } 
            }
        }
    }
}