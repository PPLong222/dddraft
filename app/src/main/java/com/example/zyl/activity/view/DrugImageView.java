package com.example.zyl.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zyl.Helper.ImagePositionManager;

@SuppressLint("AppCompatCustomView")
public class DrugImageView extends ImageView implements View.OnTouchListener,ScaleGestureDetector.OnScaleGestureListener  {
    private OnImageDragListener listener;
    private ScaleGestureDetector scaleGestureDetector;
    private long startTime;
    private long endTime;
    private int initX;
    private int initY;
    private boolean isFirstClicked= true;
    private long ClickTimeDuration = 100;
    private boolean isUpRepeat = false;

    private float originFactor = 1.0f;

    public void setListener(OnImageDragListener listener) {
        this.listener = listener;
    }



    private static String Tag = "DrugImageView" ;
    public DrugImageView(@NonNull Context context) {
        super(context);
        setOnTouchListener(this);
        scaleGestureDetector= new ScaleGestureDetector(context,this);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //解决滑动时带来的 ACTION_CANCEL 的问题（事件分发）
        getParent().requestDisallowInterceptTouchEvent(true);
        int fingerCount = event.getPointerCount();
        scaleGestureDetector.onTouchEvent(event);
        Log.d(Tag,""+fingerCount);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isUpRepeat = false;
                    startTime = System.currentTimeMillis();
                    endTime = System.currentTimeMillis();
                    listener.onDragDown();
                    Log.d(Tag, "down");
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(fingerCount ==1 ) {
                        if (endTime - startTime > ClickTimeDuration) {
                            if (isFirstClicked) {
                                initX = (int) event.getRawX();
                                initY = (int) event.getRawY();
                                isFirstClicked = false;
                            }
                            listener.onMove(v, (int) event.getRawX(), (int) event.getRawY(), initX, initY);

                            break;
                        }
                    }
                case MotionEvent.ACTION_UP:
                    endTime = System.currentTimeMillis();
                    Log.d(Tag, "up");
                    if (endTime - startTime < ClickTimeDuration) {
                        listener.onClicked(initX, initY);
                        //setOnTouchListener(null);

                    }
                    listener.onDragUp();
                    break;

            }

        return true;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        originFactor *=detector.getScaleFactor();
        setScaleX(originFactor);
        setScaleY(originFactor);

        return false;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d("Drug","begin");
        return true;
    }


    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d("Drug","END");

    }


    public interface OnImageDragListener{
        void onDragDown();
        void onDragUp();
        void onClicked(double x,double y);
        void onMove(View v,int rawx,int rawy,int x,int y);
    }

}
