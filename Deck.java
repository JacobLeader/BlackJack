import java.util.*;
public class Deck {
    // New instance for each game played
    ArrayList<Card> deck = new ArrayList<Card>();
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};

    public Deck() {
        createDeck();
        shuffleDeck();
    }

    public void createDeck() {
        for (String suit: suits) {
            for (int i = 0; i < 14; i++) {
                deck.add(new Card(i, suit));
            }
        }
    }

    public void removeCard(int value, String suit) {
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            if (card.getValue() == value && card.getSuit().equals(suit)) {
                deck.remove(i);
                break; // dont need to look anymore
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
}
