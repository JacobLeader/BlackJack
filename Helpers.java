import java.util.ArrayList;
public class Helpers {

    // gets the value of the arraylist of cards passed into it
    public int getHandValue(ArrayList<Card> cards) {
        // straight forward other than aces
        int value = 0;
        int aceCount = 0;
        int tempValue;
        for (Card card : cards) {
            // value = (value + card.getValue() ? card.getValue() < 10  ? card.getValue() != 14 : aces++)
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

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
