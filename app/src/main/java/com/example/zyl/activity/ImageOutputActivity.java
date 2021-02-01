package com.example.zyl.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.zyl.Helper.Constants;
import com.example.zyl.Helper.ViewCutHelper;
import com.example.zyl.R;

import java.io.File;


public class ImageOutputActivity extends Activity implements View.OnClickListener {
    private Button buttonBack;
    private Button buttonConfirm;
    private RadioGroup radioGroup;
    private ImageView imageView;
    private String img_path;
    private File imgFile;
    private int year;
    private ViewCutHelper viewCutHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_output);
        setUi();

        getCurrentYear();

        // load image
        Glide.with(this).load(img_path).into(imageView);
    }

    private void getCurrentYear() {
        img_path = getIntent().getStringExtra("img_path");
        imgFile = new File(img_path);
        img_path.lastIndexOf(".");
        year = Integer.parseInt(img_path.substring(img_path.lastIndexOf(".") - 4, img_path.lastIndexOf(".")));
    }

    private void setUi() {
        buttonBack = findViewById(R.id.head_view_back);
        buttonConfirm = findViewById(R.id.output_confirm);
        radioGroup = findViewById(R.id.img_output_radiogroup);
        imageView = findViewById(R.id.output_select_image);

        buttonConfirm.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        radioGroup.setOnClickListener(this);

        viewCutHelper = new ViewCutHelper();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_view_back:
                onBackPressed();
                viewCutHelper.deleteFile(img_path);
                break;
            case R.id.output_confirm:
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioGroup_jpg:
                        viewCutHelper.renameFile(img_path, ".jpg", year);

                        break;
                    case R.id.radioGroup_png:
                        viewCutHelper.renameFile(img_path, ".png", year);
                        break;
                }
                onBackPressed();
                break;
        }
    }
}
