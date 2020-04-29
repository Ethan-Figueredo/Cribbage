package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;


import android.content.Intent;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
	private CribState state;


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

// if it was a "not your turn" message, just ignore it
		if (info instanceof NotYourTurnInfo) return;

		if (!(info instanceof CribState)) return;

		Logger.log("TTTComputer", "Sending move");

		state = (CribState) info;
		sleep(1);
		if(state.getGameStage() == CribState.THROW_STAGE) {
			int[] index = crib();
			game.sendAction(new CribThrowAction(this, index[0], index[1]));
		}else if(state.getGameStage() == CribState.PLAY_STAGE){
			if(state.getWhoseMove() != playerNum){
				return;
			}
			int index = play();
			game.sendAction(new CribPlayAction(this, index));
		}
	}// receiveInfo

	/**
	 *send cards by checking if there is a 15 in the first play, 2 consecutive number, and pairs
	 */

	private int play(){
		ArrayList<Integer> possible = under31();
		int found = -1;
		if(check15(possible)){
			found = lookfor15(possible);
		}
		if(found == -1 && check2Consec()){
			found = lookFor2Consec(possible);}
		if(found == -1 && checkPair()){
 			found = playPair(possible);
		}
		if(found == -1){
			if(possible.size() >= 1){
				return possible.get(0);
			}
		}
		return found;
	}
	private boolean checkPair(){
		if(state.getPlayedCards().size() >= 1){
			return true;
		}
		return false;
	}
	private ArrayList<Integer> under31(){
		int x = state.getHand(playerNum).size();
		ArrayList<Integer> possible = new ArrayList<>();

		for(int i = 0; i < x; i++){
			if((state.getRunningTotal() + state.rankToInt(state.getHand(playerNum).getCard(i))) < 32){
				possible.add(i);
			}
		}
		return possible;
	}
	private int lookFor2Consec(ArrayList<Integer> possible){
		int x = state.getPlayedCards().size();
		int handSize = possible.size();
		int lastCard = state.rankToInt(state.getPlayedCards().getCard(x-1));
		int secondLastCard = state.rankToInt(state.getPlayedCards().getCard(x-2));
		int diff = Math.abs(lastCard-secondLastCard);
		for (int i = 0; i < handSize; i++) {
			if(diff == 2){//need to find the number that is inclusive to make a run
				if(lastCard - secondLastCard < 0 && state.rankToInt(state.getHand(playerNum).getCard(i)) - 1 == lastCard && state.rankToInt(state.getHand(playerNum).getCard(i)) + 1 == secondLastCard){//smaller number is the last Card
					return i;
				}else{ //bigger number is the last secondLastCard
					if(state.rankToInt(state.getHand(playerNum).getCard(i))+ 1 == lastCard && state.rankToInt(state.getHand(playerNum).getCard(i)) - 1 == secondLastCard){
						return i;
					}
				}
			}else{//need to find the number that is exclusive to make a run
				if(lastCard - secondLastCard < 0 && state.getHand(playerNum).getCard(i).getRank().ordinal() + 1 == lastCard){//smaller number is the last Card
					return i;
				}else{ //bigger number is the last secondLastCard
					if(state.rankToInt(state.getHand(playerNum).getCard(i)) - 1 == secondLastCard){
						return i;
					}
				}
			}
		}
		return -1;
	}
	private boolean check2Consec(){
		int x = state.getPlayedCards().size();
		if(x < 2){
			return false;
		}
		try {
			int lastCard = state.rankToInt(state.getPlayedCards().getCard(x - 1));
			int secondLastCard = state.rankToInt(state.getPlayedCards().getCard(x - 2));

			int diff = Math.abs(lastCard - secondLastCard);
			if (diff <= 2 && lastCard != secondLastCard) {
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}
	private boolean check15(ArrayList<Integer> possible){

		int count = 0;
		int y = state.getPlayedCards().size();
		if(y >= 1){
			for(int i = 0; i < y;i++){
				try {
					count += state.rankToInt(state.getPlayedCards().getCard(i));
				}catch (NullPointerException e){
					break;
				}
				if(count >= 15){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	private int lookfor15(ArrayList<Integer> possibl){
		int x = possibl.size();
		int firstCard = state.getRunningTotal();
		for(int i = 0; i < x; i++){
			if(firstCard + possibl.get(i) == 15){
				return i;
			}
		}
		return -1;
	}
	private int playPair(ArrayList<Integer> possible){
		try {
			int lastCard = state.rankToInt(state.getPlayedCards().getCard(state.getPlayedCards().size() - 1));
			int x = possible.size();
			for (int i = 0; i < x; i++) {
				if (lastCard == state.rankToInt(state.getHand(playerNum).getCard(i))) {
					return i;
				}
			}
			return -1;
		}catch (NullPointerException e){
			return -1;
		}
	}
	/**
	 * depending which player is the dealer send the best card
	 * if dealer is cpu -> send 5 or 10 values if none found then send pair else send first two cards else send high cards
	 * else
	 * send the lowest two cards
	 */
	private int[] crib(){
		int x = state.getHand(playerNum).size();
		int dealerID = state.getDealerID();

		if(dealerID == playerNum){ //cpu is dealer
			int[] index = fiveorten();

			if(index == null){
				index = pair();
				if(index == null){
					return twoConsec();
				}
				return index;
			}
			return index;
		}else{ //cpu is not dealer
			return findTwoMax();
		}
	}
	private int[] twoConsec(){
		int x = state.getHand(playerNum).size();
		for (int i = 0; i < x; i++) {
			for (int j = i+1; j < x - 1; j++) {
				int card1 = state.getHand(playerNum).getCard(i).getRank().ordinal();
				int card2 = state.getHand(playerNum).getCard(j).getRank().ordinal();
				if (card1 == card2 + 1 || card1 == card2 - 1) {
					int[] index = {card1,card2};
					return index;
				}
			}
		}
		int[] index = {0,0};
		return index;
	}


	private int[] findTwoMax(){
		int max = -1;
		int max1 = -1;
		int x = state.getHand(playerNum).size();
		for(int i = 0; i < x; i++){
			if(state.getHand(playerNum).getCard(i).getRank().ordinal() > max){
				max1 = max;
				max = i;
			}else if(state.getHand(playerNum).getCard(i).getRank().ordinal() > max1){
				max1 = i;
			}
		}
		int[] index = {max,max1};
		return index;
	}
	private int[] pair(){
		int one = state.getHand(playerNum).getCard(0).getRank().ordinal();
		int two = state.getHand(playerNum).getCard(1).getRank().ordinal();
		int three = state.getHand(playerNum).getCard(2).getRank().ordinal();
		int four = state.getHand(playerNum).getCard(3).getRank().ordinal();
		int five = state.getHand(playerNum).getCard(4).getRank().ordinal();
		int six = state.getHand(playerNum).getCard(5).getRank().ordinal();

		int[] index = new int[2];
		if(one == two) {
			index[0] = 0;
			index[1] = 1;
			return index;
		}
		if( one == three){
			index[0] = 0;
			index[1] = 2;
			return index;
		}
		if(one == four){
			index[0] = 0;
			index[1] = 3;
			return index;
		}
		if(one == five){
			index[0] = 1;
			index[1] = 4;
			return index;
		}
		if(one == six){
			index[0] = 0;
			index[1] = 5;
			return index;
		}
		if(two == three) {
			index[0] = 1;
			index[1] = 2;
			return index;
		}
		if(two == four){
			index[0] = 1;
			index[1] = 3;
			return index;
		}
		if(two == five){
			index[0] = 1;
			index[1] = 4;
			return index;
		}
		if(two == six){
			index[0] = 1;
			index[1] = 5;
			return index;
		}
		if(three == four){
			index[0] = 2;
			index[1] = 3;
			return index;
		}
		if(three == five){
			index[0] = 2;
			index[1] = 4;
			return index;
		}
		if(three == six){
			index[0] = 2;
			index[1] = 5;
			return index;
		}
		if(four == five){
			index[0] = 3;
			index[1] = 4;
			return index;
		}
		if(four == six){
			index[0] = 3;
			index[1] = 5;
			return index;
		}
		if(five == six){
			index[0] = 4;
			index[1] = 5;
			return index;
		}
		return null;
	}
	private int[] fiveorten(){
		int[] index = new int[2];

		int x = state.getHand(playerNum).size();

		boolean ONE = false;

		for (int i = 0; i < x; i++) {
			int temp = state.getHand(playerNum).getCard(i).getRank().ordinal();
			if(temp == 4 || temp >= 9){
				if(!ONE){
					index[0] = i;
					ONE = true;
				}else{
					index[1] = i;
					return index;
				}
			}
		}
		return null;
	}

	@Override
	public int getPlayerNum() {
		return playerNum;
	}

	@Override
	public CribState getCribState() {
		return state;
	}
}

