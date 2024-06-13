import java.util.ArrayList;
public class Helpers {

    /* gets the value of the arraylist of cards passed into it
        @peram {ArrayList<Card>} cards: indexed and values are summed
    */
    public int getHandValue(ArrayList<Card> cards) {
        // straight forward other than aces
        int value = 0;
        int aceCount = 0;
        int tempValue;
        for (Card card : cards) {
            tempValue = card.getValue();
            if (tempValue == 1) { // Ace
                value += 11;
                aceCount++;
            } else if (tempValue >= 10) { // Face cards
                value += 10;
            } else {
                value += tempValue;
            }
        }
        // aces can be 1 or 11, so if value is above 21 & there are aces, than change aces from the initial value of 11 to 1 until under 21
        while (value > 21 && aceCount > 0) {
            value -= 10; // change 1 ace from 11 -> 1
            aceCount--;
        }
        return value;
    }

    // Clears console
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    // for the card counting feature, returns the Hi-Lo value of the card to be added to the count
    public static int getCardCountValue(Card card) {
        if (card.getValue() <= 6) {
            return 1;
        }
        else if (card.getValue() >= 6 && card.getValue() <= 9) {
            return 0;
        }
        else {
            return -1;
        }
    }
}