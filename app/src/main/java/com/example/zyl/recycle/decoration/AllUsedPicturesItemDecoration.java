package com.example.zyl.recycle.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllUsedPicturesItemDecoration extends RecyclerView.ItemDecoration {

    private Paint myPaint;
    public AllUsedPicturesItemDecoration() {
        super();
        myPaint=new Paint();
        myPaint.setColor(Color.BLACK);
    }

    // set offset for each img and depends on its count
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int count=parent.getChildCount();
        outRect.left = 10;
        if(count % 3 == 3){
            outRect.right = 10;
        }
        if(count>3){
            outRect.top = 10 ;
        }
    }
}
