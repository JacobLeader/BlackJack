import java.util.ArrayList;

public class Hand extends Helpers {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;
    public Hand(int gBet) {
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        bet = gBet;
    }

    // adds or subtracts amount to change
    public void addToBet(int amount) {
        bet += amount;
    }

    public void setBet(int amount) {
        bet = amount;
    }

    public int getBet() {
        return bet;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public int getPlayerValue() {
        return getHandValue(playerCards);
    }

    public int getDealerValue() {
        return getHandValue(dealerCards);
    }

    public void givePlayerCard(Card card) {
        playerCards.add(card);
    }
    public void giveDealerCard(Card card) {
        dealerCards.add(card);
    }
}
