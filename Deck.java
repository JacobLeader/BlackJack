import java.util.*;
public class Deck {
    // New instance for each game played
    ArrayList<Card> deck = new ArrayList<Card>();
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};

    // Constructor
    public Deck() {
        createDeck();
        shuffleDeck();
    }

    // Creates a new deck 
    public void createDeck() {
        for (String suit: suits) {
            for (int i = 1; i <= 13; i++) {
                deck.add(new Card(i, suit));
            }
        }
        System.out.println(deck.size());
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Deck is shuffled, so we can just grab the first card, than remove it so there are no cards dealt twice
    public Card getCard() {
        return deck.remove(0);
    }
}