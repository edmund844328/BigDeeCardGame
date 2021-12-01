import java.util.ArrayList;
/**
 * This class is used model Quad hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Quad extends Hand{
    /**
	 * This constructor construct Quad Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Quad(CardGamePlayer player, CardList cards) {
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
            boolean first_four_card_is_top = false;
            boolean last_four_card_is_top = false;
            
            if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank()){
                first_four_card_is_top = true;
            }

            if (this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
                last_four_card_is_top = true;
            }

            if (first_four_card_is_top || last_four_card_is_top){
                return true;
            }
            else{
                return false;
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
        boolean first_four_card_is_top = false;
        boolean last_four_card_is_top = false;
            
        if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank()){
                first_four_card_is_top = true;
        }

        if (this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
                last_four_card_is_top = true;
        }

        if (first_four_card_is_top){
            for (int i = 0; i < 4; i++){
                temp_card_list.add(this.getCard(i));
            }
        }
        else if (last_four_card_is_top){
            for (int i = 1; i < this.size(); i++){
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
    public boolean beats(Hand hand){
        if (hand.getType()== "Straight" || hand.getType()== "Flush" || hand.getType()== "FullHouse"){
			return true;
		}
        else if (hand.getType()== "Quad" && this.getTopCard().compareTo(hand.getTopCard()) > 0){
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
        return "Quad";
    }
}
