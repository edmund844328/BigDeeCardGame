/**
 * This class is used model straight hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Straight extends Hand{
    /**
	 * This constructor construct a Straight Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Straight(CardGamePlayer player, CardList cards) {
        super(player, cards);

    }

    /**
	 * This method checks if the hand is valid or not
	 * 
	 * @return valid or not
	 */ 
    public boolean isValid(){
        if (this.size() == 5){
            this.sort();
            Card temp_card = this.getCard(this.size()-1);
            int max_rank = temp_card.getRank();
            if (max_rank == 0){
                max_rank = 13;
            }
            if (max_rank == 1){
                max_rank = 14;
            }

            int count = 0;
            for (int i = this.size()-2 ; i >= 0 ; i--){
                temp_card = this.getCard(i);
                int temp_rank = temp_card.getRank();

                if (temp_rank == 0){
                    temp_rank = 13;
                }
                if (temp_rank == 1){
                    temp_rank= 14;
                }

                if(max_rank-1 == temp_rank){
                    count++;
                    max_rank--;
                }
            }
            if (count == 4){
                return true;
            }
        }
        return false;
    }
    /**
	 * This method checks if tis hand beats another hand
	 * 
     * @param hand the hand to be compared with
	 * @return valid or not
	 */ 
    public boolean beats(Hand hand){
        if (hand.getType() == "Straight" && this.getTopCard().compareTo(hand.getTopCard()) > 0){
            return true;
        }
        return false;
    }


    /**
	 * This method return the name of the hand 
	 * 
	 * @return name of the hand
	 */ 
    public String getType(){
        return "Straight";
    }
}
