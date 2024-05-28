import java.util.ArrayList;

public class Hand extends Helpers {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;

    // Constructor
    public Hand() {
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        bet = 0;
    }

    // Constructor where you can constrol exactly whats in it, used for split
    public Hand(Card playerCard, ArrayList<Card> gDealerCards, int gBet) {
        playerCards = new ArrayList<>();
        playerCards.add(playerCard);
        dealerCards = gDealerCards;
        bet = gBet;
    }

    // Adds or subtracts amount to change
    public void addToBet(int amount) {
        bet += amount;
    }

    // Used first to get a valid bet from the player
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

    // Overloaded method used for doubling down
    public void setBet(int gBet) {
        bet = gBet;
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

    // Returns the hand that someone has (player or dealer)
    public ArrayList<Card> getCards(String who) {
        if (who.equals("player")) {
            return playerCards;
        }
        if (who.equals("dealer")) {
            return dealerCards;
        }
        return new ArrayList<Card>();
    }

    public boolean canDoubleDown() {
        if (playerCards.size() == 2) {
            return true;
        }
        System.out.println("You can only double down with two cards.");
        return false;
    }

    // Checks if the player can split
    public boolean canSplit() {
        if (playerCards.size() == 2 && playerCards.get(0).getValue() == playerCards.get(1).getValue()){
            return true;
        }
        System.out.println("You cannot split at this time.");
        return false;
    }
}
