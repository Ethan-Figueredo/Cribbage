package edu.up.cs301.cribbage;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GamePlayer;

public class CribCribAction extends CribMoveAction {
    private static final long serialVersionUID = 2134321631283669359L;


    /**
     * Constructor for TTTMoveAction
     * <p>
     * //@param source the player making the move
     *
     * @param player
     */
    public CribCribAction(GamePlayer player, Card touch1, Card touch2) {
        super(player,touch1,touch2);
    }

    @Override
    public boolean isCrib() {
        return true;
    }
}
