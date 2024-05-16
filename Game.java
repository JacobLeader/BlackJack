import java.util.ArrayList;
@SuppressWarnings("unused")
public class Game {
    // private Deck deck; 
    private Player player;
    private Dealer dealer;
    private Hand hand;

    private static Game game;
    public Game() {
        player = new Player(1000);
        dealer = new Dealer();
    }

    public static void main(String [] args) {
        int move;
        game = new Game();

        // Each loop represents a round/hand
        while(true) {
            move = 1;
            System.out.println("--- NEW GAME ---");
            Deck deck = new Deck();
            int bet = game.player.getBet("this hand");
            Hand hand = new Hand(bet); // contains the bets
            
            hand.giveDealerCard(deck.getCard());
            while (move == 1){
                move = playerTurn(hand, deck);
            }

            if (move == -1) { // if player busted
                checkWin(hand);
                game.player.giveMoney(-1 * hand.getBet());
                continue;
            }
            // Dealer's turn
            while (hand.getDealerValue() < 17) {
                hand.giveDealerCard(deck.getCard());
                System.out.println("gave dealer card  " + hand.getDealerValue());
            }
            
            int gameStatus = checkWin(hand);

            if (gameStatus == 1 || gameStatus == 2) {
                // WIN
                game.player.giveMoney(hand.getBet());
                // TODO check which bets the player has, contained in arraylist
            }
            else if (gameStatus == 3) {
                // TIE
            }
            else {
                // LOSS
                game.player.giveMoney(-1 * hand.getBet());
            }
        }
    }

    // checks to see if player won, 0 = loss, 1 = win, 2 = 21, 3 = tie
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

    public static boolean checkBust(Hand hand){
        return hand.getPlayerValue() > 21;
    }

    // handles the players turn
    public static int playerTurn(Hand hand, Deck deck) {
        int move = 1;
            move = game.player.getMove();
            
            if (move == 0) { // Stand
                return 0;
            }
            if (move == 1) { // Hit
                Card card = deck.getCard();
                hand.givePlayerCard(card);
                System.out.println("You got a " + card.getValue() + " of " + card.getSuit() + " your card value is " + hand.getPlayerValue());
                if (checkBust(hand)) {
                    return -1; // forced out of loop in main, so the game ends
                    // return 1;
                }
            }
            if (move == 2) { // Double Down: Double bet & take one more card, than they have to stand
                hand.setBet(hand.getBet() * 2);
                // return 2;
            }
            if (move == 3) { // Split: If player's first two cards = value, they can split them into two separate hands and play each hand
                // TODO implement Split
            }
            if (move == 4) { // Insurance: If dealer's visible card is an Ace, player can buy insurance, which is a side bet that pays out 2:1 if dealer gets Blackjack 
                // TODO implement Insurance
            }
            return move;
    }

    public void dealCards() {
        // TODO initial dealing, player gets two, dealer gets one
    }
}