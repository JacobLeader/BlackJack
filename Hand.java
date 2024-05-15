public class Hand {
    // this represents each round of the game
    // used to handle bets
    private int bet;
    public Hand(int gBet) {
        bet = gBet;
    }

    // public void dealCards(int turn) {
    //     if (card)
    // }

    // adds or subtracts amount to change
    public void addToBet(int amount) {
        bet += amount;
    }

    public void setBet(int amount) {
        bet = amount;
    }

    public int getBet() {
        return bet;
    }
}
