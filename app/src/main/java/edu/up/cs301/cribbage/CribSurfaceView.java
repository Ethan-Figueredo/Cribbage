package edu.up.cs301.cribbage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.cribbage.CribState;
import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;

/**
 * A SurfaceView which allows which an animation to be drawn on it by a
 * Animator.
 *
 * @author Steve Vegdahl
 * @version 23 September 2016
 *
 *
 */
public class CribSurfaceView extends FlashSurfaceView {
    //Tag for logging
    private static final String TAG = "TTTSurfaceView";

    // some constants, which are percentages with respect to the minimum
    // of the height and the width. All drawing will be done in the "middle
    // square".
    //
    // The divisions both horizontally and vertically within the
    // playing square are:
    // - first square starts at 5% and goes to 33%
    // - second square starts at 36% and goes to 64%
    // - third square starts at 67& and goes to 95%
    // There is therefore a 5% border around the edges; each square
    // is 28% high/wide, and the lines between squares are 3%
    private final static float BORDER_PERCENT = 5; // size of the border
    private final static float SQUARE_SIZE_PERCENT = 28; // size of each of our 9 squares
    private final static float LINE_WIDTH_PERCENT = 3; // width of a tic-tac-toe line
    private final static float SQUARE_DELTA_PERCENT = SQUARE_SIZE_PERCENT
            + LINE_WIDTH_PERCENT; // distance from left (or top) edge of square to the next one

    /*
	 * Instance variables
	 */

    // the game's state
    protected CribState state;

    // the offset from the left and top to the beginning of our "middle square"; one
    // of these will always be zero
    protected float hBase;
    protected float vBase;

    // the size of one edge of our "middle square", or -1 if we have not determined
    // size
    protected float fullSquare;

    /**
     * Constructor for the TTTSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public CribSurfaceView(Context context) {
        super(context);
        init();
    }// ctor

    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout.
     *
     * @param context - a reference to the activity this animation is run under
     * @param attrs   - set of attributes passed from system
     */
    public CribSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    /**
     * Helper-method for the constructors
     */
    private void init() {
        setBackgroundColor(Color.GREEN);
    }// init


    public void setState(CribState state) {
        this.state = state;
    }

    /**
     * callback method, called whenever it's time to redraw
     * frame
     *
     * @param g
     * 		the canvas to draw on
     */
    public void onDraw(Canvas g) {
        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }

        Paint blue = new Paint(); //drawing the crib
        Paint red = new Paint();
        blue.setColor(Color.BLUE);
        red.setColor(Color.RED);
        System.out.println(getWidth()-100 + " This is it");

        RectF crib = new RectF(0, 300, 500, 600);
        RectF hand0 = new RectF(500,getHeight(),getWidth()-100,getHeight()-300);
        RectF hand1 = new RectF(500,0,getWidth()-100, 300);

        g.drawRect(crib, blue);
        g.drawRect(hand0, blue);
        g.drawRect(hand1, blue);

        RectF crib1 = new RectF(0,300,125,600);
        RectF crib2 = new RectF(125,300,125,600);
        RectF crib3 = new RectF(250,300,125,600);
        RectF crib4 = new RectF(375,300,125,600);

        int width = getWidth() - 100;
        int height = getHeight()-300;
        RectF hand00 = new RectF(500,getHeight(), width/6,height);
        RectF hand01 = new RectF(500 + width/6, getHeight(), width/3, height);
        RectF hand02 = new RectF(500 + width/3, getHeight(),width/2,height);
        RectF hand03 = new RectF(500 + width/2, getHeight(),4*width/6,height);
        RectF hand04 = new RectF(500 + 4*width/6, getHeight(),5*width/6,height);
        RectF hand05 = new RectF(500 + width, getHeight(),width,height);

        RectF hand10 = new RectF(500, 0, width/6, 300);
        RectF hand11 = new RectF(500 + width/6, 0, width/3, 300);
        RectF hand12 = new RectF(500+width/3, 0, width/2, 300);
        RectF hand13 = new RectF(500+width/2, 0, 4*width/6, 300);
        RectF hand14 = new RectF(500+4*width/6, 0, 5*width/6, 300);
        RectF hand15 = new RectF(500+5*width/6, 0, width, 300);


        ArrayList<RectF> cribBox = new ArrayList<>();
        cribBox.add(crib1);
        cribBox.add(crib2);
        cribBox.add(crib3);
        cribBox.add(crib4);

        ArrayList<RectF> hand0Box = new ArrayList<>();
        hand0Box.add(hand00);
        hand0Box.add(hand01);
        hand0Box.add(hand02);
        hand0Box.add(hand03);
        hand0Box.add(hand04);
        hand0Box.add(hand05);

        ArrayList<RectF> hand1Box = new ArrayList<>();
        hand1Box.add(hand10);
        hand1Box.add(hand11);
        hand1Box.add(hand12);
        hand1Box.add(hand13);
        hand1Box.add(hand14);
        hand1Box.add(hand15);

        int n = state.getHand(0).size();
        int x = state.getHand(1).size();
        int z = state.getCrib().size();
        //draw the crib
        for(int i = 0; i < z; i ++) {
            Card temp = state.getCrib().getCard(i);
            if (temp!=null) {
                drawCard(g, cribBox.get(i), temp);
            }
        }
        for(int i = 0; i < n; i ++) {
            Card temp = state.getHand(0).getCard(i);
            if (temp!=null) {
                drawCard(g, hand0Box.get(i), temp);
            }
        }
        for(int i = 0; i < x; i++){
            Card temp = state.getHand(1).getCard(i);
                if(temp!=null){
                    drawCard(g,hand1Box.get(i), temp);
                }

        }
    }

    /**
     * maps a point from the canvas' pixel coordinates to "square" coordinates
     *
     * @param x
     * 		the x pixel-coordinate
     * @param y
     * 		the y pixel-coordinate
     * @return
     *		a Point whose components are in the range 0-2, indicating the
     *		column and row of the corresponding square on the tic-tac-toe
     * 		board, or null if the point does not correspond to a square
     */
    public int mapPixelToPosition(int x, int y) {
        // no match: return null
        if(y < getHeight() - 300 || x < getWidth()/6) {
            return -1; //illegal square
        }
        if(x>getWidth()/6 &&x < getWidth()/3){
            return 0;
        }else if(x > getWidth()/3 && x < getWidth()/2){
            return 1;
        } else if(x > getWidth()/2 && x < 4*getWidth()/6) {
            return 2;
        }else if(x > 4*getWidth()/6 && x < 5*getWidth()/6){
            return 3;
        }else if(x>5*getWidth()/6 && x < getWidth()){
            return 4;
        }else if(x<getWidth()){
            return 5;
        }
        return -1;
    }

    /**
     * draws a sequence of card-backs, each offset a bit from the previous one, so that all can be
     * seen to some extent
     *
     * @param g
     * 		the canvas to draw on
     * @param topRect
     * 		the rectangle that defines the location of the top card (and the size of all
     * 		the cards
     * @param deltaX
     * 		the horizontal change between the drawing position of two consecutive cards
     * @param deltaY
     * 		the vertical change between the drawing position of two consecutive cards
     * @param numCards
     * 		the number of card-backs to draw
     */
    private void drawCardBacks(Canvas g, RectF topRect, float deltaX, float deltaY,
                               int numCards) {
        // loop through from back to front, drawing a card-back in each location
        for (int i = numCards-1; i >= 0; i--) {
            // determine theh position of this card's top/left corner
            float left = topRect.left + i*deltaX;
            float top = topRect.top + i*deltaY;
            // draw a card-back (hence null) into the appropriate rectangle
            drawCard(g,
                    new RectF(left, top, left + topRect.width(), top + topRect.height()),
                    null);
        }
    }

    /**
     * draws a card on the canvas; if the card is null, draw a card-back
     *
     * @param g
     * 		the canvas object
     * @param rect
     * 		a rectangle defining the location to draw the card
     * @param c
     * 		the card to draw; if null, a card-back is drawn
     */
    private static void drawCard(Canvas g, RectF rect, Card c) {
        if (c == null) {
            // null: draw a card-back, consisting of a blue card
            // with a white line near the border. We implement this
            // by drawing 3 concentric rectangles:
            // - blue, full-size
            // - white, slightly smaller
            // - blue, even slightly smaller
            Paint white = new Paint();
            white.setColor(Color.WHITE);
            Paint blue = new Paint();
            blue.setColor(Color.BLUE);
            RectF inner1 = scaledBy(rect, 0.96f); // scaled by 96%
            RectF inner2 = scaledBy(rect, 0.98f); // scaled by 98%
            g.drawRect(rect, blue); // outer rectangle: blue
            g.drawRect(inner2, white); // middle rectangle: white
            g.drawRect(inner1, blue); // inner rectangle: blue
        }
        else {
            // just draw the card
            c.drawOn(g, rect);
        }
    }

    /**
     * scales a rectangle, moving all edges with respect to its center
     *
     * @param rect
     * 		the original rectangle
     * @param factor
     * 		the scaling factor
     * @return
     * 		the scaled rectangle
     */
    private static RectF scaledBy(RectF rect, float factor) {
        // compute the edge locations of the original rectangle, but with
        // the middle of the rectangle moved to the origin
        float midX = (rect.left+rect.right)/2;
        float midY = (rect.top+rect.bottom)/2;
        float left = rect.left-midX;
        float right = rect.right-midX;
        float top = rect.top-midY;
        float bottom = rect.bottom-midY;

        // scale each side; move back so that center is in original location
        left = left*factor + midX;
        right = right*factor + midX;
        top = top*factor + midY;
        bottom = bottom*factor + midY;

        // create/return the new rectangle
        return new RectF(left, top, right, bottom);
    }
}