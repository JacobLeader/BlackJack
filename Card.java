public class Card {
    private int value;
    private String suit;

    public Card(int gVal, String gSuit ) {
        value = gVal;
        suit = gSuit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

}
