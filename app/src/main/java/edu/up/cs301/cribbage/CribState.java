package edu.up.cs301.cribbage;

import java.util.ArrayList;
import java.util.Random;

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

    private int throwCount = 0;

    public int getWhoseMove() {
        return whoseMove;
    }

    int lastMove;

    public void setWhoseMove() {
        whoseMove = 1 - getWhoseMove();
    }

    private int whoseMove;
    private int[] scores;
    private Deck[] piles;

    public Deck getHand(int index) {
        return piles[index];
    }

    private int dealerID;
    private int runningTotal;

    public int getLastMove(){
        return lastMove;
    }

    public void setLastMove(int move){
        lastMove = move;
    }

    public int getRunningTotal(){
        return runningTotal;
    }

    public void setRunningTotal(int value){
        runningTotal = value;
    }

    public Deck getCrib() {
        return piles[3];
    }

    public Deck getPlayedCards() {
        return piles[4];
    }

    public Deck getDeck(){
        return piles[2];
    }

    public boolean over31(int player, int index){
        int prevRun = getRunningTotal();
        int toAdd = rankToInt(getHand(player).getCard(index));
        if((prevRun + toAdd) >= 32){
            return true;
        } else {
            return false;
        }
    }

    private int turn;
    private int gameStage;
    public static final int THROW_STAGE = 0;
    public static final int PLAY_STAGE = 1;
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

        Random ran = new Random();
        int random = ran.nextInt(2);
        dealerID = random;
        whoseMove = 1 - random;
        System.out.println("Dealer Id " + dealerID);
        gameStage = THROW_STAGE;
        piles = new Deck[5];
        piles[0] = new Deck();  //player 0's hand
        piles[1] = new Deck();  //player 1's hand
        piles[2] = new Deck();  //full deck
        piles[3] = new Deck();  //crib
        piles[4] = new Deck();  //played cards

        scores = new int[]{0, 0};
        runningTotal = 0;
        lastMove = 0;

        piles[2].add52();
        piles[2].shuffle();

        for(int i = 0; i < 6; i++){
            piles[2].moveTopCardTo(piles[0]);
            piles[2].moveTopCardTo(piles[1]);
        }
    }// constructor
    
    /**
     * Copy constructor for class TTTState
     *  
     * @param orig
     * 		the TTTState object that we want to clong
     */
    public CribState(CribState orig)
    {
    	// copy the player-to-move information
        whoseMove = orig.whoseMove;
        gameStage = orig.gameStage;
        dealerID = orig.dealerID;
        piles = new Deck[5];
        piles[0] = new Deck(orig.piles[0]);  //player 0's hand
        piles[1] = new Deck(orig.piles[1]);  //player 1's hand
        piles[2] = new Deck(orig.piles[2]);  //full deck
        piles[3] = new Deck(orig.piles[3]);  //crib
        piles[4] = new Deck(orig.piles[4]);  //played cards

        scores = orig.scores;
        runningTotal = orig.runningTotal;
        lastMove = orig.lastMove;
    }
    public void resetRoundHand(){
        setWhoseMove();
        gameStage = THROW_STAGE;

        piles = new Deck[5];
        piles[0] = new Deck();  //player 0's hand
        piles[1] = new Deck();  //player 1's hand
        piles[2] = new Deck();  //full deck
        piles[3] = new Deck();  //crib
        piles[4] = new Deck();  //played cards

        piles[2].add52();
        piles[2].shuffle();

        for(int i = 0; i < 6; i++){
            piles[2].moveTopCardTo(piles[0]);
            piles[2].moveTopCardTo(piles[1]);
        }
        setRunningTotal(0);
    }

    public int getDealerID(){
        return dealerID;
    }

    public void setDealerID(){
        dealerID = 1 - dealerID;
    }

    public int getScore(int index){
        return scores[index];
    }

    public void setScore(int index, int newScore){
        scores[index] = newScore;
    }

    public int rankToInt(Card card){
        switch(card.getRank()){
            case ACE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case JACK:
                return 10;
            case QUEEN:
                return 10;
            case KING:
                return 10;
            default:
                return 0;
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

    /**
     * Setter method for the ThrowCount
     *
     */

    public final void setThrowCount(int throwCount){
        this.throwCount = throwCount;
    }

    /**
     * Getter method for the throwCount
     * @return int throwCount  The number of cards being thrown
     */

    public int getThrowCount(){
        return throwCount;
    }

    /**
     * Setter method for the GameStage
     *
     */

    public final void setGameStage(int stage) {
        gameStage = stage;
    }

    /**
     * Getter method for the gameStage
     * @return int gameStage  The stage the game is in
     */

    public final int getGameStage() {
        return gameStage;
    } // GetGameStage

}
