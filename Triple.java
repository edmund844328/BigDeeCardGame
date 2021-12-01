/**
 * This class is used model triple hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Triple extends Hand{

    /**
	 * This constructor construct a triple Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Triple(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
	 * This method checks if the hand is valid or not
	 * 
	 * @return valid or not
	 */
    public boolean isValid(){
        if (this.size() == 3){
            Card temp1 = getCard(0);
            Card temp2 = getCard(1);
            Card temp3 = getCard(2);
            if (temp1.rank == temp2.rank && temp2.rank == temp3.rank){
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
        return "Triple";
    }
}
