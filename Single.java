/**
 * This class is used model FullHouse hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Single extends Hand{
    /**
	 * This constructor construct a single Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
     /**
	 * This method checks if the hand is valid or not
	 * 
	 * @return valid or not
	 */ 
    public boolean isValid(){
        if (this.size() == 1){
            return true;
        }
        else{
            return false;
        }

    }
    /**
	 * This method return the name of the hand 
	 * 
	 * @return name of the hand
	 */ 
    public String getType(){
        return "Single";
    }
}
