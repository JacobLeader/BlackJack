import java.util.ArrayList;
@SuppressWarnings("unused")
public class Game {
    // private Deck deck; 
    private Player player;
    private Dealer dealer;
    private static Game game;
    public Game() {
        player = new Player(1000);
        dealer = new Dealer();
    }

    public static void main(String [] args) {
        int move = -1;
        game = new Game();
        // Each loop represents a round/hand
        while(true) {
            Deck deck = new Deck();

            int bet = game.player.getBet("this hand");
            Hand hand = new Hand(bet); // contains the bets

            playerTurn(hand, deck);

            // Dealer's turn
            while (game.dealer.getDealerValue() < 17) {
                game.dealer.giveCard(deck.getCard());
            }

            if (checkWin() == 1 || checkWin() == 2) {
                // WIN
                // TODO check which bets the player has, contained in arraylist
            }
            else if (checkWin() == 3) {
                // TIE

            }
            else {
                // LOSS

            }
        }
    }

    // checks to see if player won, 0 = loss, 1 = win, 2 = 21, 3 = tie
    public static int checkWin() {
        int playerVal = game.player.getPlayerValue();
        int dealerVal = game.dealer.getDealerValue();
        
        if (playerVal > 21) {
            System.out.println("Dealer wins");
            return 0;
        }
        // if dealer busts, and player is still alive
        else if (dealerVal > 21) {
            System.out.println("Player wins");
            return (playerVal < 21) ? 1 : 2; // if player value is below 21, return win, else return 21
        }
        // if both player and dealer are alive
        else {
            if (playerVal > dealerVal) {
                System.out.println("Player wins");
                return 1; // player wins
            } else if (playerVal < dealerVal) {
                System.out.println("Dealer wins ");
                return 0; // player loses
            } else {
                System.out.println("Tie");
                return 3; // tie
            }
        }
    }

    // handles the players turn
    public static void playerTurn(Hand hand, Deck deck) {
        int move = -1;
        while (move == 1) { // while player hits
            move = game.player.getMove();
            game.player.giveCard(deck.getCard());
        }
        if (move == 0) { // Stand
            return;
        }
        if (move == 2) { // Double Down: Double bet & take one more card, than they have to stand
            hand.setBet(hand.getBet() * 2);
        }
        if (move == 3) { // Split: If player's first two cards = value, they can split them into two separate hands and play each hand
            // TODO implement Split
        }
        if (move == 4) { // Insurance: If dealer's visible card is an Ace, player can buy insurance, which is a side bet that pays out 2:1 if dealer gets Blackjack 
            // TODO implement Insurance
        }
    }

    public void dealCards() {
        // TODO initial dealing, player gets two, dealer gets one
    }
}