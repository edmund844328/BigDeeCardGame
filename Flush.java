/**
 * This class is used model Flush hands
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class Flush extends Hand {

    /**
	 * This constructor construct a Flush Hand
	 * @param player The player
     * @param cards The card list
	 */ 
    public Flush(CardGamePlayer player, CardList cards){
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
            int count = 0;
            Card temp_card = this.getCard(this.size()-1);
            for (int i = this.size()-2 ; i >= 0 ; i--){
                Card temp_card2 = this.getCard(i);
                if (temp_card.getSuit() == temp_card2.getSuit()){
                    count++;
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
		if (hand.getType() == "Straight"){
			return true;
		}
        else if (hand.getType()== "Flush" && this.getTopCard().getSuit() > hand.getTopCard().getSuit()){
			return true;
		}
        else if (hand.getType()== "Flush" && this.getTopCard().getSuit() < hand.getTopCard().getSuit()){
			return false;
		}
        else if (hand.getType()== "Flush" && this.getTopCard().getSuit() == hand.getTopCard().getSuit()){
			if (this.getTopCard().compareTo(hand.getTopCard()) > 0){
				return true;
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
        return "Flush";
    }
}
