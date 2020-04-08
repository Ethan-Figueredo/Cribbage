package edu.up.cs301.cribbage;

import android.os.Looper;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;
import edu.up.cs301.game.GameFramework.actionMessage.GameOverAckAction;
import edu.up.cs301.game.GameFramework.actionMessage.MyNameIsAction;
import edu.up.cs301.game.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.game.GameFramework.infoMessage.BindGameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.GameOverInfo;
import edu.up.cs301.game.GameFramework.infoMessage.StartGameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.TimerInfo;
import edu.up.cs301.game.GameFramework.utilities.GameTimer;
import edu.up.cs301.game.GameFramework.utilities.MessageBox;
import edu.up.cs301.game.GameFramework.utilities.Tickable;

import android.os.Handler;
import android.os.Looper;
/**
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 *
 * @author Steven R. Vegdahl
 * @versio2 July 2013
 */
public abstract class CribComputerPlayer1 extends GameComputerPlayer
{
    private CribState gameState;

    // the state
    private CribState state;

    // the ID for the layout to use
    private int layoutId;

    private ArrayList<Card> cardHand1 = new ArrayList();
    private Random ran = new Random();

    private CribState state; // the game object
    private int playerNum; // which player number I am
    private String name; // my name
    private String[] allPlayerNames; // list of all player names, in ID order
    private Handler myHandler; // the handler for this player's thread
    private boolean running; // whether the player's thread is running
    private boolean gameOver = false; // whether the game is over
    private CribMainActivity myActivity; // the game's main activity, set only
    // this game is connected to the GUI
    private GameTimer myTimer = new GameTimer(this); // my timer
    private CribMoveAction action;//action that will be sent to the game



    /*
     * Constructor for the CribComputerPlayer1 class
     */
    public CribComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
        this.name = name;
        ArrayList<Card> cards1 = crib.getDeck();

        for (int i = 0; i < 6; i++){
            int nxt = ran.nextInt(52);
            cards1.add(cards1.get(nxt));
            cards1.remove(nxt);
        }
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;
        Logger.log("TTTComputer", "My turn!");
        // pick x and y positions at random (0-2)
        int xVal = (int)(3*Math.random());
        int yVal = (int)(3*Math.random());

        // delay for a second to make opponent think we're thinking
        sleep(1);

        // Submit our move to the game object. We haven't even checked it it's
        // our turn, or that that position is unoccupied. If it was not our turn,
        // we'll get a message back that we'll ignore. If it was an illegal move,
        // we'll end up here again (and possibly again, and again). At some point,
        // we'll end up randomly pick a move that is legal.
        Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new CribMoveAction(this, yVal, xVal));

        if(info instanceof CribState){
            state = (CribState)info;
            int turn = state.getTurn();
            if(state.getTurn() == CribState.PLAYER_2) {
                takeTurn();
            }// else do nothing
        }

    }

    /**
     * This function throws two random cards from an initial hand of 6
     */
    Card[] throwCards(Card[] hand){
        int rand1 = (int)(Math.random()*hand.length); // oracle to remind myself how to make a random number.
        int rand2 = (int)(Math.random()*hand.length);
        while(rand1 == rand2) {
            rand2 = (int)(Math.random()*hand.length);
        }
        Card[] toThrow = new Card[2];
        toThrow[0] = hand[rand1];
        toThrow[1] = hand[rand2];
        hand[rand1] = null;
        hand[rand2] = null;
        return toThrow;
    }

    private int indexOfCard(Card[] a, Card c){
        for(int i =0; i<a.length;i++){//iterate through array searching for card
            if(a[i]==c){
                return i;//index of card touched
            }
        }
        return -1;//error value
    }

    /**
     * Determines randomly which card in its hand to play to the table
     */


    private Card cardsToTable(Card[] hand){

        int rand1 = (int)Math.random()*hand.length;
        boolean canPlay = false;

        // return the randomly chosen element if not null.
        // if null, loop through the array until a non-null element is found and then retrun.
        while (hand[rand1] == null && !canPlay) {

            rand1 = (rand1 + 1)%6;
            canPlay = CribCounter.canMove(hand[rand1], state);
        }

        return hand[rand1];
    }

    /**
     * Method to determine what to play during the computer's turn
     */
    private void takeTurn(){
        action = null;
        Card card = null;
        if(state.getGameStage() == CribState.THROW_STAGE){//if throw stage
            action = new CribCardsToThrow(this, throwCards(state.getPlayer1Hand()));//pick two cards to throw and save them into
            //a CardsToThrow action
        }
        else if (state.getGameStage() == CribState.PEG_STAGE){
            card = cardsToTable(state.getPlayer1Hand());
            action = new CribCardsToTable(this, card);//pick one card and save it to
            //a CardsToTable action


            sleep((int) (Math.random()*1000));//sleep up to one second
        }
        game.sendAction(action);//sends action
        if(card != null){
            int cardPos = indexOfCard(state.getPlayer1Hand(), card);
            if (cardPos >=0 && cardPos < state.getPlayer1Hand().length){
                Card[] hand = state.getPlayer1Hand();
                hand[cardPos] = null;
                state.setHand(hand);//gets index of card played and removes the card
            }
        }
    }

    @Override
    public int getPlayerNum() {
        return playerNum;
    }

    @Override
    public CribState getCribState() {
        return gameState;
    }
}
