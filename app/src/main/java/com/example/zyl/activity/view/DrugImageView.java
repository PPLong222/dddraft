package com.example.zyl.activity.view;

import android.animation.AnimatorListenerAdapter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zyl.Helper.ImagePositionManager;

@SuppressLint("AppCompatCustomView")
public class DrugImageView extends ImageView implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {
    private OnImageDragListener listener;
    private ScaleGestureDetector scaleGestureDetector;
    private long startTime;
    private long endTime;
    private int lastX;
    private int lastY;
    private int offsetX;
    private int offsetY;
    private View rootView;
    private boolean isFirstClicked = true;
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
        setScaleType(ScaleType.CENTER_CROP);
        rootView = this.getRootView();
        scaleGestureDetector = new ScaleGestureDetector(rootView.getContext(), this);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //解决滑动时带来的 ACTION_CANCEL 的问题（事件分发）
        getParent().requestDisallowInterceptTouchEvent(true);

        int fingerCount = event.getPointerCount();
        scaleGestureDetector.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isUpRepeat = false;
                    lastX = (int) event.getX();
                    lastY = (int) event.getY();
                    startTime = System.currentTimeMillis();
                    endTime = System.currentTimeMillis();
                    // 还原初始点击时 相对图片的xy 坐标
                    listener.onDragDown();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (fingerCount == 1) {
                        if (endTime - startTime > ClickTimeDuration) {
                            offsetX = (int) (event.getX() - lastX);
                            offsetY = (int) (event.getY() - lastY);
                            rootView.layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                            break;
                        }
                    }
                case MotionEvent.ACTION_UP:
                    endTime = System.currentTimeMillis();
                    // 这也是很重要的一步， 在layout时不会改变param的数据
                    //重新添加后位置就会初始化，因此这里需要手动的更改其param参数并设置
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootView.getLayoutParams();
                    params.topMargin = getTop();
                    params.leftMargin = getLeft();
                    rootView.setLayoutParams(params);

                    if (endTime - startTime < ClickTimeDuration) {

                    }
                    isFirstClicked = true;
                    listener.onDragUp();
                    break;

            }

        return true;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d(Tag, "enter" + detector.getScaleFactor());
        originFactor *= detector.getScaleFactor();
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


    }

}
