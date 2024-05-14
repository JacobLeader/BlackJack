import java.util.ArrayList;
@SuppressWarnings("unused")
public class Game {
    private Deck deck; 
    private Player player;
    private Dealer dealer;
    public Game() {
        player = new Player(1000);
        dealer = new Dealer();
    }

    public void main(String [] args) {
        int move = -1;
        // Each loop represents a round/hand
        while(true) {
            Deck deck = new Deck();
            // Hand hand = new Hand(deck);

            while (move != 0) {
                move = player.getMove();
            }
            while (dealer.getDealerValue() < 17) {
                dealer.giveCard(deck.getCard());
            }
            // TODO if statement to find winner
        }
    }

    public void executeMove(int move) {

    }

    public void dealCards() {

    }
}