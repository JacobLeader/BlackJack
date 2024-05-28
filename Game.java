import java.util.ArrayList;
@SuppressWarnings("unused")
public class Game extends Helpers {
    // private Deck deck; 
    private Player player;
    private Dealer dealer;
    private static Hand hand;
    private static Game game;
    private static int gameCount;
    private static ArrayList<Hand> splitHands;


    // Constructor
    public Game() {
        player = new Player(1000);
        dealer = new Dealer();
        splitHands = new ArrayList<>();
        gameCount = 0;
    }

    // TODO Card counting cheat code 
    // TODO Stats about game play: games played, win ratio, luck factor, net profit
    
    // Main game loop
    public static void main(String [] args) {
        int move;
        game = new Game();

        // Each loop represents a round/hand, gameCount == 0 needs to be infront of askPlayAgain so it checks first
        while(gameCount == 0 || game.player.askPlayAgain()) {
            move = 1;
            clearConsole();
            System.out.println("                            --- NEW GAME ---");
            Deck deck = new Deck();
            Hand hand = new Hand(); // contains the bets & cards
            
            hand.setBet(game.player); // goes into a loop until the player gives a valid bet amount (money >= bet)

            clearConsole();
            
            //  --- GAME START --- 
            dealCard("Dealer", hand, deck);
            dealCard("Player", hand, deck);
            dealCard("Player", hand, deck);
            // dealPair(hand);
            System.out.println();
            printHandStatus(hand);

            gameMechanism(hand, deck, true);

            gameCount++;
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
            dealCard("Player", hand, deck);
            printHandStatus(hand);
            if (checkBust(hand)) { // Prints who won and what happened
                return -1; // forced out of loop in main, so the game ends
            }
        }
        if (move == 2 && hand.canDoubleDown()) { // Double Down: Double bet & take one more card, than the hand is over
            hand.setBet(hand.getBet() * 2);
            System.out.println("Your bet is now " + hand.getBet());
            dealCard("Player", hand, deck);
            printHandStatus(hand);
            return 2;
        }
        else if (move == 2) {
            return 1; // gets a new move, like hitting but no actual hit
        }
        if (move == 3 && hand.canSplit()) {
            split(hand, deck);
            for (int i = 0; i < splitHands.size(); i++) {
                System.out.println("Your are now playing split #" + (i+1));
                printHandStatus(splitHands.get(i));
                gameMechanism(splitHands.get(i), deck, i == splitHands.size() - 1); // only have dealer turn on the last split
            }
        }
        else if (move == 3) {
            return 1; // gets a new move, like a hit happened
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
        System.out.println("Player has a value of " + hand.getPlayerValue() + " with:");
        for (Card card : hand.getCards("player")){
            System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
        }
        

        System.out.println();

        System.out.println("Dealer has a value of " + hand.getDealerValue() + " with:");
        for (Card card : hand.getCards("dealer")){
            System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
        }
        System.out.println();
        
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

    // Gives a card to the given 'who' input (Player, Dealer)
    public static void dealCard(String who, Hand hand, Deck deck) {
        Card card = deck.getCard();
        if (who.equals("Player")) {
            hand.givePlayerCard(card);
        } else {
            hand.giveDealerCard(card);
        }
        System.out.println((who.equals("Player") ? "You" : who) + " got a " + handleCard(card.getValue()) + " of " + card.getSuit());
    }

    // For testing split
    public static void dealPair(Hand hand) {
        Card card1 = new Card(10, "Hearts");
        Card card2= new Card(10, "Clubs");

        hand.givePlayerCard(card1);
        hand.givePlayerCard(card2);
    }

    // Creates split hands by using the custom hand constructor
    public static void split(Hand hand, Deck deck) {
        Hand hand1 = new Hand(hand.getPlayerCards().get(0), hand.getDealerCards(), hand.getBet());
        // dealCard("player", hand1, deck);

        Hand hand2 = new Hand(hand.getPlayerCards().get(0), hand.getDealerCards(), hand.getBet());
        // dealCard("player", hand2, deck);

        splitHands.add(hand1);
        splitHands.add(hand2);

    }

    /* The player's turn, than the dealers turn, than checks to see the result of the game
        @peram dealerTurn If the dealer should take their turn, used for split
    */
    public static void gameMechanism(Hand hand, Deck deck, boolean dealerTurn) {
        int move = 1;

        // playerTurn
        while (move == 1) {
            move = playerTurn(hand, deck);
            if (checkBust(hand)) {
                checkWin(hand);
                return;
            }
        }
        System.out.println("Dealers Turn Now");
        // Dealer's turn
        while (hand.getDealerValue() < 17 && dealerTurn) {
            dealCard("Dealer", hand, deck);
        }
        printHandStatus(hand);
        if (dealerTurn) { // only check for a win once the dealer has had their turn
            int gameStatus = checkWin(hand);
            handleGameResult(gameStatus, hand);
        }
    }

    public static void handleGameResult(int gameStatus, Hand hand) {
        if (gameStatus == 1 || gameStatus == 2) {
            int payout = gameStatus == 1 ? hand.getBet() : 3 * (hand.getBet() / 2);
            game.player.giveMoney(payout);
        } else if (gameStatus == 3) {
            game.player.giveMoney(0);
        } else {
            game.player.giveMoney(-1 * hand.getBet());
        }
    }
}