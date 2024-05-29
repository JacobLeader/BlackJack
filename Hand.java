import java.util.ArrayList;

public class Hand extends Helpers {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;

    // Hand object Constructor, initializes player & dealer cards & bet instance variables
    public Hand() {
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        bet = 0;
    }

    /* Hand object Constructor to constrol exactly whats in it, used for split
        @peram {Card} playerCard: to add the one card to the new player hand instance
        @peram {ArrayList<Card>} gDealerCards: to keep the dealers cards the same
        @peram {int} gBet: to keep the bet the same
    */ 
    public Hand(Card playerCard, ArrayList<Card> gDealerCards, int gBet) {
        playerCards = new ArrayList<>();
        playerCards.add(playerCard);
        dealerCards = gDealerCards;
        bet = gBet;
    }

    /* Used to get a valid bet from the player
        @peram {Player} player: to use the getMoney & getBetInput from player instance
    */
    public void setBet(Player player) {
        int gBet;
        int money = player.getMoney();
        while (true) {
            gBet = player.getBetInput("this hand");
            if (gBet <= 0) {
                System.out.println("Error: Your Bet Must be Greater than Zero");
            } else if (money >= gBet) {
                bet = gBet;
                System.out.println(money + "  " + gBet);
                return;
            } else {
                System.out.println("Error: Insufficient Funds");
            }
        }
    }

    /* Hand object bet setter method
        @peram {int} gBet: the amount to set the bet to
    */
    public void setBet(int gBet) {
        bet = gBet;
    }

    // Hand object bet getter method
    public int getBet() {
        return bet;
    }

    // Hand object player cards getter method
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }            
     // Hand object dealer cards getter method
    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    // Gets the value of the player's hand, uses helper method and passes private instance of the player's cards
    public int getPlayerValue() {
        return getHandValue(playerCards);
    }
     // Gets the value of the dealer's hand, uses helper method and passes private instance of the dealer's cards
    public int getDealerValue() {
        return getHandValue(dealerCards);
    }

    /* Adds a card to the player's arraylist of cards
        @peram {Card} card: the card added to the player's arraylist
    */
    public void givePlayerCard(Card card) {
        playerCards.add(card);
    }
    /* Adds a card to the dealers's arraylist of cards
        @peram {Card} card: the card added to the dealers's arraylist
    */
    public void giveDealerCard(Card card) {
        dealerCards.add(card);
    }

    /* Checks if the player can double down (must have only 2 cards & significant funds)
        @peram {int} bet: the bet amount on the hand, doubled then compared to the player's money
        @peram {int} playerMoney: the amount of money that the player has, used to check if the player has enough money
    */
    public boolean canDoubleDown(int bet, int playerMoney) {
        if (playerCards.size() == 2) {
            if (bet * 2 > playerMoney) {
                System.out.println("Error: Insufficient Funds");
                return false;
            }
            return true;
        }
        System.out.println("You can only double down with two cards.");
        return false;
    }

    // Checks if the player can split (must have only 2 cards & same value cards)
    public boolean canSplit() {
        if (playerCards.size() == 2 && playerCards.get(0).getValue() == playerCards.get(1).getValue()){
            return true;
        }
        System.out.println("You cannot split at this time.");
        return false;
    }

    // Checks if the player got a Blackjack, loops looking for an ace & a ten card (face card or 10)
    public boolean isBlackjack() {
        boolean hasAce = false;
        boolean hasTenOrFace = false;
    
        for (Card card : playerCards) {
            int value = card.getValue();
            if (value == 1) {
                hasAce = true;
            } else if (value >= 10) {
                hasTenOrFace = true;
            }
        }
        return hasAce && hasTenOrFace;
    }
}