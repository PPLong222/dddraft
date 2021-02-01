package com.example.zyl.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.zyl.R;

public class RecButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {
    public RecButton(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.red_rec, null);
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
