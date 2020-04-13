package edu.up.cs301.cribbage;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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

    // These variables will reference widgets that will be modified during play
    private TextView playerScoreTextView = null;
    private TextView oppScoreTextView    = null;

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
    private int indexOfCard1;
    private int count;

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
        count = 1;
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
            if(playerNum == 0){
                playerScoreTextView.setText(state.getScore(0)+ "");
            }
            if(playerNum == 1){
                playerScoreTextView.setText(state.getScore(1)+ "");
            }
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
        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//its a warning

        // set the surfaceView instance variable
        surfaceView = (CribSurfaceView)myActivity.findViewById(R.id.surfaceView);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);

        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
        surfaceView.setState(state);

        Card.initImages(activity);
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
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int xGet = (int) event.getX();
        int yGet = (int) event.getY();
        int indexOfTouch = surfaceView.mapPixelToPosition(xGet, yGet);
        System.out.println("Index pf touch " + indexOfTouch);
        if(count%2 != 0){
            indexOfCard1 = indexOfTouch;
        }

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game

        if (indexOfTouch == -1) {
            surfaceView.flash(Color.RED, 50);
        } else {
            if(state.getGameStage() == CribState.THROW_STAGE){

                if(count%2 == 0) {
                    System.out.print("This is comp1 " + playerNum);
                    game.sendAction(new CribThrowAction(this, indexOfCard1, indexOfTouch));

                }
                count++;
                surfaceView.invalidate();
            } else if(state.getGameStage() == CribState.PLAY_STAGE){
                game.sendAction(new CribPlayAction(this,indexOfCard1));
                surfaceView.invalidate();
            }
        }

        // register that we have handled the event
        return true;

    }

}
