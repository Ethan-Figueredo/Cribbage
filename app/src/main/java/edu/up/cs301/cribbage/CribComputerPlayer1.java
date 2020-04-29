package edu.up.cs301.cribbage;

import android.os.Looper;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;
import edu.up.cs301.game.GameFramework.utilities.GameTimer;

import android.os.Handler;

/**
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 *
 * @author Steven R. Vegdahl
 * @versio2 July 2013
 */
public class CribComputerPlayer1 extends GameComputerPlayer
{
    private CribState gameState;


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

    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game. Most of computer 1 functionality
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;

        if (!(info instanceof CribState)) return;

        Logger.log("TTTComputer", "Sending move");


        state = (CribState)info;
        if(state.getHand(1 - this.playerNum).size() == 0){
            state.setWhoseMove();
            return;
        }
        int turn = state.getWhoseMove();
        sleep(1);
        if(state.getGameStage() == CribState.THROW_STAGE) {
            System.out.print("This is comp player" + playerNum + " " + turn);
            game.sendAction(new CribThrowAction(this, 0, 1));
        }else if(state.getGameStage() == CribState.PLAY_STAGE){
            int index = pickIndex();
            if(index == -1){
                state.setWhoseMove();
                return;
            }
            game.sendAction(new CribPlayAction(this, index));
        }
        Logger.log("TTTComputer1", "Play move");

    }
    private int pickIndex(){
        for(int i = 0; i < state.getHand(1 - this.playerNum).size(); i++){
            if(!(state.over31(1 - this.playerNum, i))){
                return i;
            }
        }
        return -1;
    }

    /**
     * This function throws two random cards from an initial hand of 6
     */


    @Override
    public int getPlayerNum() {
        return playerNum;
    }

    @Override
    public CribState getCribState() {
        return gameState;
    }
}
