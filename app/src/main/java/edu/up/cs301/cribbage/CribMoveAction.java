package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;

/**
 * A game-move object that a tic-tac-toe player sends to the game to make
 * a move.
 * 
 * @author Steven R. Vegdahl
 * @author Ethan Figueredo
 * @version 2 July 2001
 */
public abstract class CribMoveAction extends GameAction {
    //Tag for logging
    private static final String TAG = "CribMoveAction";
    private static final long serialVersionUID = -2242980258970485343L;

    private Card touch1;
    private Card touch2;

    /**
     * Constructor for TTTMoveAction
     * <p>
     * //@param source the player making the move
     *
     *  is the card that was touched
     */

    public CribMoveAction(GamePlayer player, Card touch1, Card touch2) {
        // invoke superclass constructor to set the player
        super(player);
        this.touch1 = touch1;
        this.touch2 = touch2;
    }

    public Card getTouch1() {
        return touch1;
    }
    public Card getTouch2(){
        return touch2;
    }

    /**
     * @return
     * 		whether the move was a sent to crib
     */
    public boolean isCrib() {
        return false;
    }
    /**
     * @return
     * 		whether the move was a play
     */
    public boolean isPlay(){
        return false;
    }

}
