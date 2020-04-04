package edu.up.cs301.cribbage;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;

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
    protected int playerNum; // which player number I am
    protected String name; // my name
    protected String[] allPlayerNames; // list of all player names, in ID order
    private boolean running; // whether the player's thread is running
    private boolean gameOver = false; // whether the game is over

    // the state
    private CribState state;

    // the ID for the layout to use
    private int layoutId;

    private ArrayList<Card> cardHand1 = new ArrayList();
    private Random ran = new Random();

    CribState crib;

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
    	game.sendAction(new CribMoveAction(this, yVal, xVal));
    	
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
