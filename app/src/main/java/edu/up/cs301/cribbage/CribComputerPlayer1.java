package edu.up.cs301.cribbage;

import android.util.Log;

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
    /*
     * Constructor for the CribComputerPlayer1 class
     */
    public CribComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
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

        if (info instanceof NotYourTurnInfo) return;

    	// delay for a second to make opponent think we're thinking
    	sleep(1);



    	// Submit our move to the game object. We haven't even checked it it's
    	// our turn, or that that position is unoccupied. If it was not our turn,
    	// we'll get a message back that we'll ignore. If it was an illegal move,
    	// we'll end up here again (and possibly again, and again). At some point,
    	// we'll end up randomly pick a move that is legal.
        Logger.log("TTTComputer", "Sending move");


    	game.sendAction(new CribMoveAction(this, getCardFromHand(), getCardFromHand()));
    	
    }

    @Override
    public int getPlayerNum() {
        return playerNum;
    }

    @Override
    public CribState getCribState() {
        return gameState;
    }

    /*
    Method that gets Random Card from players deck. Since this is easy computer, it is Random
     */
    public Card getCardFromHand() {
        ArrayList<Card> temp = null;
        int size = 0;
        CribState state = getCribState();
        switch(playerNum){
            case 0:
                temp = state.getPlayer0Hand();
                size = temp.size();
                break;
            case 1:
                temp = state.getPlayer1Hand();
                size = temp.size();
                break;
        }

        Random ran = new Random();
        switch (size){
            case 1:
                return temp.get(0);
            case 2:
                return temp.get(ran.nextInt(1));
            case 3:
                return temp.get(ran.nextInt(2));
            case 4:
                return temp.get(ran.nextInt(3));
            case 5:
                return temp.get(ran.nextInt(4));
            case 6:
                return temp.get(ran.nextInt(5));
            default:
                Log.d("Get Card", "couldn't find card");
        }
        return null;
    }
}
