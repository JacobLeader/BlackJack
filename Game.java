import java.util.ArrayList;
public class Game extends Helpers {
    private Player player;
    private static Game game;
    private static int gameCount;
    private static ArrayList<Hand> splitHands;

    // Game object Constructor
    public Game() {
        player = new Player(1000);
        splitHands = new ArrayList<>();
        gameCount = 0;
    }

    // TODO Card counting cheat code 
    // TODO put the cards back into the deck
    // TODO Stats about game play: games played, win ratio, luck factor, net profit, correct move percentage
    
    // Main game loop
    public static void main(String [] args) {
        game = new Game();
        game.player.showOptions();

        // Each loop represents a round/hand, gameCount == 0 needs to be infront of askPlayAgain so it checks first
        while(gameCount == 0 || game.player.askPlayAgain()) {
            if (gameCount > 0) {clearConsole();}
            System.out.println("                            --- NEW GAME ---");
            Deck deck = new Deck();
            Hand hand = new Hand(); // contains the bets & cards
            
            hand.setBet(game.player); // goes into a loop until the player gives a valid bet amount (money >= bet)

            clearConsole();
            
            //  --- GAME START --- 
            dealCard("Dealer", hand, deck);
            dealCard("Player", hand, deck);
            dealCard("Player", hand, deck);
            // dealCustomCards(hand); // Used for testing
            System.out.println();
            printHandStatus(hand);

            if (hand.isBlackjack("player")) {
                handleGameResult(checkWin(hand), hand);
                continue;
            }

            gameMechanism(hand, deck, true);

            gameCount++;
        }
    }

    /* Checks to see if player won, 0 = loss, 1 = win, 2 = 21, 3 = tie
        @peram hand: to get player & dealer hand values
    */
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
            System.out.print(hand.isBlackjack("player") ? "Player got Blackjack!\n" : "");
            return hand.isBlackjack("player") ? 2 : 1; // if player got a blackjack, return 2, else return a win(1)
        }
        // if both player and dealer are alive
        else {
            if (playerVal > dealerVal) {
                System.out.println("Player wins, with a " + playerVal);
                System.out.print(hand.isBlackjack("player") ? "Player got Blackjack!\n" : "");
                return hand.isBlackjack("player") ? 2 : 1; // player wins, check for a blackjack
            } else if (playerVal < dealerVal) {
                System.out.println("Dealer wins, with a " + dealerVal);
                return 0; // player loses
            } else {
                System.out.println("Tie");
                return 3; // tie
            }
        }
    }

    /* Checks if player has busted
        @peram hand: to get the player's hand value
    */
    public static boolean checkBust(Hand hand){
        return hand.getPlayerValue() > 21;
    }

    /* Handles the player's turn 
        @peram hand: to pass into methods, set/get the player's bet
        @peram deck: to pass into methods
    */
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
        if (move == 2 && hand.canDoubleDown(hand.getBet(), game.player.getMoney())) { // Double Down: Double bet & take one more card, than the hand is over
            hand.setBet(hand.getBet() * 2);
            System.out.println("Your bet is now " + hand.getBet());
            dealCard("Player", hand, deck);
            return 2;
        }
        else if (move == 2) {
            return 1; // gets a new move, like hitting but no actual hit
        }
        if (move == 3 && hand.canSplit()) {
            split(hand);
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
            if (hand.canGetInsurance(hand.getBet(), game.player.getMoney())) {
                hand.setInsurance(true);
                System.out.println("You now have insurance worth " + hand.getBet()/2);
            }
            return 1; // gets a new move, like a hit happened
        }
        return move;
    }

    /* Prints out the hand value and what cards the player and dealer have 
        @peram hand: to get player and dealer cards and hand values
    */
    public static void printHandStatus(Hand hand){
        System.out.println("Player has a value of " + hand.getPlayerValue() + " with:");
        for (Card card : hand.getPlayerCards()){
            System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
        }
        
        System.out.println();

        System.out.println("Dealer has a value of " + hand.getDealerValue() + " with:");
        for (Card card : hand.getDealerCards()){
            System.out.println("    " + handleCard(card.getValue()) + " of " + card.getSuit());
        }
        System.out.println();
    }

    /* To interperate the face and value cards
        @peram value: used to tell what card it is
    */ 
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

    /* Gives a card to the given 'who' input (Player or Dealer)
        @peram who: to determine who to give card to, player or dealer
        @peram hand: to give player or dealer that card
        @peram deck: to get a card from the deck
    */
    public static void dealCard(String who, Hand hand, Deck deck) {
        Card card = deck.getCard();
        if (who.equalsIgnoreCase("Player")) {
            hand.givePlayerCard(card);
        } else {
            hand.giveDealerCard(card);
        }
        System.out.println((who.equals("Player") ? "You" : who) + " got a " + handleCard(card.getValue()) + " of " + card.getSuit());
    }

    // For testing specific scenarios
    public static void dealCustomCards(Hand hand) {
        // Test split
        // Card card1 = new Card(10, "Hearts");
        // Card card2= new Card(10, "Clubs");

        // Test blackjack
        // Card card1 = new Card(1, "Hearts");
        // Card card2= new Card(10, "Clubs");

        // hand.givePlayerCard(card1);
        // hand.givePlayerCard(card2);

        // Test insurance
        Card card = new Card(1, "Hearts");
        hand.giveDealerCard(card);
    }

    /* Creates split hands by using the custom hand constructor 
        @peram hand: to pass player and dealer cards into the Hand constructor
    */
    public static void split(Hand hand) {
        Hand hand1 = new Hand(hand.getPlayerCards().get(0), hand.getDealerCards(), hand.getBet());
        // dealCard("player", hand1, deck);

        Hand hand2 = new Hand(hand.getPlayerCards().get(0), hand.getDealerCards(), hand.getBet());
        // dealCard("player", hand2, deck);

        splitHands.add(hand1);
        splitHands.add(hand2);

    }

    /* The player's turn, than the dealers turn, than checks to see the result of the game
        @peram hand, deck: passed into methods
        @peram dealerTurn If the dealer should take their turn, used for split
    */
    public static void gameMechanism(Hand hand, Deck deck, boolean dealerTurn) {
        int move = 1;
        int gameStatus = 0;

        // Player's Turn
        while (move == 1) {
            move = playerTurn(hand, deck);
            if (checkBust(hand)) {
                if (hand.hasInsurance()) { // TODO refactor this
                    handleInsuranceResult(hand.getBet(), hand.isBlackjack("dealer"), game.player);
                    hand.setInsurance(false);
                }
                gameStatus = checkWin(hand);
                handleGameResult(gameStatus, hand);
                return;
            }
        }
        // Dealer's turn
        while (hand.getDealerValue() < 17 && dealerTurn) {
            dealCard("Dealer", hand, deck);
            if (hand.hasInsurance()) {
                handleInsuranceResult(hand.getBet(), hand.isBlackjack("dealer"), game.player);
                hand.setInsurance(false);
            }
        }
        printHandStatus(hand);
        if (dealerTurn) { // only check for a win once the dealer has had their turn
            gameStatus = checkWin(hand);
            handleGameResult(gameStatus, hand);
        }
    }

    /* Handles the result from the check win and gives/removes money from player
        @peram gameStatus: To determine what the payout/removal of money should be
        @peram hand: To get the bet amount
    */
    public static void handleGameResult(int gameStatus, Hand hand) {
        if (gameStatus == 1 || gameStatus == 2) { // WIN
            int payout = gameStatus == 1 ? hand.getBet() : (int)(hand.getBet() * 1.5);
            game.player.giveMoney(payout);
        } else if (gameStatus == 3) { // TIE
            game.player.giveMoney(0);
        } else {
            game.player.giveMoney(-1 * hand.getBet()); // LOSS
        }
    }

    /* Handles the result of the player getting insurance
        @peram {int} bet: to determine what the payout/removal of money should be
        @peram {boolean} dealerBlackjack: if the dealer got blackjack or not
        @peram {Player} player: used to give the player money
    */
    public static void handleInsuranceResult(int bet, boolean dealerBlackjack, Player player) {
        System.out.println(dealerBlackjack ? "You won your insurance" : "You lost your insurance");
        int payout = (int)(dealerBlackjack ? bet/2 : bet/-2);
        player.giveMoney(payout);
    }
}