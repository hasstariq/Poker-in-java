Player Class______________________________________________________
package pokerAssistant;

import java.util.*;

public class Player {
	ArrayList<Card> hand = new ArrayList<Card>(); 
	float balance = 0; 									// Playable cash
	float bet = 0;										// Total player funds in pot
	boolean bigBlind = false;	
	boolean smallBlind = false;
	boolean dealer = false;		
	String status = "wait";								// fold, call, check, raise, wait, allIn
	
	
	public Player(ArrayList <Card> handIn,float balanceIn,int betIn, boolean bigBlindIn,boolean dealerIn,String statusIn) {
		/* =============================================================
		 * Constructor Class for Player Object
		 * =============================================================
		 * Preconditions:
		 * 		hand - ArrayList<Card>
		 * 		balance - float
		 * 		bet - int
		 * 		bigBlind - boolean
		 * 		smallBlind - boolean
		 * 		dealer - boolean
		 * 		status - string (fold, call, check, raise, wait, allIn)
		 * 
		 * Postconditions:
		 * 		All values for this Player object are initialized
		 * =============================================================
		 */ 
		
		hand = handIn;
		balance = balanceIn;
		bet = betIn;
		bigBlind = bigBlindIn;
		dealer = dealerIn;
		status = statusIn;
		
		return;
	}
	
	
	public void add_card(ArrayList<Card> cardsInPlay) {
		/* =============================================================
		 * Adds two cards to this Player's hand
		 * =============================================================
		 * Preconditions:
		 * 		cardsInPlay - ArrayList<Card> containing all cards in circulation
		 * Postconditions:
		 * 		Player.hand receives two new cards
		 * =============================================================
		 */ 
		this.hand.add(TableFunctions.dealCard(cardsInPlay));
		this.hand.add(TableFunctions.dealCard(cardsInPlay));
		
		return;
	}
	
	
	public void print() {
		/* =============================================================
		 * Prints all the cards in a Player object's hand
		 * =============================================================
		 * Preconditions:
		 * 
		 * Postconditions:
		 * 		All cards are printed to console
		 * =============================================================
		 */ 
		
		for (int j = 0; j < this.hand.size(); j++) {
			System.out.print("(" + this.hand.get(j).suit + ", ");
			System.out.print(this.hand.get(j).face_value + ")     ");
		}
		
		System.out.println("");
		return;
	}

}

Card Class____________________________________________________________

package pokerAssistant;

public class Card {

	int suit;			// 0 = Hearts, 1 = Diamond, 2 = Clubs, 3 = Spades
	int face_value;		// Ace = 13
	
	// Constructor for Card class
	public Card(int suitIn, int face_valueIn) {
		suit = suitIn;
		face_value = face_valueIn;
	}
	
}


Table Class_____________________________________________________________

package pokerAssistant;

import java.util.*;

// Each player is an object

public class Table {
	
	public static void main(String argsp[]) {
		// ================================  Setup Code  =====================================
		
		// Set starting funds for players
		int START_BALANCE = 1000;
		
		// Prevent duplicates by storing cards
		ArrayList<Card> cardsInPlay = new ArrayList<Card>();
		
		// Create player objects
		// Player(hand, Starting balance, bet,  isBig, isDeader, move)
		Player bot1 = new Player(new ArrayList<Card>(), START_BALANCE, 0, false, false, ""); 
		Player bot2 = new Player(new ArrayList<Card>(), START_BALANCE, 0, false, false, "");
		Player bot3 = new Player(new ArrayList<Card>(), START_BALANCE, 0, false, false, "");
		Player player = new Player(new ArrayList<Card>(), START_BALANCE, 0, false, false, "");
		
		// Create a Player Object called 'table' to hold cards on table top
		Player table = new Player(new ArrayList<Card>(), 0, 0, false, false, "");
		
		// Order of play
		ArrayList<Player> OrderOfPlay = new ArrayList<Player>();
	
		// Deal cards
		player.add_card(cardsInPlay);
		bot1.add_card(cardsInPlay);
		bot2.add_card(cardsInPlay);
		bot3.add_card(cardsInPlay);
		
		// Add players to the OrderOfPlay
		OrderOfPlay.add(player);
		OrderOfPlay.add(bot1);
		OrderOfPlay.add(bot2);
		OrderOfPlay.add(bot3);
		
		// Open new instance of the table interface
		TableGUI.drawTable();
		
		// Draw CPU profile pictures
		TableGUI.setCPU(1,1);
		TableGUI.setCPU(2,2);
		TableGUI.setCPU(3,3);
		
		// Draw CPU and player balances
		TableGUI.setPot(0,0);				// Pot
		TableGUI.setPot(1,START_BALANCE);	// Bot 1
		TableGUI.setPot(2,START_BALANCE);	// Bot 2
		TableGUI.setPot(3,START_BALANCE);	// Bot 3
		TableGUI.setPot(4,START_BALANCE);	// Player
		
		// Draw Player Cards
		TableGUI.setCard(6,player.hand.get(0).suit ,player.hand.get(0).face_value );
		TableGUI.setCard(7,player.hand.get(1).suit ,player.hand.get(1).face_value );
		
		
		
		
		
		
		
		// =================================  Main Game Code  ================================
		
		
		
		Player currentPlayer = null;
		int turnNum;
		boolean betting = true;
		Scanner userInput = new Scanner(System.in);		
		float pot = 0;
		float bet = 0;
		
		// Loop through all 5 betting rounds of 1 hand
		for (int round = 0; round < 4; round++) {
			
			// Reset Player Turn
			turnNum = 0;
			
			//TESTING
			System.out.println("");  
			System.out.println("==================== NEW BETTING ROUND ====================");
			System.out.print("Table:    ");
			table.print();
			
			// Loop through each player's turn 
			betting = true;
			while (betting == true || turnNum < OrderOfPlay.size()) {
				
				// If we have completed a loop of the table, reset the turnNum
				if (turnNum == OrderOfPlay.size()) {
					turnNum = 0;
				}
				
				// Set Current Player
				currentPlayer = OrderOfPlay.get(turnNum);
				
				
				// If its the player's turn
				if (currentPlayer == player && ! currentPlayer.status.contentEquals("fold")) {
					System.out.print("PLAYER TURN: ");
					currentPlayer.print();
					
					// Get action from player
					System.out.println("");
					System.out.println("How would you like to proceed? (fold, call, check, raise, allIn)");
					currentPlayer.status =  userInput.nextLine();
				

				// If its the bot's turn to play
				} else if (currentPlayer.status != "fold") {
					System.out.println("");
					System.out.println("BOT TURN: ");
					
					// Bots Class uses a separate function if the table is empty
					if (round == 0) {
						//System.out.println("---- TEST #1 ----");
					} else {
						//System.out.println("---- TEST #2 ----");
					}
					
					currentPlayer.status = "call";
				}
				
				
				// Execute user/bot action
				if (currentPlayer.status.contentEquals("raise")) {
					// Get amount from player
					System.out.println("How much do you want to bet?");
					currentPlayer.bet = userInput.nextInt();
					userInput.nextLine(); // remove the \n that is not consumed by scanner.nextInt()
					
					// Update 'bet' for this round
					bet = currentPlayer.bet;
					
				} else if (currentPlayer.status.contentEquals("call")) {
					// Update 'bet' for this round
					currentPlayer.bet = bet;
					
				} else if (currentPlayer.status.contentEquals("check")) {
					// Nothing happens to the player's balance with a check move
					
				} else if (currentPlayer.status.contentEquals("allIn")) {
					// Update 'bet' for this round
					currentPlayer.bet =  currentPlayer.balance;
					
				} else if (currentPlayer.status.contentEquals("fold")) {
					// Nothing happens to the player's balance with a check move
					
				} 
				
				
				// Repeat input back to user
				System.out.println("STATUS:" + currentPlayer.status + "   BET: " + currentPlayer.bet + "   BALANCE:" + currentPlayer.balance);
				
				// Update pot
				pot += currentPlayer.bet;
				TableGUI.setPot(0, pot);
				
				// Determine if betting is finished
				betting = false;
				for (int i = 0; i < OrderOfPlay.size(); i++) {
					
					// For a hand to finish, each player must have the same investment into the pot
					if ((OrderOfPlay.get(i).bet != currentPlayer.bet) && (currentPlayer.status != "fold") && (currentPlayer.status != "allIn")) {
						betting = true;
					} 
				}
				
				// Increment Counter
				turnNum++;
			}
			
			// Deal cards to table top
			if (round == 0) {
				table.hand.add(TableFunctions.dealCard(cardsInPlay));
				table.hand.add(TableFunctions.dealCard(cardsInPlay));
				table.hand.add(TableFunctions.dealCard(cardsInPlay));
				
				TableGUI.setCard(1,table.hand.get(0).suit ,table.hand.get(0).face_value);
				TableGUI.setCard(2,table.hand.get(1).suit ,table.hand.get(1).face_value);
				TableGUI.setCard(3,table.hand.get(2).suit ,table.hand.get(2).face_value);
				
			} else if (round == 1) {
				table.hand.add(TableFunctions.dealCard(cardsInPlay));
				TableGUI.setCard(4,table.hand.get(3).suit ,table.hand.get(3).face_value);
				
			} else if (round == 2) {
				table.hand.add(TableFunctions.dealCard(cardsInPlay));
				TableGUI.setCard(5,table.hand.get(4).suit ,table.hand.get(4).face_value);
				
			} else {
				// No card is dealt after last hand
				
			}
		}
		
		// Close Input Scanner
		userInput.close();
	}	
	
}







Table Functions_____________________________________________________________

package pokerAssistant;
import java.util.ArrayList;

public class TableFunctions {
	
	
	public static Card dealCard(ArrayList <Card> cards_in_play) {
		/* =============================================================
		 * Generates a new, randomly selected card that is not already in
		 * circulation and deals it to the Player
		 * =============================================================
		 * Preconditions:
		 * 		cards_in_play - An ArrayList<Card> with all cards in 
		 * 						circulation
		 * 
		 * Postconditions:
		 * 		A card is added to both the players hand and the 
		 * 		cards_in_play list
		 * =============================================================
		 */ 
		
		// Instantiate Variables
		Card card = new Card(0, 0);
		int flag = 0;
		
		while (flag == 0) {
			// Get random suit and face
			int suit = 0 + (int)(Math.random() * 3);
			int face = 0 + (int)(Math.random() * 13);
			
			// Update card values
			card.suit = suit;
			card.face_value = face;
			
			// Check if cards_in_play is empty
			if (cards_in_play.size() == 0) {
				flag = 1;
				
			// If not empty, loop through cards_in_play
			} else {
				for (int i = 0; i < cards_in_play.size(); i++) {
					
					Card temp = cards_in_play.get(i);
					
					// Make sure the card hasn't been dealt
					if ((card.suit == temp.suit) & (card.face_value == temp.face_value)) {
						flag = 0;
					} else {
						flag = 1;
					}
				}
			}
		}
		
		cards_in_play.add(card);
		
		return card;
	}
	
	
	
	
}


Table GUI (Menu Assets Required) https://drive.google.com/file/d/1w6kvPd2_mDBdKMBUGUd1HluGSOcj7JLX/view?usp=sharing
New table image:



/*
 * GUI for the Poker Assistant app.
 * 
 * Functions:
 * 
 * drawTable(): Initializes the blank table UI. Always use this first.
 * 
 * setCard(int,int,int): Modifies a card on the table.
 * 	-int 1 = card number (1-7) (6, 7 are player cards)
 * 	-int 2 = card suit (0-4)
 * 		0 = No suit/erase card suit
 * 		1 = club
 * 		2 = diamond
 * 		3 = heart
 * 		4 = spade
 * 	-int 3 = number on card (0-13)
 * 		0 = No number/erase card number
 * 		1 = Ace
 * 		2-10 = Rank
 * 		11 = Jack
 * 		12 = Queen
 * 		13 = King
 * 
 * setCPU(int, int): Modifies a CPU's avatar. In the future, 0 will represent no player.
 * 	-int 1 = CPU number (1-3)
 * 	-int 1 = Avatar number (1-3)
 * 
 * setPot(int, int): Sets the value of a pot.
 * 	-int 1 = Pot ID (0-3)
 * 		0 = Pot
 * 		1 = CPU1 Pot
 * 		2 = CPU2 Pot
 * 		3 = CPU3 Pot
 * 		4 = Player Pot
 * 	-int 2 = Value to set
 * 
 * addPot(int, int): Adds a value to a pot.
 * 	-int 1 = Pot ID (0-3)
 * 		0 = Pot
 * 		1 = CPU1 Pot
 * 		2 = CPU2 Pot
 * 		3 = CPU3 Pot
 * 		4 = Player Pot
 * 	-int 2 = Value to add
 * 	
 * subPot(int, int): Subtracts a value to a pot.
 * 	-int 1 = Pot ID (0-3)
 * 		0 = Pot
 * 		1 = CPU1 Pot
 * 		2 = CPU2 Pot
 * 		3 = CPU3 Pot
 * 		4 = Player Pot
 * 	-int 2 = Value to subtract
 * 
 * clearTable(): Clears the table of all values.
 * 
 * clearTableCPU(): Same as clearTable(), but also clears all avatars. Currently the same as clearTable().
 * 
 * clearCards(): Clears all cards from the table.
 * 
 * setTabe(): Refreshes the table GUI to update it with new info. Used by all other functions automatically.
 * 
 */


package pokerAssistant;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.lang.Object;
import javax.swing.*;

public class TableGUI extends JPanel{
	
	static JFrame f = new JFrame("Table");
	static JPanel table = new JPanel();
	static BufferedImage img1 = null;
	static BufferedImage img2 = null;
	static BufferedImage img3 = null;
	static BufferedImage img4 = null;
	static BufferedImage img5 = null;
	static BufferedImage imgP1 = null;
	static BufferedImage imgP2 = null;
	static BufferedImage imgTable = null;
	static String num1 = "";
	static String num2 = "";
	static String num3 = "";
	static String num4 = "";
	static String num5 = "";
	static String numP1 = "";
	static String numP2 = "";
	static float pot = 0;
    static Color suitC1 = Color.BLACK;
    static Color suitC2 = Color.BLACK;
    static Color suitC3 = Color.BLACK;
    static Color suitC4 = Color.BLACK;
    static Color suitC5 = Color.BLACK;
    static Color suitCP1 = Color.BLACK;
    static Color suitCP2 = Color.BLACK;
	static BufferedImage cpu1 = null;
	static BufferedImage cpu2 = null;
	static BufferedImage cpu3 = null;
	static float cpu1Pot = 0;
	static float cpu2Pot = 0;
	static float cpu3Pot = 0;
	static float playerPot = 0;
	
	public static void main(String args[]) {
		drawTable();
		setCard(1,2,12);
		setCard(2,3,4);
		setCard(3,1,1);
		setCard(4,4,11);
		setCard(5,1,13);
		setCard(6,1,13);
		setCard(7,1,13);
		setCPU(1,1);
		setCPU(2,2);
		setCPU(3,3);
		setPot(0,400);
		setPot(1,40);
		setPot(2,4000);
		setPot(3,40000);
		setPot(4,696969);
		addPot(0,250);
		subPot(0,10);
		//clearTableCPU();
		
	}
	
	public static JFrame drawTable() {
		
		try {
		    imgTable = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\table.png"));
		} catch (IOException e) {
		}
	    System.out.println(System.getProperty("user.dir")+"\\src\\resources\\table.png");
		
		final BufferedImage image = imgTable;
		
        table = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };

        f.add(table, BorderLayout.CENTER); 
        f.setSize(645, 505); 
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true); 
		
        return f;
	}
	
	public static JFrame setCard(int cardNum, int suit, int num) {
		
		String suitS = "";
		
			switch(suit) {
			case 0:
				suitS = "";
				break;
			case 1:
				suitS = "club";
				break;
			case 2:
				suitS = "diamond";
				break;
			case 3:
				suitS = "heart";
				break;
			case 4:
				suitS = "spade";
				break;
			}
		
	    switch(cardNum) {
	    case 1:
	    	try {
	    		if(suit != 0)
	    			img1 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			img1 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitC1 = Color.RED;
		    else
		    	suitC1 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		num1 = "";
	    		break;
	    	case 14:
	    		num1 = "A";
	    		break;
	    	case 11:
	    		num1 = "J";
	    		break;
	    	case 12:
	    		num1 = "Q";
	    		break;
	    	case 13:
	    		num1 = "K";
	    		break;
	    	default:
		    	num1 = Integer.toString(num);
		    	break;
	    	}
	    	break;
	    case 2:
	    	try {
	    		if(suit != 0)
	    			img2 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			img2 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitC2 = Color.RED;
		    else
		    	suitC2 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		num2 = "";
	    		break;
	    	case 14:
	    		num2 = "A";
	    		break;
	    	case 11:
	    		num2 = "J";
	    		break;
	    	case 12:
	    		num2 = "Q";
	    		break;
	    	case 13:
	    		num2 = "K";
	    		break;
	    	default:
		    	num2 = Integer.toString(num);
		    	break;
	    	}
	    	break;
	    case 3:
	    	try {
	    		if(suit != 0)
	    			img3 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			img3 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitC3 = Color.RED;
		    else
		    	suitC3 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		num3 = "";
	    		break;
	    	case 14:
	    		num3 = "A";
	    		break;
	    	case 11:
	    		num3 = "J";
	    		break;
	    	case 12:
	    		num3 = "Q";
	    		break;
	    	case 13:
	    		num3 = "K";
	    		break;
	    	default:
		    	num3 = Integer.toString(num);
		    	break;
	    	}
	    	break;
	    case 4:
	    	try {
	    		if(suit != 0)
	    			img4 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			img4 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitC4 = Color.RED;
		    else
		    	suitC4 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		num4 = "";
	    		break;
	    	case 14:
	    		num4 = "A";
	    		break;
	    	case 11:
	    		num4 = "J";
	    		break;
	    	case 12:
	    		num4 = "Q";
	    		break;
	    	case 13:
	    		num4 = "K";
	    		break;
	    	default:
		    	num4 = Integer.toString(num);
		    	break;
	    	}
	    	break;
	    case 5:
	    	try {
	    		if(suit != 0)
	    			img5 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			img5 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitC5 = Color.RED;
		    else
		    	suitC5 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		num5 = "";
	    		break;
	    	case 14:
	    		num5 = "A";
	    		break;
	    	case 11:
	    		num5 = "J";
	    		break;
	    	case 12:
	    		num5 = "Q";
	    		break;
	    	case 13:
	    		num5 = "K";
	    		break;
	    	default:
		    	num5 = Integer.toString(num);
		    	break;
	    	}
	    	break;
	    case 6:
	    	try {
	    		if(suit != 0)
	    			imgP1 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			imgP1 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitCP1 = Color.RED;
		    else
		    	suitCP1 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		numP1 = "";
	    		break;
	    	case 14:
	    		numP1 = "A";
	    		break;
	    	case 11:
	    		numP1 = "J";
	    		break;
	    	case 12:
	    		numP1 = "Q";
	    		break;
	    	case 13:
	    		numP1 = "K";
	    		break;
	    	default:
		    	numP1 = Integer.toString(num);
		    	break;
	    	}
		    break;
	    case 7:
	    	try {
	    		if(suit != 0)
	    			imgP2 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png"));
	    		else
	    			imgP2 = null;
	    	} catch (IOException e) {
			}
		    if (suit == 2 || suit == 3)
		    	suitCP2 = Color.RED;
		    else
		    	suitCP2 = Color.BLACK;
	    	switch (num) {
	    	case 0:
	    		numP2 = "";
	    		break;
	    	case 14:
	    		numP2 = "A";
	    		break;
	    	case 11:
	    		numP2 = "J";
	    		break;
	    	case 12:
	    		numP2 = "Q";
	    		break;
	    	case 13:
	    		numP2 = "K";
	    		break;
	    	default:
		    	numP2 = Integer.toString(num);
		    	break;
	    	}
		    break;
	    }
	    
	    	
		
		System.out.println(System.getProperty("user.dir")+"\\src\\resources\\"+suitS+".png");
		
        setTable();
        
        f.remove(table);
        f.add(table, BorderLayout.CENTER); 
        f.setVisible(true);
		
        return f;
	}
	
	public static JFrame setCPU(int cpuNum, int charNum) {
		
	    switch(cpuNum) {
	    case 1:
	    	try {
				cpu1 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\players\\"+Integer.toString(charNum)+".png"));
			} catch (IOException e) {
			};
	    	break;
	    case 2:
	    	try {
				cpu2 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\players\\"+Integer.toString(charNum)+".png"));
			} catch (IOException e) {
			};
	    	break;
	    case 3:
	    	try {
				cpu3 = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\resources\\players\\"+Integer.toString(charNum)+".png"));
			} catch (IOException e) {
			};
	    	break;
	    }
	    System.out.println(System.getProperty("user.dir")+"\\src\\resources\\table.png");
	    
        setTable();
		
        return f;
	}
	
	public static JFrame setPot(int playerNum, float pot2) {
		
	    switch(playerNum) {
	    case 0:
	    	pot = pot2;
	    	break;
	    case 1:
	    	cpu1Pot = pot2;
	    	break;
	    case 2:
	    	cpu2Pot = pot2;
	    	break;
	    case 3:
	    	cpu3Pot = pot2;
	    	break;
	    case 4:
	    	playerPot = pot2;
	    }
	    
        setTable();
		
        return f;
	}
	
	public static JFrame addPot(int playerNum, float pot2) {
		
	    switch(playerNum) {
	    case 0:
	    	pot += pot2;
	    	break;
	    case 1:
	    	cpu1Pot += pot2;
	    	break;
	    case 2:
	    	cpu2Pot += pot2;
	    	break;
	    case 3:
	    	cpu3Pot += pot2;
	    	break;
	    case 4:
	    	playerPot += pot2;
	    }
	    
        setTable();
		
        return f;
	}
	
	public static JFrame subPot(int playerNum, float pot2) {
		
	    switch(playerNum) {
	    case 0:
	    	pot -= pot2;
	    	break;
	    case 1:
	    	cpu1Pot -= pot2;
	    	break;
	    case 2:
	    	cpu2Pot -= pot2;
	    	break;
	    case 3:
	    	cpu3Pot -= pot2;
	    case 4:
	    	playerPot -= pot2;
	    	break;
	    }
	    
        setTable();
		
        return f;
	}
	
	public static JFrame clearTable() {
		
	    setPot(0,0);
	    setPot(1,0);
	    setPot(2,0);
	    setPot(3,0);
	    setCard(1,0,0);
	    setCard(2,0,0);
	    setCard(3,0,0);
	    setCard(4,0,0);
	    setCard(5,0,0);
	    
        setTable();
		
        return f;
	}
	
public static JFrame clearTableCPU() {
		
	    setPot(0,0);
	    setPot(1,0);
	    setPot(2,0);
	    setPot(3,0);
	    setCard(1,0,0);
	    setCard(2,0,0);
	    setCard(3,0,0);
	    setCard(4,0,0);
	    setCard(5,0,0);
	    setCPU(1,0);
	    setCPU(2,0);
	    setCPU(3,0);
	    
        setTable();
		
        return f;
	}
public static JFrame clearCards() {
	
    setCard(1,0,0);
    setCard(2,0,0);
    setCard(3,0,0);
    setCard(4,0,0);
    setCard(5,0,0);
    
    setTable();
	
    return f;
}
	
	public static JFrame setTable() {
		table = new JPanel() {
            //@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgTable, 0, 0, null);
                g.drawImage(img1, 130, 328, null);
                g.drawImage(img2, 205, 328, null);
                g.drawImage(img3, 280, 328, null);
                g.drawImage(img4, 355, 328, null);
                g.drawImage(img5, 430, 328, null);
                g.drawImage(imgP1, 422, 180, null);
                g.drawImage(imgP2, 497, 180, null);
                g.drawImage(cpu1, 33, 0, null);
                g.drawImage(cpu2, 280, 0, null);
                g.drawImage(cpu3, 520, 0, null);
                
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
                g.setColor(suitC1);
                g.drawString(num1, 145, 392); 
                g.setColor(suitC2);
                g.drawString(num2, 220, 392);
                g.setColor(suitC3);
                g.drawString(num3, 295, 392);
                g.setColor(suitC4);
                g.drawString(num4, 370, 392);
                g.setColor(suitC5);
                g.drawString(num5, 445, 392);
                g.setColor(suitCP1);
                g.drawString(numP1, 437, 244);
                g.setColor(suitCP2);
                g.drawString(numP2, 512, 244);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
                g.setColor(Color.BLACK);
                g.drawString(Float.toString(cpu1Pot), 23, 115); 
                g.drawString(Float.toString(cpu2Pot), 270, 115); 
                g.drawString(Float.toString(cpu3Pot), 510, 115); 
                g.drawString(Float.toString(playerPot), 86, 276); 
                g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
                g.drawString(Float.toString(pot), 260, 245); 
            }
        };
        
        f.remove(table);
        f.add(table, BorderLayout.CENTER); 
        f.setVisible(true);
        
		return f;
	}

	
}
