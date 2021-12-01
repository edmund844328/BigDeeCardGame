/**
 * This class is for modeling the big two deck
 * 
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class BigTwoDeck extends Deck{
    /**
	 * This method initialize a big two deck
	 */ 
    public BigTwoDeck(){
        this.initialize();
    }
    
    public void initialize(){
        this.removeAllCards();
        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard BTC = new BigTwoCard(i, j);
				this.addCard(BTC);
			}
        }
    }


}
