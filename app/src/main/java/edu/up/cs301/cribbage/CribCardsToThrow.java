package edu.up.cs301.cribbage;
import edu.up.cs301.card.*;
import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

/**
 * @author Will
 * @version 04/7/2020
 */

class CribCardsToThrow extends CribMoveAction {

    /**
     * Class that is used for throw actions
     */
    private Card[] cardsToThrow;
    private GamePlayer player;

    public CribCardsToThrow(GamePlayer player, Card[] cards) {
        super(player);
        this.player = player;
        cardsToThrow = cards;
    } // Constructor for the CardsToThrow gameAction

    /**
     * Gets the cards from an action
     * @return  cards thrown
     */

    public Card[] cards(){
        return cardsToThrow;
    }

    /**
     * @return player
     */

    public GamePlayer player(){
        return player;
    }
}