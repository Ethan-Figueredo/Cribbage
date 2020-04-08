package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GamePlayer;

public class CribPlayAction extends CribMoveAction {
    private static final long serialVersionUID = 3250639793499599047L;
    private int indexPlay;

    /**
     * Constructor for TTTMoveAction
     * <p>
     * //@param source the player making the move
     *
     * @param player
     */
    public CribPlayAction(GamePlayer player, int index) {
        super(player);
        this.indexPlay = index;
    }

    public boolean isPlay() {
        return true;
    }
}
