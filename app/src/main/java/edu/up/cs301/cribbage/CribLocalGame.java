package edu.up.cs301.cribbage;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.utilities.Logger;

/**
 * The TTTLocalGame class for a simple tic-tac-toe game.  Defines and enforces
 * the game rules; handles interactions with players.
 * 
 * @author Steven R. Vegdahl 
 * @version July 2013
 */

public class CribLocalGame extends LocalGame {
	//Tag for logging
	private static final String TAG = "CribLocalGame";
	// the game's state
	protected CribState state;

	private GameStage stage;

	// the number of moves that have been played so far, used to
	// determine whether the game is over
	protected int moveCount;


	/**
	 * Constructor for the TTTLocalGame.
	 */
	public CribLocalGame() {

		// perform superclass initialization
		super();

		// create a new, unfilled-in TTTState object
		state = new CribState();
	}


	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {
		//if the score is greater or equal to 121, you win. Check dealers score first, then other player

		int dealersScore = state.getScore(state.getDealerID());
		int otherID;
		if(state.getDealerID() == 0){
			otherID = 1;
		}else {
			otherID = 0;
		}
		int otherScore = state.getScore(otherID);
		// the character that will eventually contain an 'X' or 'O' if we
		if(dealersScore >= 121){//found winner
			return playerNames[state.getDealerID()] +" is the winner.";
		}else if(otherScore >= 121){ //found winner
			return playerNames[otherID] +" is the winner.";
		} else{ //game not over
			return null;
		}
	}

	/**
	 * Notify the given player that its state has changed. This should involve sending
	 * a GameInfo object to the player. If the game is not a perfect-information game
	 * this method should remove any information from the game that the player is not
	 * allowed to know.
	 * 
	 * @param p
	 * 			the player to notify
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// make a copy of the state, and send it to the player
		p.sendInfo(new CribState(state));

	}

	/**
	 * Tell whether the given player is allowed to make a move at the
	 * present point in the game. 
	 * 
	 * @param playerIdx
	 * 		the player's player-number (ID)
	 * @return
	 * 		true iff the player is allowed to move
	 */
	protected boolean canMove(int playerIdx) {
		return playerIdx == state.getWhoseMove();
	}

	/**
	 * Makes a move on behalf of a player.
	 * 
	 * @param action
	 * 			The move that the player has sent to the game
	 * @return
	 * 			Tells whether the move was a legal one.
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		CribMoveAction temp = action.getCribAction();
		if(temp.getTypeOfAction().equals(CribMoveAction.nameAction.CRIB)) { //isEnum
			//checks for crib actions
			if(temp.getCard1() == null || temp.getCard2() == null){ //check for number of cards selected to send to crib
				Logger.log("Check CribAction", "One card is null while playing to crib");
				return false;
			}
		} else if(temp.getTypeOfAction().equals((CribMoveAction.nameAction.PLAY))){
			//checks for play action
			if(temp.getCard2() != null){
				Logger.log("Check CribAction", "Too many cards selected");
				return false;
			}
		}
		return true;
	}

}
