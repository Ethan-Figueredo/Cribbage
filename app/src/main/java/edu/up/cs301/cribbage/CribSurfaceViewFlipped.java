package edu.up.cs301.cribbage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;

import edu.up.cs301.cribbage.CribSurfaceView;

/**
 * A SurfaceView which allows which an animation to be drawn on it by a
 * Animator.
 *
 * @author Steve Vegdahl
 * @version 23 September 2016
 *
 *
 */
public class CribSurfaceViewFlipped extends CribSurfaceView {
    //Tag for logging
    private static final String TAG = "TTTSurfaceViewFlipped";
    /*
	 * Instance variables
	 */

    /**
     * Constructor for the TTTSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public CribSurfaceViewFlipped(Context context) {
        super(context);
    }// ctor

    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout.
     *
     * @param context - a reference to the activity this animation is run under
     * @param attrs   - set of attributes passed from system
     */
    public CribSurfaceViewFlipped(Context context, AttributeSet attrs) {
        super(context, attrs);
    }// ctor

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int foregroundColor() {
        return Color.RED;
    }

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int backgroundColor() {
        return Color.YELLOW;
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
    public int mapPixelToSquare(int x, int y) {

        // get point that superclass would have
        int p = super.mapPixelToPosition(x, y);

        // return "inverted" point
        return p;
    }

}