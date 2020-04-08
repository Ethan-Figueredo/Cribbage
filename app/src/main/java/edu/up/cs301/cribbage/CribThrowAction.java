package edu.up.cs301.cribbage;
import edu.up.cs301.card.*;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

/**
 * @author Will
 * @version 04/7/2020
 */

class CribThrowAction extends CribMoveAction {

    public CribThrowAction(GamePlayer player) {
        super(player);
    } // Constructor for the CardsToThrow gameAction

    public boolean isThrow(){
        return true;
    }
}