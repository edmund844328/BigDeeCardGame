import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This class is used to start the GUI
 * @author Tsang Edmund Chiu Yat
 * @version 1
 */
public class BigTwoGUI implements CardGameUI{
    private BigTwo game;
    //a boolean array indicating which cards are being selected
    private boolean[] selected;
    //an integer specifying the index of the active player
    private int activePlayer;
    //he main window of the application
    private JFrame frame;
    //a panel for showing the cards of each player and the cards played on the table
    private JPanel bigTwoPanel;
    //a Play button for the active player to play the selected cards
    private JButton playButton;
    //a Play button for the active player to play the selected cards
    private JButton passButton;
    //a text area for showing the current game status as well as end of game messages
    private JTextArea msgArea;
    //A text area for showing chat messages sent by the players
    private JTextArea chatArea;
    //A text field for players to input chat messages.
    private JTextField chatInput;

    /**
	 * This constructor builds the gui 
	 * @param game The BigTwo object passed from the BtgTwo class
	 */
    public BigTwoGUI(BigTwo game){
        this.game = game;
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,2,2,2));
        this.setActivePlayer(((BigTwo)game).getCurrentPlayerIdx());
        this.selected = new boolean[13];
        
        //The Green pannel for the cards and avatar
        this.bigTwoPanel = new BigTwoPanel();
        this.bigTwoPanel.setLayout(new BorderLayout());
        this.bigTwoPanel.setBackground(new Color(61,122,43));
        // this.bigTwoPanel.setOpaque(true);
		// this.bigTwoPanel.setVisible(true);
		// this.bigTwoPanel.setLocation(0, 0);

        //Pannel for the two button and the two button rh
        JPanel buttonpanel = new JPanel();
        this.playButton = new JButton("Play");
        this.passButton = new JButton("Pass");
        buttonpanel.add(this.playButton);
        buttonpanel.add(this.passButton);
        this.bigTwoPanel.add(buttonpanel,BorderLayout.SOUTH);

        //Listener for LeftPanel
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		bigTwoPanel.addMouseListener(new BigTwoPanel());

        //Right panel for dealing with the message area and caht area
        JPanel rightpanel = new JPanel();
        rightpanel.setLayout(new GridLayout(2,1,2,2));

        //toprightPane for dealing with the game message area
        JPanel toprightPanel = new JPanel(new BorderLayout());
        //The game message area
        this.msgArea = new JTextArea();
        this.msgArea.setBackground(Color.PINK);
        msgArea.setEditable(false);
        JScrollPane textScroller = new JScrollPane(this.msgArea);
	    textScroller.setVerticalScrollBarPolicy(
	    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    textScroller.setHorizontalScrollBarPolicy(
	    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        toprightPanel.add(textScroller,BorderLayout.CENTER);

        //bottomright for dealing with the chat
        JPanel bottomright = new JPanel(new BorderLayout());
        //The chat
        this.chatArea =new JTextArea();
        this.chatArea.setBackground(Color.CYAN);
	    JScrollPane chatscroller = new JScrollPane(this.chatArea);
	    this.chatArea.setEditable(false);
	    this.chatArea.setFont(new Font("Arial",Font.PLAIN,20));
	    chatscroller.setVerticalScrollBarPolicy(
	    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    chatscroller.setHorizontalScrollBarPolicy(
	    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    bottomright.add(chatscroller,BorderLayout.CENTER);

        //bottombottomright for dealing with the text input and send button
        JPanel bottombottomright = new JPanel();
        this.chatInput = new JTextField(40);	
	    bottombottomright.add(this.chatInput,BorderLayout.WEST);

        JButton send = new JButton("send");
        send.addActionListener(new SendButtonListener());
	    bottombottomright.add(send,BorderLayout.EAST);

        //Building up all component together
        bottomright.add(bottombottomright,BorderLayout.SOUTH);
        rightpanel.add(toprightPanel);
        rightpanel.add(bottomright);

        //Just for the sake of following the example
        JMenuBar bar = new JMenuBar();
		JMenuItem game_button = new JMenuItem("Game");
		JMenuItem messgae_button = new JMenuItem("Messge");
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new RestartMenuItemListener());
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitMenuItemListener());
        frame.setJMenuBar(bar);
		bar.add(game_button);
		bar.add(messgae_button);
        bar.add(restart);
        bar.add(quit);

        frame.add(this.bigTwoPanel); //panel on left (where cards are updated)
		frame.add(rightpanel);  // panel on left(where chat and text are updated)
		frame.setMinimumSize(new Dimension(1100,1000));
		frame.setVisible(true);
    }

    /**
	 * This method set the active player as given
	 * @param activePlyer Integer that indicates the active player
	 */
    public void setActivePlayer(int activePlayer){
        this.activePlayer = activePlayer;
    }

    /**
	 * This method set the refresh the bigTwoPannel
	 *  
	 */
    public void repaint(){
        bigTwoPanel.repaint();
    }
    /**
	 * This method append the message from BigTwo class to the msgArea
	 * @param messgae String of mesage
	 */
    public void printMsg(String message){
        msgArea.setFont(new Font("TimesRoman", Font.BOLD, 15));
        msgArea.append(message +"\n");
    }

    /**
	 * This method clear all message from the MsgArea
     * 
	 */
    public void clearMsgArea(){
        this.msgArea.setText("");
    }

    /**
	 * This resets everything from MsgArea to the button
     * 
	 */
    public void reset(){
        for (int i=0; i < selected.length; i++) {
            selected[i]=false;
        }
        this.clearMsgArea();
        this.chatArea.setText("");
        this.enable();
    }
    /**
	 * This enable every button
     * 
	 */
    public void enable(){
        playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
        chatInput.setEnabled(true);
    }
    /**
	 * This disable every button
     * 
	 */
    public void disable(){
        this.playButton.setEnabled(false);
		this.passButton.setEnabled(false);
		this.bigTwoPanel.setEnabled(false);
        chatInput.setEnabled(false);
    }

    /**
	 * This tells the plyer it is their turn by sending a message to the msgArea
     * 
	 */
    public void promptActivePlayer(){
        String player_name = game.getPlayerList().get(activePlayer).getName();
        printMsg(player_name + " it is your turn !!");
    }

    /**
    * This class prints all the avatar and cards on the table
    * This class also implements the MouseListener and can record mouse movement
    * Ths class is also a extension of JPanel
    * 
    * @author Tsang Edmund Chiu Yat
    * @version 1
    */
    class BigTwoPanel extends JPanel implements MouseListener{
        /**
		 * Method for mainly drawing the graphic, this method will automatically call it self
		 * 
		 * @param g the graphic
		 * 		
		 */
        public void paintComponent(Graphics g){
            //for checking the end of game and prevent player to interact once the game is ended
            if (game.endOfGame() == true){
                playButton.setEnabled(false);
                passButton.setEnabled(false);
                bigTwoPanel.setEnabled(false);
                chatInput.setEnabled(false);
            }
            else if (game.endOfGame() == false){
                playButton.setEnabled(true);
                passButton.setEnabled(true);
                bigTwoPanel.setEnabled(true);
                chatInput.setEnabled(true);
            }           
            //for storing image
            ArrayList<Image> avatar = new ArrayList<Image>();
            avatar.add(new ImageIcon("Avatar/blue.gif").getImage());
            avatar.add(new ImageIcon("Avatar/dog.gif").getImage());
            avatar.add(new ImageIcon("Avatar/green.gif").getImage());
            avatar.add(new ImageIcon("Avatar/pig.gif").getImage());
            super.paintComponent(g);
            //for drawing perfect line
            int k = 8;
            for (int i =1; i <5; i++){
            g.drawLine(0, ((this.getHeight()/5)-k)*i, this.getWidth(), ((this.getHeight()/5)-k)*i);
            }
            //for adding avatar
            for (int i = 0; i <4; i++){
            g.drawImage(avatar.get(i), this.getWidth()/100-7, -137+ this.getHeight()/5+ 178*i,this);
            // g.drawImage(avatar.get(0), this.getWidth()/100-7, this.getHeight()/5+42,this);
            // g.drawImage(avatar.get(1), this.getWidth()/100-7, this.getHeight()/70+38,this);
            // g.drawImage(avatar.get(3), this.getWidth()/100-7, this.getHeight()/3+97,this);
            // g.drawImage(avatar.get(2), this.getWidth()/100-7, this.getHeight()/2+119,this);
            }
            //for adding "Player 1", "Player 2" etc
            g.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
            g.setColor(new Color(255,165,0));
            for (int i =0 ; i <4; i++){
                g.drawString(game.getPlayerList().get(i).getName(), this.getWidth()/100-5,  + 23*(i+1) +this.getHeight()/6*i);

            }
            //for indicating the hand played by player
            if (game.getHandsOnTable().size()==0) {//no cards on table.
                g.drawString("Empty", this.getWidth()/100-5, this.getHeight()/2+280);
            }
            else if (game.getHandsOnTable().size()!=0){
                Hand lastHand;
                if(game.getHandsOnTable().isEmpty()){
                    lastHand = null;
                }
                else{
                    lastHand = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
                    String output = "Played by " + lastHand.getPlayer().getName() + " [" + game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getType() + "]";
                    g.drawString(output, this.getWidth()/100-5, this.getHeight()/2+280);
                }
            }
            //The back of the card
            Image cardback = new ImageIcon("cards/b.gif").getImage();
            //Storing the card into an array
            String rank = null;
            String suit = null;
            Image cardKeeper[][] = new Image[4][13];
            for (int i = 1; i < 14; i++){
                if (i == 1){
                    rank = "a";
                }
                else if (i == 10){
                    rank = "t";
                }
                else if (i == 11){
                    rank = "j";
                }
                else if (i == 12){
                    rank = "q";
                }
                else if (i == 13){
                    rank = "k";
                }
                else{
                    rank = Integer.toString(i);
                }
                for (int j = 0; j < 4; j++){
                    if (j == 0){
                        suit = "d";
                        cardKeeper[j][i-1] = new ImageIcon("cards/"+rank+suit+".gif").getImage();
                    }
                    else if (j == 1){
                        suit = "c";
                        cardKeeper[j][i-1] = new ImageIcon("cards/"+rank+suit+".gif").getImage();
                    }
                    else if (j == 2){
                        suit = "h";
                        cardKeeper[j][i-1] = new ImageIcon("cards/"+rank+suit+".gif").getImage();
                    }
                    else if (j == 3){
                        suit = "s";
                        cardKeeper[j][i-1] = new ImageIcon("cards/"+rank+suit+".gif").getImage();
                    }
                }
            }

            //for drawing the cards
            //Go through ever players card, find the active player and review his card
            for (int i = 0; i < game.getNumOfPlayers(); i++){
                int card_separation = 0;
                if (i == activePlayer){
                    for (int j = 0; j <game.getPlayerList().get(activePlayer).getNumOfCards(); j++){
                        Card temp = game.getPlayerList().get(activePlayer).getCardsInHand().getCard(j);
                        
                        if (selected[j] == true){
                        g.drawImage(cardKeeper[temp.getSuit()][temp.getRank()], this.getWidth()/4 + card_separation, (50-i*2)+this.getHeight()/5*i, this);
                        card_separation +=this.getWidth()/20;
                        }
                        
                        else{
                        g.drawImage(cardKeeper[temp.getSuit()][temp.getRank()], this.getWidth()/4 + card_separation, (50-i*2)+this.getHeight()/5*i+ 10, this);
                        card_separation +=this.getWidth()/20;
                        }
                    }
                }
                else{
                    for (int j = 0; j <game.getPlayerList().get(i).getNumOfCards(); j++){
                        g.drawImage(cardback, this.getWidth()/4 + card_separation, (50-i*2)+this.getHeight()/5*i + 10, this);
                        card_separation +=this.getWidth()/20;
                    }
                }
            }
            if (game.getHandsOnTable().size() > 0){
                int card_separation = 0;
                Hand lastHand = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
                for (int i = 0; i < lastHand.size(); i++){
                    Card temp = lastHand.getCard(i);
                    g.drawImage(cardKeeper[temp.getSuit()][temp.getRank()], this.getWidth()/50-8 + card_separation, 10+this.getHeight()/5*4, this);
                    card_separation +=this.getWidth()/20;
                }             
            }
        }
        
        /**
		 * Method for handling the mouse pressing event.
		 * 
		 * @param event
		 * 	
		 */
        public void mousePressed(MouseEvent event) {
            int x_coordinate = event.getX();
            int y_coordinate = event.getY();
            //for debug and find location
            System.out.println("x coordinate: " + x_coordinate);
            // System.out.println("y coordinate: " + y_coordinate);
            System.out.println(((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/3787));
            // System.out.println(bigTwoPanel.getWidth()/5 + 26);
            // System.out.println(bigTwoPanel.getWidth()/5 + 350);


            if( ((BigTwo)game).getCurrentPlayerIdx() == activePlayer){
                if (activePlayer == 0){
                    if (y_coordinate <= (bigTwoPanel.getHeight()/5)*activePlayer +154 && y_coordinate >= (bigTwoPanel.getHeight()/5)*activePlayer -23){

                        for (int i=0; i < game.getPlayerList().get(activePlayer).getNumOfCards(); i++){          
                            int first_x = ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/3787) + ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518 +1)*i;    
                            if (i < 12){
                                if((y_coordinate > 60 && y_coordinate < 155) && (x_coordinate > first_x && x_coordinate < first_x + (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                            else{
                                if((y_coordinate > 60 && y_coordinate < 155) && (x_coordinate > first_x && x_coordinate < first_x + 70)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                        }                      
                    }
                }

                if (activePlayer == 1){
                    if (y_coordinate <= (bigTwoPanel.getHeight()/5)*activePlayer +154 && y_coordinate >= (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518){
                        for (int i=0; i < game.getPlayerList().get(activePlayer).getNumOfCards(); i++){                           
                            int first_x = ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/3787) + ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518 +1)*i;
                            if(i<12){
                                if((y_coordinate > 245 && y_coordinate < 338) && (x_coordinate > first_x && x_coordinate < first_x + (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                            else{
                                if((y_coordinate > 245 && y_coordinate < 338) && (x_coordinate > first_x && x_coordinate < first_x + 70)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                        }
                    }
                }

                if (activePlayer == 2){
                    if (y_coordinate <= (bigTwoPanel.getHeight()/5)*activePlayer +154 && y_coordinate >= (bigTwoPanel.getHeight()/5)*activePlayer -23){
                        for (int i=0; i < game.getPlayerList().get(activePlayer).getNumOfCards(); i++){                           
                            int first_x = ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/3787) + ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518 +1)*i;
                            if (i<12){
                                if((y_coordinate > 430 && y_coordinate < 523) && (x_coordinate > first_x && x_coordinate < first_x + (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                            else{
                                if((y_coordinate > 430 && y_coordinate < 523) && (x_coordinate > first_x && x_coordinate < first_x + (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }                          
                        }
                    }
                }

                if (activePlayer == 3){
                    if (y_coordinate <= (bigTwoPanel.getHeight()/5)*activePlayer +154 && y_coordinate >= (bigTwoPanel.getHeight()/5)*activePlayer -23){
                        for (int i=0; i < game.getPlayerList().get(activePlayer).getNumOfCards(); i++){                           
                            int first_x = ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/3787) + ((bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518 +1)*i;
                            if (i < 12){
                                if((y_coordinate > 615 && y_coordinate < 709) && (x_coordinate > first_x && x_coordinate < first_x + (bigTwoPanel.getWidth()*bigTwoPanel.getHeight())/19518)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                            else{
                                if((y_coordinate > 615 && y_coordinate < 709) && (x_coordinate > first_x && x_coordinate < first_x + 70)){
                                    if (selected[i] == false){
                                        selected[i] = true;
                                    }
                                    else{
                                        selected[i] = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //for debug
            //System.out.println(Arrays.toString(selected)); 
            if (game.endOfGame() == true){
                playButton.setEnabled(false);
				passButton.setEnabled(false);
				bigTwoPanel.setEnabled(false);
            }
            else if (game.endOfGame() == false){
                playButton.setEnabled(true);
				passButton.setEnabled(true);
				bigTwoPanel.setEnabled(true);
            }
            bigTwoPanel.repaint();
		}

        /**
		 * For override 
		 */
        public void mouseClicked(MouseEvent event){
        }
        /**
		 * For override 
		 */
        public void mouseReleased(MouseEvent event) {
		}
        /**
		 * For override 
		 */
        public void mouseEntered(MouseEvent event) {
		}
        /**
		 * For override 
		 */
		public void mouseExited(MouseEvent event) {	
		}
				
    }
    /**
	 * An action listener for the play button.
	 * 
	 * @author Edmund Tsang
	 *
	 */
    class PlayButtonListener implements ActionListener{
        /**
         * An action listener for the play button.
         * 
         * @param event the event for clicking the play button
         *
         */
        public void actionPerformed(ActionEvent event){
            int card_array_size = 0;
            for (int i = 0; i < selected.length; i++){
                if (selected[i] == true){
                    card_array_size++;
                }
            }
            int[] cardIdx = null;
            if (card_array_size > 0){
                cardIdx = new int[card_array_size];
                int index = 0;
                for(int i = 0; i < selected.length; i++){
                    if (selected[i] == true){
                        cardIdx[index] = i;
                        index++;
                    }
                }
            }
            // System.out.println(Arrays.toString(cardIdx)); 
            if (cardIdx != null){
                game.makeMove(game.getCurrentPlayerIdx(), cardIdx);
            }
            //reset selected
            for (int i=0; i < selected.length; i++) {
                selected[i]=false;
            }
        }
    }
    
    /**
	 * An action listener for the pass button.
	 * 
	 * @author Edmund Tsang
	 *
	 */
    class PassButtonListener implements ActionListener{
        /**
		 * Method for clicking the pass button, it will trigger the makeMove in BigTwo.
		 * 
		 * @param event the event for clicking pass quit button
		 * 	
		 */
        public void actionPerformed(ActionEvent event) {
            game.makeMove(activePlayer, null);
        }       
    }

    /**
	 * An action listener for the RestartMenu button.
	 * 
	 * @author Edmund Tsang
	 *
	 */
    class RestartMenuItemListener implements ActionListener{
        /**
		 * Method for clicking the restart button.
		 * 
		 * @param event the event for clicking the restart button
		 * 	
		 */
        public void actionPerformed(ActionEvent event) {
            reset();
            BigTwoDeck deckforgame = new BigTwoDeck();
            game.start(deckforgame);
        }
    }

    /**
	 * An action listener for the quit button.
	 * 
	 * @author Edmund Tsang
	 *
	 */
    class QuitMenuItemListener implements ActionListener{
        /**
         * An action listener for the quit button and it will terminate the program.
         * 
         * @param event the event for clicking the quit button
         *
         */
        public void actionPerformed(ActionEvent event) {
            System.exit(1);
        }
    }

    /**
	 * An action listener for the send button.
	 * 
	 * @author Edmund Tsang
	 *
	 */
    class SendButtonListener implements ActionListener{
        /**
         * The method when send button is click is trigged
         * it will use the append method and append the text to the chatArea
         * @param event the event for clicking send button
         */
        public void actionPerformed(ActionEvent event) {
            String input = chatInput.getText();
            chatArea.append(input+'\n');
            chatInput.setText("");
        }
    }
    
}