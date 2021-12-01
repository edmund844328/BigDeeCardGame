/**
 * This class is used model hands, it is abstract
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public abstract class Hand extends CardList {

    private CardGamePlayer player;

	 /**
	 * This constructor construct a hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Hand(CardGamePlayer player, CardList cards){
        this.player = player;
        for (int i = 0; i < cards.size(); i++){
            this.addCard(cards.getCard(i));
        }
    }

	/**
	 * This method return the player
	 * @return player the player
	 */ 
    public CardGamePlayer getPlayer(){
        return this.player;
    }
    
    /**
	 * This method gets the top card of the hand
	 * 
	 * @return the top card of the hand
	 */ 
    public Card getTopCard(){
		Card max = this.getCard(0);
		for (int i=0; i<this.size();i++){
			if (this.getCard(i).compareTo(max)>0){
				max = this.getCard(i);
			}
		}
		return max;
	}
    
    /**
	 * This method checks if tis hand beats another hand
	 * 
     * @param hand the hand to be compared with
	 * @return valid or not
	 */ 
    public boolean beats(Hand hand){
		if (this.size() == hand.size() && this.getTopCard().compareTo(hand.getTopCard())>0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * This method is to be overidded
	 */ 
    public abstract boolean isValid();
	/**
	 * This method is to be overidded
	 */ 
    public abstract String getType();
 

}
