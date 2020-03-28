package edu.up.cs301.cribbage;

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

    public int getIndex() {
        return index;
    }

    // instance variables:
    private int index;

    /**
     * Constructor for TTTMoveAction
     *
     //@param source the player making the move
     * @param row the row of the square selected (0-2)
     * @param col the column of the square selected
     */
    public CribMoveAction(GamePlayer player, int row, int col)
    {
        // invoke superclass constructor to set the player
        super(player);

    }
    public void playCard(int index){

    }
    public void sendToCrib(int index){

    }


}
