/**
 * This class is for modeling the big two card
 * 
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class BigTwoCard extends Card{

	/**
	 * This constructor construct a BigTwoCard 
	 * @param suit The suit of the card
     * @param rank The rank suit of the card
	 */ 
    public BigTwoCard(int suit, int rank) {
        super(suit, rank);
    }
	
    /**
	 * This method overidde the compareTo method in card 
	 * @param card the card to be compared
	 */ 
	public int compareTo(Card card) {
        if (this.rank == 1 && card.rank != 1){
            return 1;
        }
        else if (this.rank != 1 && card.rank == 1){
            return -1;
        }
		else if (this.rank == 0 && (card.rank != 1 && card.rank != 0)){
			return 1;
		}
		else if (this.rank != 0 && (card.rank != 1 && card.rank == 0)){
			return -1;
		}
		if (this.rank > card.rank) {
			return 1;
		} else if (this.rank < card.rank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
		
    }

	
}
