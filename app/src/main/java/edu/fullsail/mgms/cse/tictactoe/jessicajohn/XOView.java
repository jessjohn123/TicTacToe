package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


/**
 * Created by ushnashah on 8/22/17.
 */

public class XOView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    private SurfaceHolder mHolder;
    private byte mValue;
    private Paint mPaintToken;
    private Paint mPaintText;
    private Rect mFieldDim;
    private int mLocation;

    public XOView(Context context){
        super(context);
        getHolder().addCallback(this);
        //init(context);
    }

    public XOView(Context context, AttributeSet attrb){
        super(context, attrb);
        getHolder().addCallback(this);
        //init(context);
    }

    public XOView(Context context, AttributeSet attrb, int defStyle){
        super(context, attrb);
        getHolder().addCallback(this);
        //init(context);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    @Override
    public void onDraw(Canvas canvas){

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;
    }
}
