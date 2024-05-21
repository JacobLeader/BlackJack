import java.util.ArrayList;

public class Hand extends Helpers {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;
    public Hand() {
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        bet = 0;
    }

    // adds or subtracts amount to change
    public void addToBet(int amount) {
        bet += amount;
    }

    public void setBet(Player player) {
        int gBet;
        int money = player.getMoney();
        while (true) {
            gBet = player.getBet("this hand");
            if (money >= gBet) {
                bet = gBet;
                System.out.println(money + "  " + gBet);
                return;
            }

            System.out.println("Error: Insufficient Funds");
        }
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

    // returns the hand that someone has (player or dealer)
    public ArrayList<Card> getCards(String who) {
        if (who.equals("player")) {
            return playerCards;
        }
        if (who.equals("dealer")) {
            return dealerCards;
        }
        return new ArrayList<Card>();
    }
}
