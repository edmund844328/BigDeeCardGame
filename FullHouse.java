import java.util.ArrayList;
/**
 * This class is used model FullHouse hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class FullHouse extends Hand {

    /**
	 * This constructor construct a FullHouse Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public FullHouse(CardGamePlayer player, CardList cards){
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
            boolean first_three_card_is_top = false;
            boolean last_three_card_is_top = false;

            if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank()){
                first_three_card_is_top = true;
            }

            if (this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
                last_three_card_is_top = true;
            }

            if (first_three_card_is_top){
                if (this.getCard(3).getRank() == this.getCard(4).getRank()){
                    return true;
                }
                else{
                    return false;
                }
            }
            if (last_three_card_is_top){
                if (this.getCard(0).getRank() == this.getCard(1).getRank()){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    /**
	 * This method gets the top card of the hand
	 * 
	 * @return the top card of the hand
	 */ 
    public Card getTopCard(){

        ArrayList<Card> temp_card_list = new ArrayList<Card>();
        boolean first_three_card_is_top = false;
        boolean last_three_card_is_top = false;

        if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank()){
            first_three_card_is_top = true;
        }

        if (this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
            last_three_card_is_top = true;
        }
        if (first_three_card_is_top){
            for (int i = 0; i < 3; i++){
                temp_card_list.add(this.getCard(i));
            }
        }
        else if (last_three_card_is_top){
            for (int i = 2; i < this.size(); i++){
                temp_card_list.add(this.getCard(i));
            }
        }

        Card max = temp_card_list.get(0);
        for (int i = 1; i < temp_card_list.size(); i++){
            if (temp_card_list.get(i).compareTo(max) > 0){
                max = temp_card_list.get(i);
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
    public boolean beats (Hand hand){
        if (hand.getType()== "Straight" || hand.getType()== "Flush"){
			return true;
		}
        else if (hand.getType()== "FullHouse" && this.getTopCard().compareTo(hand.getTopCard()) >0 ){
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
        return "FullHouse";
    }

}
