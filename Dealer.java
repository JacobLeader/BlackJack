import java.util.ArrayList;
public class Dealer extends Helpers {
    private ArrayList<Card> cards;
    public Dealer() {
        cards = new ArrayList<Card>();
    }

    public void giveCard(Card card) {
        cards.add(card);
    }
    public int getDealerValue() {
        return getHandValue(cards);
    }
}

