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
    private int playerToMove;
    private int player0Score;
    private int player1Score;
    private ArrayList<Card> player0Hand;
    private ArrayList<Card> player1Hand;
    private int dealerID;
    private ArrayList<Card> deck;
    private ArrayList<Card> crib;
    private ArrayList<Card> playedCards;

    /**
     * Constructor for objects of class TTTState
     */
    public CribState()
    {
        playerToMove = 0;
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
        playerToMove = original.playerToMove;
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

    public int setDealerID(int newID){
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
}
