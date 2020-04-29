package edu.up.cs301.cribbage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import edu.up.cs301.card.Card;
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


	// the number of moves that have been played so far, used to
	// determine whether the game is over
	protected int moveCount;


	/**
	 * Constructor for the TTTLocalGame.
	 */
	public CribLocalGame() {

		// perform superclass initialization
		super();

		// create a new, unfilled-in CribState object
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
		if(playerIdx == state.getWhoseMove()){
		    return false;
        }
		if(!checkCanPlay(playerIdx)){
		    return false;
        }
		return true;
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
		if(!(action instanceof CribMoveAction)){
			return false;
		}
		CribMoveAction cribMA = (CribMoveAction) action;
		int thisPlayerIdx = getPlayerIdx(cribMA.getPlayer());

		if(cribMA.isThrow()){
			if(state.getHand(thisPlayerIdx).size() != 6){
				return false;
			} else if(((CribThrowAction) action).getIndexofCard1() == ((CribThrowAction) action).getIndexofCard2()){
				return false;
			} else {
				sendToCrib(thisPlayerIdx, ((CribThrowAction) action).getIndexofCard1(), ((CribThrowAction) action).getIndexofCard2());
				state.setWhoseMove();
			}
			if(state.getHand(thisPlayerIdx).size() == 4 && state.getHand(1 - thisPlayerIdx).size() == 4){
				state.setGameStage(CribState.PLAY_STAGE);
			}
			return true;
		} else if(cribMA.isPlay()){
			if(state.getHand(thisPlayerIdx).size() > 4) {//player has too many cards in their hands
				return false;
			} else if(!checkCanPlay(thisPlayerIdx) && !checkCanPlay(1 - thisPlayerIdx)){//check if both players cant play
				forLast(state.getLastMove());
				state.getPlayedCards().nullifyDeck();
				state.setRunningTotal(0);
				return false;
			} else{
				if(!checkCanPlay(thisPlayerIdx)) {
					state.setWhoseMove();
					return false;
				} else if(state.over31(thisPlayerIdx, ((CribPlayAction)action).getIndexPlay())) {
					return false;
				} else {
					state.setRunningTotal(state.getRunningTotal() + state.rankToInt(state.getHand(thisPlayerIdx).getCard(((CribPlayAction) action).getIndexPlay())));
					sendToPlay(thisPlayerIdx, ((CribPlayAction) action).getIndexPlay());
					checkPair(thisPlayerIdx);
					state.setLastMove(thisPlayerIdx);
					state.setWhoseMove();
				}
			}
			if(state.getHand(thisPlayerIdx).size() == 0 && state.getHand(1- thisPlayerIdx).size() == 0){
				//calculate score
				forLast(state.getLastMove());
				calculateHandScore();
				state.setDealerID();
				state.resetRoundHand();

			}
			return true;
		}
		return false;
	}
	private void calculateHandScore(){
		Card pos1 = state.getHand(0).getCard(0);
		Card pos2 = state.getHand(0).getCard(1);
		Card pos3 = state.getHand(0).getCard(2);
		Card pos4 = state.getHand(0).getCard(3);

		Card pos5 = state.getHand(1).getCard(0);
		Card pos6 = state.getHand(1).getCard(1);
		Card pos7 = state.getHand(1).getCard(2);
		Card pos8 = state.getHand(1).getCard(3);


		pairCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		fifteenCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		pairRoyal(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		runCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());

		pairCheck(1,pos5.getRank().ordinal(),pos6.getRank().ordinal(),pos7.getRank().ordinal(),pos8.getRank().ordinal());
		fifteenCheck(1,pos5.getRank().ordinal(),pos6.getRank().ordinal(),pos7.getRank().ordinal(),pos8.getRank().ordinal());
		pairRoyal(1,pos5.getRank().ordinal(),pos6.getRank().ordinal(),pos7.getRank().ordinal(),pos8.getRank().ordinal());
		runCheck(1,pos5.getRank().ordinal(),pos6.getRank().ordinal(),pos7.getRank().ordinal(),pos8.getRank().ordinal());
	}

    private void calculatePlayScore(){
        Card pos1 = state.getPlayedCards().getCard(0);
        Card pos2 = state.getPlayedCards().getCard(1);
        Card pos3 = state.getPlayedCards().getCard(2);
        Card pos4 = state.getPlayedCards().getCard(3);


        pairCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
        fifteenCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
        pairRoyal(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
        runCheck(0,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
    }

	public void forLast(int player){
		state.setScore(player, state.getScore(player) + 1);
	}

	public void checkPair(int player){
		Deck playedCards = state.getPlayedCards();
		if(playedCards.size() < 2){
			return;
		}
		Card prevCard = playedCards.getCard(playedCards.size() - 2);
		Card cardPlayed = playedCards.getCard(playedCards.size() - 1);
		int prevCardRank = state.rankToInt(prevCard);
		int cardPlayedRank = state.rankToInt(cardPlayed);
		if(prevCardRank == cardPlayedRank){
			state.setScore(player, state.getScore(player) + 2);
		}
	}

	private void calculateCribScore(){
		Card pos1 = state.getCrib().getCard(0);
		Card pos2 = state.getCrib().getCard(1);
		Card pos3 = state.getCrib().getCard(2);
		Card pos4 = state.getCrib().getCard(3);

		pairCheck(50,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		fifteenCheck(50,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		pairRoyal(50,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
		runCheck(50,pos1.getRank().ordinal(),pos2.getRank().ordinal(),pos3.getRank().ordinal(),pos4.getRank().ordinal());
	}
	//method that checks pairs
	private void pairCheck(int playerIdx, int one, int two, int three, int four){
		int x;
		if(playerIdx == 50){
			x =state.getDealerID();
		} else {
			x = playerIdx;
		}

		int[] cribCards = new int[]{one, two, three, four};
		for(int i = 0; i < cribCards.length; i++){
			for(int j = i + 1; j < cribCards.length; j++){
				if(cribCards[i] == cribCards[j]){
					System.out.println("Pair: " + i + " " + j);
					state.setScore(x, state.getScore(x) + 2);
				}
			}
		}
	}
	//method that checks combinations for 15
	private void fifteenCheck(int playerIdx, int one, int two, int three, int four) {
		int x;
		if(playerIdx == 50){
			x =state.getDealerID();
		} else {
			x = playerIdx;
		}
		int[] cribCards = new int[]{one, two, three, four};

		for(int i = 0; i < cribCards.length; i++){
			for(int j = i + 1; j < cribCards.length; j++){
				if(cribCards[i] + cribCards[j] == 15){
					System.out.println("15: " + i + " " + j);
					state.setScore(x, state.getScore(x) + 2);
				}
			}
		}

		for(int i = 0; i < cribCards.length; i++){
			for(int j = i + 1; j < cribCards.length; j++){
				for(int k = j + 1; k < cribCards.length; k++) {
					if (cribCards[i] + cribCards[j] + cribCards[k] == 15) {
						System.out.println("15: " + i + " " + j + " " + k);
						state.setScore(x, state.getScore(x) + 2);
					}
				}
			}
		}

		if(one+two+three+four == 15){
			state.setScore(x, state.getScore(x) + 2);
		}

	}
	//checks for three of a kind
	private void pairRoyal(int playerIdx, int one, int two, int three, int four){
		int x;
		if(playerIdx == 50){
			x =state.getDealerID();
		} else {
			x = playerIdx;
		}
		int[] cribCards = new int[]{one, two, three, four};

		for(int i = 0; i < cribCards.length; i++){
			for(int j = i + 1; j < cribCards.length; j++){
				for(int k = j + 1; k < cribCards.length; k++) {
					if (cribCards[i] == cribCards[j] && cribCards[j] == cribCards[k]) {
						System.out.println("3 of a Kind: " + i + " " + j + " " + k);
						state.setScore(x, state.getScore(x) + 6);
					}
				}
			}
		}
	}
	//checks runs
	private void runCheck(int playerIdx, int one, int two, int three, int four){
		int x;
		if(playerIdx == 50){
			x =state.getDealerID();
		} else {
			x = playerIdx;
		}
		int[] temp = {one,two,three,four};
		Arrays.sort(temp);
		if(temp[0] == temp[1] - 1 && temp[1] == temp[2]-1){
			state.setScore(x, state.getScore(x) + 3);
		}
		if (temp[1] == temp[2]-1 && temp[2] == temp[3] - 1){
			state.setScore(x, state.getScore(x) + 3);
		}
		if(temp[0] == temp[1] - 1 && temp[1] == temp[2]-1 && temp[2] == temp[3] - 1){
			state.setScore(x, state.getScore(x) + 4);
		}
	}
	//helper method (helps runCheck) that sorts the numbers in rising order

	public boolean checkCanPlay(int player){
		Deck playerHand = state.getHand(player);
		for(int i = 0; i < playerHand.size(); i++){
			if(!state.over31(player, i)){
				return true;
			}
		}

		return false;
	}

	private void sendToPlay(int playerNum, int index){
		state.getPlayedCards().add(state.getHand(playerNum).getCard(index));
		state.getHand(playerNum).removeCard(index);
	}

	private void sendToCrib(int playerNum, int index, int index2){
		state.getCrib().add(state.getHand(playerNum).getCard(index));
		state.getCrib().add(state.getHand(playerNum).getCard(index2));
		if(index < index2){
			state.getHand(playerNum).removeCard(index2);
			state.getHand(playerNum).removeCard(index);
		}else if(index2 < index){
			state.getHand(playerNum).removeCard(index);
			state.getHand(playerNum).removeCard(index2);
		}
	}
}