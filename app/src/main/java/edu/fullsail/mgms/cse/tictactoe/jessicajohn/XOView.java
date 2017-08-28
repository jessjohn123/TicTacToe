package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


/**
 * Created by ushnashah on 8/22/17.
 */

public class XOView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    public Bitmap mXBitmap;
    public Bitmap mOBitmap;
    private Paint mPaint;
    private boolean mIHaveBeenTouched;
    private GameActivty.GamePlayer mDrawXorOorNone = GameActivty.GamePlayer.NONE;

    public XOView(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }

    public XOView(Context context, AttributeSet attrb){
        super(context, attrb);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }

    public XOView(Context context, AttributeSet attrb, int defStyle){
        super(context, attrb);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }
    private void init(Context context){

        mOBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        mXBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
        mPaint = new Paint();

        //Just to make sure that onDraw happens when I touch the screen
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas c){
        super.onDraw(c);
        c.drawColor(Color.WHITE);

        if(mDrawXorOorNone == GameActivty.GamePlayer.X){
            c.drawBitmap(mXBitmap,0,0,mPaint);
        }
        else if(mDrawXorOorNone == GameActivty.GamePlayer.O){
            c.drawBitmap(mOBitmap,0,0,mPaint);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        System.out.println("Creating" + getId());

        Canvas c = null;

        try{
            c = holder.lockCanvas(null);
            mOBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
            mOBitmap = Bitmap.createScaledBitmap(mOBitmap, c.getWidth(), c.getHeight(), false);
            mXBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
            mXBitmap = Bitmap.createScaledBitmap(mXBitmap, c.getWidth(), c.getHeight(), false);
            synchronized (holder){
                System.out.println("Resizing image content");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error occured while scaling the image");
        }finally {
            if(c != null)
                holder.unlockCanvasAndPost(c);
        }

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            mIHaveBeenTouched = true;

        return false;
    }

    public boolean getTouchedSign(){
        return mIHaveBeenTouched;
    }

    public void Drawing(GameActivty.GamePlayer d){
        mDrawXorOorNone = d;
    }

}
