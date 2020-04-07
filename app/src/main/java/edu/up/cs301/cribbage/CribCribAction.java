package edu.up.cs301.cribbage;

import edu.up.cs301.game.GameFramework.GamePlayer;

public class CribCribAction extends CribMoveAction {
    private static final long serialVersionUID = 3250639793499599047L;


    /**
     * Constructor for TTTMoveAction
     * <p>
     * //@param source the player making the move
     *
     * @param player
     */
    public CribCribAction(GamePlayer player) {
        super(player);
    }

    @Override
    public boolean isPlay() {
        return true;
    }
}
