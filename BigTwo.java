import java.util.ArrayList;
/**
 * This class is to start the game
 * 
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class BigTwo implements CardGame{
    private static final int numOfplayer =4;
    private Deck deck;
    private ArrayList<CardGamePlayer> playerList;
    private ArrayList<Hand> handsOnTable;
    private int currentPlayerIdx;
    private BigTwoGUI gui;

    /**
	 * This constructor construct a BigTwo object
	 * 
	 */
    public BigTwo() {

		handsOnTable = new ArrayList<Hand>();
		playerList= new ArrayList<CardGamePlayer>();
		
		CardGamePlayer Player0 = new CardGamePlayer();
		CardGamePlayer Player1 = new CardGamePlayer();
		CardGamePlayer Player2 = new CardGamePlayer();
		CardGamePlayer Player3 = new CardGamePlayer();
	   
		this.playerList.add(Player0);
		this.playerList.add(Player1);
		this.playerList.add(Player2);
		this.playerList.add(Player3);
        gui = new BigTwoGUI(this);
	}
    
     /**
	 * This starts the program
	 * 
	 * @param args Unused.
	 */
    public static void main(String[] args) {
        BigTwo game = new BigTwo();
        BigTwoDeck deckforgame = new BigTwoDeck();
        game.start(deckforgame);
        
        // for debug
        // Card card = new Card(0, 0);
        // Card card2 = new Card(1, 0);
        // Card card3 = new Card(2, 0);
        // Card card4 = new Card(3, 0);
        // CardList temp = new CardList();
        // temp.addCard(card);
        // temp.addCard(card2);
        // temp.addCard(card3);
        // temp.addCard(card4);
        // Hand hand = new Quad(game.playerList.get(0), temp);
        // game.handsOnTable.add(hand);

        // System.out.println("Hands on table : " + game.handsOnTable.size());
        // System.out.println("Num players : " + game.getNumOfPlayers());
        // System.out.println("currentPlayerIdx " + game.getCurrentPlayerIdx());
        //________________________________________________
    }

    /**
	 * This method return the number of players
	 * @return the number of players
	 */
    public int getNumOfPlayers() {
        return numOfplayer;
    }
    /**
	 * This method return the deck
	 * @return the deck
	 */
    public Deck getDeck(){
        return this.deck;
    }
    /**
	 * This method return the player arraylist
	 * @return the player arraylist
	 */
    public ArrayList<CardGamePlayer> getPlayerList(){
        return this.playerList;
    }
    /**
	 * This method return the array of cards on table
	 * @return the array of cards on table
	 */
    public ArrayList<Hand> getHandsOnTable(){
        return this.handsOnTable;
    }
    /**
	 * This method return the current player index
	 * @return the current player index
	 */
    public int getCurrentPlayerIdx(){
        return this.currentPlayerIdx;
    }
    /**
	 * This method distribute the deck to the player equally and start the game
	 * @param deck the deck
	 */
    public void start(Deck deck){
        for (int i = 0; i <playerList.size(); i++){
            playerList.get(i).removeAllCards();
        }  
        handsOnTable.clear();
        this.deck = deck;

        deck.shuffle();
        for (int i = 0; i< 52; i++){  
            this.playerList.get(i%4).addCard(this.deck.getCard(i));
            if (this.deck.getCard(i).getRank() == 2 && this.deck.getCard(i).getSuit() == 0){
                currentPlayerIdx = i%4;
                this.gui.setActivePlayer(currentPlayerIdx);
            }
        }
        for (int i = 0; i < playerList.size(); i++){
            playerList.get(i).sortCardsInHand();
        }
        this.gui.repaint();
        this.gui.promptActivePlayer();
    }
    
    /**
	 * This method execute players command
	 * @param playerIdx player index
     * @param cardIdx index of card chosen
	 */
    public void makeMove(int playerIdx, int[] cardIdx){
        checkMove(playerIdx, cardIdx);
    }
    /**
	 * This method checks players command
	 * @param playerIdx player index
     * @param cardIdx index of card chosen
	 */
    public void checkMove(int playerIdx, int[] cardIdx){
        if(this.endOfGame()){
            print_scoreboard();
        }
        //first turn.
        if (this.handsOnTable.size() == 0){

            CardList cards_to_play =  playerList.get(currentPlayerIdx).play(cardIdx);

            if (cards_to_play != null){
                BigTwoCard diamond_three = new BigTwoCard(0, 2);
                Hand hand_to_play = composeHand(playerList.get(currentPlayerIdx), cards_to_play);

                if (hand_to_play != null){
                    if (hand_to_play.contains(diamond_three) == false){
                        this.gui.printMsg("Not a legal move!!!");
                        this.gui.promptActivePlayer();
                    }
                    else{
                        this.handsOnTable.add(hand_to_play);
                        this.gui.printMsg("<" + playerList.get(currentPlayerIdx).getName() + ">" + " " + "{" + hand_to_play.getType() + "}" + " " );
                        hand_to_play.print();
                        for (int i=0; i< hand_to_play.size(); i++) {
                            playerList.get(currentPlayerIdx).getCardsInHand().removeCard(hand_to_play.getCard(i));
                        }
                        print_scoreboard();

                        currentPlayerIdx++;
                        if (currentPlayerIdx == 4){
                            currentPlayerIdx = 0;
                        }
                        this.gui.setActivePlayer(getCurrentPlayerIdx());
                        this.gui.repaint();
                        this.gui.promptActivePlayer();
                    }
                }
                //hand is not a valid hand.
                else{ 
                    this.gui.printMsg("Not a legal move!!!");
                    this.gui.promptActivePlayer();
                }
            }
            //hand is empty.
            else{
                this.gui.printMsg("Not a legal move!!!");
                this.gui.promptActivePlayer();
            }
        }
        
        //not first turn.
        else if (this.handsOnTable.size() != 0){

            CardList cards_to_play =  playerList.get(currentPlayerIdx).play(cardIdx);

            if (cards_to_play != null){
                Hand hand_to_play = composeHand(playerList.get(currentPlayerIdx), cards_to_play);

                if (hand_to_play != null){

                    //The case that the other 3 player have passed and return to the first player.
                    if (this.playerList.get(currentPlayerIdx).getName() == this.getHandsOnTable().get(this.getHandsOnTable().size()-1).getPlayer().getName()){
                        this.handsOnTable.add(hand_to_play);
                        this.gui.printMsg("<" + playerList.get(currentPlayerIdx).getName() + ">" + " " + "{" + hand_to_play.getType() + "}" + " ");
                        hand_to_play.print();
                        for (int i=0; i< hand_to_play.size(); i++) {
                            playerList.get(currentPlayerIdx).getCardsInHand().removeCard(hand_to_play.getCard(i));
                        }
                        print_scoreboard();

                        currentPlayerIdx++;
                        if (currentPlayerIdx == 4){
                        currentPlayerIdx = 0;
                        }
                        this.gui.setActivePlayer(getCurrentPlayerIdx());
                        this.gui.repaint();
                        this.gui.promptActivePlayer();
                    }

                    //The normal case.
                    else if (hand_to_play.beats(handsOnTable.get(handsOnTable.size()-1))){
                        this.handsOnTable.add(hand_to_play);
                        this.gui.printMsg("<" + playerList.get(currentPlayerIdx).getName() + ">" + " " + "{" + hand_to_play.getType() + "}" + " ");
                        hand_to_play.print();
                        for (int i=0; i< hand_to_play.size(); i++) {
                            playerList.get(currentPlayerIdx).getCardsInHand().removeCard(hand_to_play.getCard(i));
                        }
                        print_scoreboard();

                        currentPlayerIdx++;
                        if (currentPlayerIdx == 4){
                        currentPlayerIdx = 0;
                        }
                        this.gui.setActivePlayer(getCurrentPlayerIdx());
                        this.gui.repaint();
                        this.gui.promptActivePlayer();
                    }
                    //The hand cannot beat the hand on table.
                    else{
                        this.gui.printMsg("Not a legal move!!!");
                        this.gui.promptActivePlayer();
                    }
                }
            }
            
            //If the player is the first to play and other 3 player passed, he cannot pass again.
            if(cards_to_play == null && check_pass() == false) { 
                this.gui.printMsg("Not a legal move!!!");
                this.gui.promptActivePlayer();
            }
            else if (cards_to_play == null && check_pass() == true){
                this.gui.printMsg("<" + playerList.get(currentPlayerIdx).getName() + ">" + " {pass}");
                print_scoreboard();
                currentPlayerIdx++;
                if (currentPlayerIdx == 4){
                currentPlayerIdx = 0;
                }
                this.gui.setActivePlayer(getCurrentPlayerIdx());
                this.gui.repaint();
                this.gui.promptActivePlayer();
            }
        }
    }
    /**
	 * This method checks the end of game
	 * @return the status of the game (T or F)
	 */
    public boolean endOfGame() {
        if (playerList.get(currentPlayerIdx).getCardsInHand().size() == 0 ){
            return true;
        }
        return false;
    }
    /**
	 * This method checks if a player can pass
	 * @return can pass or not (T or F)
	 */
    private boolean check_pass(){
        if (this.handsOnTable.size() == 0){
            return false;
        }
        else if (this.playerList.get(currentPlayerIdx).getName() == this.getHandsOnTable().get(this.getHandsOnTable().size()-1).getPlayer().getName()){
            return false;
        }
        return true;
    }
    /**
	 * This method prints the scoreboard and end the program
	 * 
	 */
    private void print_scoreboard(){
        if (this.endOfGame()){
            this.gui.printMsg(" ");
            this.gui.printMsg("Game ends");
            for (int i = 0; i < playerList.size(); i++){
                if (this.getCurrentPlayerIdx() == i){
                    this.gui.printMsg(playerList.get(currentPlayerIdx).getName() + " wins the game.");
                }
                else{
                    this.gui.printMsg(playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards in hand.");
                }
            }
        }
    }
    /**
	 * This method makes and checks a valid hand
	 * @param player the player profile
     * @param cards the card list
	 */
    Hand composeHand(CardGamePlayer player, CardList cards){

        Hand checker;

        checker = new Single(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new Pair(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new Triple(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new StraightFlush(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new Straight(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new Flush(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new FullHouse(player, cards);
        if (checker.isValid()){
            return checker;
        }

        checker = new Quad(player, cards);
        if (checker.isValid()){
            return checker;
        }
        return null;
    }
}
