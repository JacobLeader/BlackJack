import java.util.Scanner;
public class Player extends Helpers {
    int money;
    Scanner scanner;

    /* Player object Constructor
        @peram {int} gMoney: how much money the player starts with
    */
    public Player(int gMoney) {
        money = gMoney;
        System.out.println("You have $" + money);
        scanner = new Scanner(System.in);
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
}