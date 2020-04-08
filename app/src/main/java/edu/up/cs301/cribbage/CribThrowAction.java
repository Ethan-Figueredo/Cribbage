package edu.up.cs301.cribbage;
import edu.up.cs301.card.*;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

/**
 * @author Will
 * @version 04/7/2020
 */



class CribThrowAction extends CribMoveAction {
    private int indexofCard1;

    public int getIndexofCard1() {
        return indexofCard1;
    }

    public int getIndexofCard2() {
        return indexofCard2;
    }

    private int indexofCard2;
    public CribThrowAction(GamePlayer player,int index1, int index2) {
        super(player);
        this.indexofCard1 = index1;
        this.indexofCard2 = index2;
    } // Constructor for the CardsToThrow gameAction

    public boolean isThrow(){
        return true;
    }
}