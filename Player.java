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
            input = scanner.nextLine().toLowerCase();
            System.out.print("\033[H\033[2J");
            if (input == "S"){
                return 0;
            }
            else if (input == "H"){
                return 1;
            }
            else if (input == "DD"){
                return 2;
            }
            else if (input == "Spt"){
                return 3;
            }
            else if (input == "I"){
                return 4;
            }
            else {
                System.out.println(input + " is not a valid move, please refer to the options below.");
                showOptions();
            }
        }
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
