package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GamePlayer;

public class CribPlayAction extends CribMoveAction {
    private static final long serialVersionUID = 3250639793499599047L;


    /**
     * Constructor for TTTMoveAction
     * <p>
     * //@param source the player making the move
     *
     * @param player
     */
    public CribPlayAction(GamePlayer player, Card touch1) {
        super(player,touch1,null);
    }

    @Override
    public boolean isPlay() {
        return true;
    }
}
