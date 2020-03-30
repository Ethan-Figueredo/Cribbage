package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

/**
 * A game-move object that a tic-tac-toe player sends to the game to make
 * a move.
 * 
 * @author Steven R. Vegdahl
 * @author Ethan Figueredo
 * @version 2 July 2001
 */
public class CribMoveAction extends GameAction {
    //Tag for logging
    private static final String TAG = "CribMoveAction";
	private static final long serialVersionUID = -2242980258970485343L;

    private nameAction typeOfAction;

    public Card getCard1() {
        return card1;
    }

    // instance variables:
    private Card card1;

    public Card getCard2() {
        return card2;
    }

    private Card card2;

    /**
     * Constructor for TTTMoveAction
     *
     //@param source the player making the move
     * @param touch is the card that was touched
     */
    public CribMoveAction(GamePlayer player, Card touch)
    {
        // invoke superclass constructor to set the player
        super(player, touch);
        this.card1 = touch;
        this.card2 = null;
        typeOfAction = nameAction.PLAY;
    }
    public CribMoveAction(GamePlayer player, Card touch, Card touch2)
    {
        // invoke superclass constructor to set the player
        super(player, touch, touch2);
        this.card2 = touch2;
        this.card1 = touch;
        typeOfAction = nameAction.CRIB;
    }
    public void playCard(int index){

    }
    public void sendToCrib(int index){

    }


}
