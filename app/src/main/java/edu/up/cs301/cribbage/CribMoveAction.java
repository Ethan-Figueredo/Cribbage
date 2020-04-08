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

    /**
     * Constructor for TTTMoveAction
     *
     *  is the card that was touched
     */

    public CribMoveAction(GamePlayer player) {
        // invoke superclass constructor to set the player
        super(player);
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
