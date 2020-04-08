package edu.up.cs301.cribbage;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;


/**
 * Contains the state of a Cribbage game.  Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class CribState extends GameState {
    //Tag for logging
    private static final String TAG = "CribState";
	private static final long serialVersionUID = 7552321013488624386L;

    //instance variables
    private int[][] board;
    private int prevPegP1;
    private int currPegP1;
    private int prevPegP0;
    private int currPegP0;

    public int getWhoseMove() {
        return whoseMove;
    }

    public static final int PLAYER_1 = 0;//player constants
    public static final int PLAYER_2 = 1;

    private int whoseMove;
    private int player0Score;
    private int player1Score;
    private ArrayList<Card> player0Hand;

    public ArrayList<Card> getPlayerHand(int hand) {
        if(hand == 0){
            return player0Hand;
        }else if(hand == 1){
            return player1Hand;
        }
        return null;
    }



    private ArrayList<Card> player1Hand;
    private int dealerID;
    private ArrayList<Card> deck;

    public ArrayList<Card> getCrib() {
        return crib;
    }

    private ArrayList<Card> crib;

    public ArrayList<Card> getPlayedCards() {
        return playedCards;
    }

    private ArrayList<Card> playedCards;

    public ArrayList<Card> getDeck(){
        return deck;
    }

    private int turn;
    private int gameStage;
    public static final int THROW_STAGE = 0;
    public static final int PEG_STAGE = 1;
    public static final int COUNT_STAGE = 2;
    public String[] tutorialTexts = {
            "It is now the Throw Stage. Please select two cards to throw to the crib",
            "It is now the Peg Stage. Please select one card at a time to send to the table",
            "It is now the Count Stage. The cards are being counted."
    };

    /**
     * Constructor for objects of class TTTState
     */
    public CribState()
    {
        whoseMove = 0;
    }// constructor
    
    /**
     * Copy constructor for class TTTState
     *  
     * @param original
     * 		the TTTState object that we want to clong
     */
    public CribState(CribState original)
    {
    	// copy the player-to-move information
        whoseMove = original.whoseMove;
    }

    public int getPrevPeg(int id){
        if(id == 0) {
            return prevPegP0;
        } else if(id == 1){
            return prevPegP1;
        } else {
            return -1;
        }
    }

    public void setPrevPeg(int id, int location){
        if(id == 0) {
            prevPegP0 = location;
        } else if(id == 1){
            prevPegP1 = location;
        }
    }

    public int getCurrPeg(int id){
        if(id == 0) {
            return currPegP0;
        } else if(id == 1){
            return currPegP1;
        } else {
            return -1;
        }
    }

    public void setCurrPeg(int id, int location){
        if(id == 0) {
            currPegP0 = location;
        } else if(id == 1){
            currPegP1 = location;
        }
    }

    public int getDealerID(){
        return dealerID;
    }

    public void setDealerID(int newID){
        dealerID = newID;
    }

    public int getScore(int id){
        if(id == 0){
            return player0Score;
        } else if(id == 1){
            return player1Score;
        } else {
            return -1;
        }
    }

    public void setScore(int id, int newScore){
        if(id == 0){
            player0Score += newScore;
        } else if(id == 1){
            player1Score += newScore;
        }
    }

    /**
     * Setter method for the turn
     *
     */

    public void setTurn(int turn){
        this.turn = turn;
    }

    /**
     * Getter method for the turn the game is in
     * @return int turn  Reference for which turn the game is in
     */

    public int getTurn(){
        return turn;
    }

}
