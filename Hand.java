import java.util.ArrayList;

public class Hand extends Helpers {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    private boolean hasInsurance;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;

    // Hand object Constructor, initializes player & dealer cards & bet instance variables
    public Hand() {
        playerCards = new ArrayList<>(); // holds the cards that the player has
        dealerCards = new ArrayList<>(); // holds the cards that the dealer has
        bet = 0; // holds the bet amount for that one hand
        hasInsurance = false; // if the player has an insurance bet
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

    /* Used to get a valid bet from the player, loops until valid (0 < bet <= Player's money)
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
                System.out.println("Error: Insufficient Funds, you only have $" + playerMoney + " which is less than $" + bet * 2);
                return false;
            }
            return true;
        }
        System.out.println("Error: You can only double down with two cards.");
        return false;
    }

    // Checks if the player can split (must have only 2 cards & same value cards)
    public boolean canSplit(int bet, int playerMoney) {
        if (playerCards.size() == 2 && playerCards.get(0).getValue() == playerCards.get(1).getValue() && bet * 2 < playerMoney) {
            return true;
        }
        else if (playerCards.get(0).getValue() != playerCards.get(1).getValue()){
            System.out.println("Error: You can only split with two cards of equal value.");
        }
        else {
            System.out.println("Error: Insufficient funds, you must be able to place an equal bet on your split hand.\nYour bet is $" + bet + ", but you only have funds of $" + playerMoney);
        }
        return false;
    }

    /* Checks if the player got a Blackjack, loops looking for an ace & a ten card (face card or 10)
        @peram {String} who: player or dealer, who's hand to check for a blackjack
    */
    public boolean isBlackjack(String who) {
        boolean hasAce = false;
        boolean hasTenOrFace = false;
    
        for (Card card : who.equalsIgnoreCase("player") ? playerCards : dealerCards) {
            int value = card.getValue();
            if (value == 1) {
                hasAce = true;
            } else if (value >= 10) {
                hasTenOrFace = true;
            }
        }
        return hasAce && hasTenOrFace;
    }

    /* Checks if the player can get insurance (dealers first card must be an ace)
       Player puts half of their original bet down on insurance
        @peram {int} bet: the bet amount on the hand, added to itself /2 then compared to the player's money
        @peram {int} playerMoney: the amount of money that the player has, used to check if the player has enough money
    */
    public boolean canGetInsurance(int bet, int playerMoney) {
        if (dealerCards.size() == 1 && dealerCards.get(0).getValue() == 1) {
            if (bet + (bet/2) > playerMoney) {
                System.out.println("Error: Insufficient Funds, you must have half of your original bet extra to put up for insurance.");
                return false;
            }
            return true;
        }
        System.out.println("Error: You can get insurance when the dealer has one ace face up");
        return false;
    }

    // Hand object hasInsurance getter method
    public boolean hasInsurance() {
        return hasInsurance;
    }
    /* Hand object hasInsurance setter method
        @peram {boolean} insurance: if the player should or shouldn't have insurance
    */ 
    public void setInsurance(boolean insurance) {
        hasInsurance = insurance;
    }
}