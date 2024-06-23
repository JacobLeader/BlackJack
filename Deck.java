import java.util.*;
public class Deck {
    // New instance for each game played
    ArrayList<Card> deck = new ArrayList<Card>(); // deck arraylist
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"}; // possible suits

    // Constructor for Deck object
    public Deck() {
        createDeck(); // creates a new deck
        shuffleDeck(); // shuffles the deck
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
        !Not used right now so that card counting can work properly
        @peram {Card} card: the card to add to the deck
    */
    public Card putIntoDeck(Card card) {
        deck.add(card);
        return card;
    }

    // Gets the lines to make up cards as an arraylist
    public static ArrayList<String> getCardLines(Card card) {
        int value = card.getValue();
        String suit = card.getSuit();
        String symbol = getSuitSymbol(suit);
        String cardValue = getValueAsString(value);

        String cardTop = "┌─────────┐";
        String cardBottom = "└─────────┘";

        ArrayList<String> cardLines = new ArrayList<>();
        cardLines.add(cardTop);

        // top left number
        String topLine = "│" + cardValue;
        for (int i = 0; i < (9 - cardValue.length()); i++) {
            topLine += " ";
        }
        topLine += "│";
        cardLines.add(topLine);

        cardLines.add("│         │");

        // line w symbol
        cardLines.add("│    " + symbol + "    │");

        cardLines.add("│         │");

        // bottom right number
        String bottomLine = "│";
        for (int i = 0; i < (9 - cardValue.length()); i++) {
            bottomLine += " ";
        }
        bottomLine += cardValue + "│";
        cardLines.add(bottomLine);

        cardLines.add(cardBottom);

        return cardLines;
    }

    // gets the face cards from a value inputed or just a string from an int
    public static String getValueAsString(int value) {
        if (value == 1) {
            return "A";
        } else if (value == 11) {
            return "J";
        } else if (value == 12) {
            return "Q";
        } else if (value == 13) {
            return "K";
        } else {
            return String.valueOf(value);
        }
    }

    // return symbol from text
    public static String getSuitSymbol(String suit) {
        if ("Hearts".equals(suit)) {
            return "♥";
        } else if ("Diamonds".equals(suit)) {
            return "♦";
        } else if ("Clubs".equals(suit)) {
            return "♣";
        } else if ("Spades".equals(suit)) {
            return "♠";
        } else {
            return " ";
        }
    }

    // Goes layer by layer down, building the cards of each list of lines, nested for loops to print out all the lines, row by row
    public static void printCards(List<Card> cards) {
        ArrayList<ArrayList<String>> allCardLines = new ArrayList<>();
        for (Card card : cards) {
            allCardLines.add(getCardLines(card));
        }

        for (int i = 0; i < allCardLines.get(0).size(); i++) {
            for (ArrayList<String> cardLines : allCardLines) {
                System.out.print(cardLines.get(i) + " ");
            }
            System.out.println();
        }

    }
}