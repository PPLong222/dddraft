package com.example.zyl.activity.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zyl.R;

public class WriteableEditText extends ConstraintLayout implements View.OnTouchListener {
    private EditText editText;
    private String Tag = getClass().getSimpleName();
    private int initX;
    private int initY;
    private View borderView;
    private long firstClickTime = 0;
    private Button dragButton;
    private long secondClickTime = 0;
    private long timeDuration = 200;
    private boolean isFirstClicked = true;
    private OnEditTextListener listener;
    private View view;
    private View rootView;

    public WriteableEditText(@NonNull Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.writebale_edittext, null);
        addView(view);
        //这一步十分关键！
        rootView = this.getRootView();
        editText = findViewById(R.id.year_editText);

        dragButton = findViewById(R.id.textdrag_button);
        borderView = findViewById(R.id.dotted_border);

        dragButton.setOnTouchListener((v, event) -> {
            getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    firstClickTime = System.currentTimeMillis();
                    if (firstClickTime - secondClickTime > timeDuration) {
                        Toast.makeText(context, ">200", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isFirstClicked) {
                        initX = (int) event.getRawX();
                        initY = (int) event.getRawY();
                        isFirstClicked = false;
                    }
                    listener.onMove(rootView, (int) event.getRawX(), (int) event.getRawY(), initX, initY);
                    break;
                case MotionEvent.ACTION_UP:
                    secondClickTime = System.currentTimeMillis();
                    if (secondClickTime - firstClickTime < timeDuration) {
                        if (dragButton.getAlpha() != 0) {
                            dragButton.setAlpha(0);
                            borderView.setAlpha(0);
                        } else {
                            dragButton.setAlpha(1);
                            borderView.setAlpha(1);
                        }
                    }
                    break;
            }
            return true;
        });
        setOnTouchListener(this);

    }

    public void setEditTextListener(OnEditTextListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstClickTime = System.currentTimeMillis();
                if (secondClickTime - firstClickTime > timeDuration) {

                }
                Log.d(Tag, "down");
                break;
            case MotionEvent.ACTION_MOVE:
/*
                if (isFirstClicked) {
                    initX = (int) event.getRawX();
                    initY = (int) event.getRawY();
                    isFirstClicked = false;
                }
                listener.onMove(v, (int) event.getRawX(), (int) event.getRawY(), initX, initY);*/

                break;

            case MotionEvent.ACTION_UP:
                secondClickTime = System.currentTimeMillis();

                Log.d(Tag, "up");

                break;

        }

        return true;
    }

    public interface OnEditTextListener {
        void onClicked(double x, double y);

        void onMove(View v, int rawx, int rawy, int x, int y);
    }
}
