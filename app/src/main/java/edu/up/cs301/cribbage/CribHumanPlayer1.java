package edu.up.cs301.cribbage;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.utilities.Logger;

import java.util.ArrayList;
import java.util.Random;

/**
 * A GUI that allows a human to play tic-tac-toe. Moves are made by clicking
 * regions on a canvas
 *
 * @author Steven R. Vegdahl
 * @version September 2016
 */
public class CribHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener {
    //Tag for logging
    private static final String TAG = "TTTHumanPlayer1";
    // the current activity
    private Activity myActivity;

    // the surface view
    private CribSurfaceView surfaceView;

    public CribState getState() {
        return state;
    }

    // the state
    private CribState state;

    // the ID for the layout to use
    private int layoutId;

    private ArrayList<Card> cardHand1 = new ArrayList();
    private Random ran = new Random();

    CribState crib;

    private Card[] tempHand;//temporary hand for changing
    private Card[] selectedCards = new Card[2];
    private Card[] cardsOnTable;//arraylist of cards currently on the table
    private Card[] currCrib;
    private CribMoveAction action;//action that will be sent to the game


    /**
     * constructor
     *
     * @param name
     *        the player's name
     * @param layoutId
     *      the id of the layout to use
     */
    public CribHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
        ArrayList<Card> cards1 = crib.getDeck();

        //Randomly picks 6 cards from 52 deck to have in start
        for (int i = 0; i < 6; i++){
            int nxt = ran.nextInt(52);
            cards1.add(cards1.get(nxt));
            cards1.remove(nxt);
        }
    }

    public CribState getCribState(){
        return state;
    }



    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof CribState))
            // if we do not have a TTTState, ignore
            return;
        else {
            state = (CribState)info;
            surfaceView.setState(state);
            surfaceView.invalidate();
            Logger.log(TAG, "receiving");
        }
    }

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activity
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (CribSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
        surfaceView.setState(state);
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Tic-Tac-Toe: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch (which we'll detect on
     * the "up" movement" onto a tic-tac-tie square
     *
     * @param event
     * 		the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int xGet = (int) event.getX();
        int yGet = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(xGet, yGet);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            if(state.getGameStage() ==  CribState.sendTocrib){
                CribCribAction action = new CribCribAction(this, selectedCards[0],selectedCards[1]);
            }else if(state.getGameStage() == CribState.sendToPlay){
                CribPlayAction action = new CribPlayAction(this,selectedCards[0]);
            }
            Logger.log("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){//only selected on down motion
            float x = event.getX();//coordinates of touch
            float y = event.getY();

            if(state != null && state.getTurn() == CribState.PLAYER_1){//checks if state is not null, cecks stage
                //and checks whose turn it is
                for(int i = 0; i<tempHand.length; i++){
                        selectCard(tempHand[i]);//selects card that user touched

                }
            }
        }

        // register that we have handled the event
        return true;

    }

    /**
     * Method called when player clicks confirm button.
     * This method takes the selected cards and creates an action based on the current stage
     * 	and sends that action to the game.
     */
/*  public final void confirm() {
        if(!isEmpty(selectedCards)){//do only if cards have been selected
            action = null;//resets action
            if(state.getGameStage() == CribState.THROW_STAGE){//checks if game is in throw stage
                if(isFull(selectedCards)){
                    for(int i = 0; i < selectedCards.length;i++){
                        int cardPos = indexOfCard(tempHand, selectedCards[i]);
                        if (cardPos >=0 && cardPos < tempHand.length){
                            //removes cards
                            tempHand[cardPos] = null;
                        }
                    }
                    action = new CribCardsToThrow(this, selectedCards);//sets action
                    game.sendAction(action);//sends game action
                }
            }
            else if(state.getGameStage() == CribState.PEG_STAGE){//checks if game is in peg stage
                    action = new CribCardsToTable(this, selectedCards[0]);//sets action
                    int cardPos = indexOfCard(tempHand, selectedCards[0]);


                    game.sendAction(action);//sends game action
                    if (cardPos >=0 && cardPos < tempHand.length){
                        tempHand[cardPos] = null;//gets index of card played and removes the card
                    }

            }
            //resets selected cards
            selectedCards[0] = null;
            selectedCards[1] = null;

        }
    }*/

    /**
     * Method that handles selecting a card that the user has chosen.
     * If the card has not been already chosen, it adds that card to the cards selected (Max 2)
     * If the card has already been chosen, it deselects that card
     * @param card  Card that was touched
     */
    private void selectCard(Card card) {
        if(state.getGameStage() == CribState.sendTocrib& isFull(tempHand)){//if the temp hand is full and its the throw stage
            if(selectedCards[0] == card){
                selectedCards[0] = null;//deselects
            }else if(selectedCards[1] == card){
                selectedCards[1] = null;//deselects
            }else if(selectedCards[0] == null){
                selectedCards[0] = card;//selects
            }else if(selectedCards[1] == null){
                selectedCards[1] = card;//selects
            }
        }else if(state.getGameStage() == CribState.sendToPlay){
            if(selectedCards[0] == null){
                selectedCards[0] = card;//selects
            }else if(selectedCards[0] == card){
                selectedCards[0] = null;//deselects
            }
        }
    }

    /**
     * Checks if the specified array is full
     * @param arr  Array to check
     * @return  if it is full or not
     */
    private boolean isFull(Card[] arr){
        for(Card c: arr){//checks if every element in the array is not null
            if(c==null){return false;}
        }
        return true;
    }

    /**
     * Return the index of the specified card in the specified array
     * @param a  Array to search in
     * @param c  Card to search for
     * @return  Index of the card, -1 if card is not found
     */
    private int indexOfCard(Card[] a, Card c){
        for(int i =0; i<a.length;i++){//iterate through array searching for card
            if(a[i]==c){
                return i;//index of card touched
            }
        }
        return 0;//error value
    }

    private boolean isEmpty(Card[] arr){
        for(Card c: arr){
            if(c!=null)return false;
        }
        return true;
    }

}
