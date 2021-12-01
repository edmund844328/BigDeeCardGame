/**
 * This class is used model Pair hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Pair extends Hand{

    /**
	 * This constructor construct a Pair Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Pair(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
	 * This method checks if the hand is valid or not
	 * 
	 * @return valid or not
	 */ 
    public boolean isValid(){
        if (this.size() == 2){
            Card temp1 = getCard(0);
            Card temp2 = getCard(1);
            if (temp1.rank == temp2.rank){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
        /**
	 * This method return the name of the hand 
	 * 
	 * @return name of the hand
	 */ 
    public String getType(){
        return "Pair";
    }
}
