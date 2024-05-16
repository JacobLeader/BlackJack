import java.util.ArrayList;
import java.util.Scanner;
public class Player extends Helpers {
    private ArrayList<Card> cards;
    private int money;
    Scanner scanner;

    public Player(int gMoney) {
        cards = new ArrayList<>();
        money = gMoney;
        scanner = new Scanner(System.in);
    }

    public void giveCard(Card card) {
        cards.add(card);
    }

    // positive or negative amount
    public void changeMoney(int amount) {
        money += amount;
    }

    public int getPlayerValue() {
        return getHandValue(cards);
    }

    public int getMove() {
        /*
            Stand: 0
            Hit: 1
            Double Down: 2
            Split: 3
            Insurance: 4
        */ 
        String input;

        System.out.println("Enter your move: ");
        while (true) {
            input = scanner.nextLine();
            System.out.println("input: " + input);
            // System.out.print("\033[H\033[2J");
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
                System.out.println("\nEnter your move: ");

            }
        }
    }
    
    // gets the amount the player wants to bet
    public int getBet(String betType) {
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

    public void giveMoney(int amount){
        money += amount;
    }

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
}
