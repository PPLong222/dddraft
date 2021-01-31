package com.example.zyl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.R;
import com.example.zyl.StrUtil;
import com.example.zyl.model.AllUsedPictures;
import com.example.zyl.recycle.adapter.UsedPhotoRecyclerViewAdater;
import com.example.zyl.recycle.decoration.AllUsedPicturesItemDecoration;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TextActivity extends Activity  {

    private EditText et_cont;
    private Button tv_name;
    private ImageButton button_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);


        setUi();
    }


    @SuppressLint("CutPasteId")
    private void setUi(){
        button_back= findViewById(R.id.button_back);
        et_cont=findViewById(R.id.et_cont);
        tv_name=findViewById(R.id.tv_name);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StrUtil.isEmpty(et_cont.getText().toString())) {
                    Toast.makeText(TextActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                getIntent().putExtra("text",et_cont.getText().toString());
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });

    }




}
