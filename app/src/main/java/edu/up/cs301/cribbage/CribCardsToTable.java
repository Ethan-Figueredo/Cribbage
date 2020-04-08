package edu.up.cs301.cribbage;
import edu.up.cs301.card.*;
import edu.up.cs301.game.GameFramework.GamePlayer;
/*

 */
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

/**
 * @author Will
 * @version 04/7/2020
 */

class CribCardsToTable extends GameAction {

    // Instance Variables

    private Card cardsToTable;
    private GamePlayer player;

    public CribCardsToTable(GamePlayer player, Card card) {
        super(player);
        cardsToTable = card;
    } // Constructor for the CardsToTable gameAction

    /**
     * Gets the cards from the action
     * @return cards stored
     */

    public Card cards(){
        return cardsToTable;
    }

    /**
     * Gets the player who sent the action
     * @return  player
     */

    public GamePlayer player(){
        return player;
    }
}