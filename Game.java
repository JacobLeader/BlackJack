import java.util.ArrayList;
@SuppressWarnings("unused")
public class Game extends Helpers {
    // private Deck deck; 
    private Player player;
    private Dealer dealer;
    private Hand hand;
    private static Game game;

    // Constructor
    public Game() {
        player = new Player(1000);
        dealer = new Dealer();
    }

    // Main game loop
    public static void main(String [] args) {
        int move;
        game = new Game();

        // Each loop represents a round/hand
        while(true) {
            move = 1;
            System.out.println("                            --- NEW GAME ---");
            Deck deck = new Deck();
            // int bet = game.player.getBet("this hand");
            Hand hand = new Hand(); // contains the bets & cards
            
            hand.setBet(game.player); // goes into a loop until the player gives a valid bet amount (money >= bet)

            clearConsole();
            
            //  --- GAME START --- 
            dealCard("Dealer", hand, deck);

            dealCard("You", hand, deck);

            while (move == 1) {
                move = playerTurn(hand, deck);
            }

            if (move == -1) { // If player busted
                game.player.giveMoney(-1 * hand.getBet());
                continue; // stop loop, go to next game
            }
            // Dealer's turn
            while (hand.getDealerValue() < 17) {
                dealCard("Dealer", hand, deck);
            }
            
            int gameStatus = checkWin(hand);

            if (gameStatus == 1 || gameStatus == 2) {
                // WIN
                int payout;
                payout = gameStatus == 1 ? hand.getBet() : 3 * (hand.getBet() / 2); // Payout bet, else, 3:2 for blackjack
                game.player.giveMoney(payout);
                // TODO check which bets the player has, contained in arraylist
            }
            else if (gameStatus == 3) {
                // TIE
                game.player.giveMoney(0); // so player sees the money they have
            }
            else {
                // LOSS
                game.player.giveMoney(-1 * hand.getBet());
            }
        }
    }

    // Checks to see if player won, 0 = loss, 1 = win, 2 = 21, 3 = tie
    public static int checkWin(Hand hand) {
        int playerVal = hand.getPlayerValue();
        int dealerVal = hand.getDealerValue();
        
        if (playerVal > 21) {
            System.out.println("Player Bust!");
            return 0;
        }
        // if dealer busts, and player is still alive
        else if (dealerVal > 21) {
            System.out.println("Dealer Bust!");
            return (playerVal < 21) ? 1 : 2; // if player value is below 21, return win, else return 21
        }
        // if both player and dealer are alive
        else {
            if (playerVal > dealerVal) {
                System.out.println("Player wins, with a " + playerVal);
                return 1; // player wins
            } else if (playerVal < dealerVal) {
                System.out.println("Dealer wins, with a " + dealerVal);
                return 0; // player loses
            } else {
                System.out.println("Tie");
                return 3; // tie
            }
        }
    }

    // Checks if player has busted
    public static boolean checkBust(Hand hand){
        return hand.getPlayerValue() > 21;
    }

    // Handles the players turn
    public static int playerTurn(Hand hand, Deck deck) {
        int move = 1;
        move = game.player.getMove();
        
        if (move == 0) { // Stand
            return 0;
        }
        if (move == 1) { // Hit
            dealCard("You", hand, deck);
            if (checkWin(hand) == 0) { // Prints who won and what happened
                return -1; // forced out of loop in main, so the game ends
            }
        }
        if (move == 2) { // Double Down: Double bet & take one more card, than they have to stand
            hand.setBet(hand.getBet() * 2);
            dealCard("You", hand, deck);
            return 2;
        }
        if (move == 3) { // Split: If player's first two cards = value, they can split them into two separate hands and play each hand
            // TODO implement Split

        }
        if (move == 4) { // Insurance: If dealer's visible card is an Ace, player can buy insurance, which is a side bet that pays out 2:1 if dealer gets Blackjack 
            // TODO implement Insurance
        }
        return move;
    }

    // Prints out what cards the player and dealer have 
    public static void printHandStatus(Hand hand){
        // System.out.println("Your hand value is " + hand.getPlayerValue());
        // System.out.println("Dealer's hand value is " + hand.getDealerValue());
        if (hand.getPlayerValue() != 0) { // dont print anything if player doesnt have any cards, because the game starts with both getting 1 card
                System.out.println("Player has a:");
                for (Card card : hand.getCards("player")){
                    System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
                }
            

            System.out.println();

            System.out.println("Dealer has a:");
            for (Card card : hand.getCards("dealer")){
                System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
            }
            System.out.println();
        }
    }

    // For print statement to tell user which card they got
    public static String handleCard(int value) {
        if (value == 1) {
            return "Ace";
        }
        if (value == 13) {
            return "King";
        }
        if (value == 12) {
            return "Queen";
        }
        if (value == 11) {
            return "Jack";
        }
        return "" + value;
    }

    // Gives a card to the given 'who' input (You, Player)
    public static void dealCard(String who, Hand hand, Deck deck) {
        Card card = deck.getCard();
        if (who.equals("Player")) {
            hand.givePlayerCard(card);
        } else {
            hand.giveDealerCard(card);
        }
        System.out.println(who.equals("Player") ? "You" : who + " got a " + handleCard(card.getValue()) + " of " + card.getSuit());
        printHandStatus(hand);
    }
}