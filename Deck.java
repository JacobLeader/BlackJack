import java.util.*;
public class Deck {
    // New instance for each game played
    ArrayList<Card> deck = new ArrayList<Card>();
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};

    // Constructor for Deck object
    public Deck() {
        createDeck();
        shuffleDeck();
    }

    // Creates a new deck with nested loops
    public void createDeck() {
        for (String suit: suits) {
            for (int i = 1; i <= 13; i++) {
                deck.add(new Card(i, suit));
            }
        }
    }

    // Uses the Collections class to shuffle the deck
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Getter method for a card from the deck instance
    // Deck is shuffled, so we can just grab the first card, than remove it so there are no cards dealt twice
    public Card getCard() {
        return deck.remove(0);
    }

    /* Adds a card to the end of the deck instance variable ArrayList, returns the card so it can be used directly in giveCard methods 
        @peram {Card} card: the card to add to the deck
    */
    public Card putIntoDeck(Card card) {
        deck.add(card);
        return card;
    }
}