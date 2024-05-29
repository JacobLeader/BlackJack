public class Card {
    private int value;
    private String suit;

    /* Constructor
        @peram {int} gVal: the value of the card
        @peram {String} gSuit: the suit of the card
    */
    public Card(int gVal, String gSuit ) {
        value = gVal;
        suit = gSuit;
    }

    // Card Object value getter method
    public int getValue() {
        return value;
    }

    // Card Object suit getter method
    public String getSuit() {
        return suit;
    }
}