package edu.up.cs301.cribbage;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;

import android.graphics.Point;

/**
 * A computerized tic-tac-toe player that recognizes an immediate win
 * or loss, and plays appropriately.  If there is not an immediate win
 * (which it plays) or loss (which it blocks), it moves randomly.
 * 
 * @author Steven R. Vegdahl 
 * @version September 2016
 * 
 */
public class CribComputerPlayer2 extends GameComputerPlayer {
	//Tag for logging
	private static final String TAG = "CribComputerPlayer2";
	/**
	 * instance variable that tells which piece am I playing ('X' or 'O').
	 * This is set once the player finds out which player they are, in the
	 * 'initAfterReady' method.
	 */
	protected char piece;
	private CribState gameState;
	private int playerNum;

	/**
	 * constructor for a computer player
	 *
	 * @param name the player's name
	 */
	public CribComputerPlayer2(String name) {
		// invoke superclass constructor
		super(name);
		//set gameState and playerNum
	}// constructor

	/**
	 * perform any initialization that needs to be done after the player
	 * knows what their game-position and opponents' names are.
	 */
	protected void initAfterReady() {
		// initialize our piece
		piece = "XO".charAt(playerNum);
	}// initAfterReady

	/**
	 * Called when the player receives a game-state (or other info) from the
	 * game.
	 *
	 * @param info the message from the game
	 */
	@Override
	protected void receiveInfo(GameInfo info) {

		// if it's not a TTTState message, ignore it; otherwise
		// cast it
		if (!(info instanceof CribState)) return;
		gameState = (CribState) info;

		// if it's not our move, ignore it
		if (gameState.getWhoseMove() != this.playerNum) return;

		// sleep for a second to make any observers think that we're thinking
		sleep(1);
	}// receiveInfo
}

