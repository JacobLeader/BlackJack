import java.util.ArrayList;
public class Deck {
    // New instance for each game played
    ArrayList<Card> deck = new ArrayList<Card>();
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    
    public Deck() {
        for (String suit: suits) {
            for (int i = 0; i < 14; i++) {
                deck.add(new Card(i, suit));
            }

        }
    }

    public void removeCard(int value, String suit) {
        deck.remove()
    }
}
