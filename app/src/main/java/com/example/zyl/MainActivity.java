package com.example.zyl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.zyl.activity.writes.MonthWrite;
import com.example.zyl.activity.writes.WeekWrite;
import com.example.zyl.activity.writes.YearWrite;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_READ = 1 ;
    private Button buttonWeek;
    private Button buttonMonth;
    private Button buttonYear;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);

        setUi();
        legacyRequest();
    }

    public void setUi(){
        buttonWeek=findViewById(R.id.buttonWeek);
        buttonMonth=findViewById(R.id.buttonMonth);
        buttonYear=findViewById(R.id.buttonYear);

        buttonWeek.setOnClickListener(this);
        buttonMonth.setOnClickListener(this);
        buttonYear.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonWeek:
                intent=new Intent(MainActivity.this, WeekWrite.class);
                break;
            case R.id.buttonMonth:
                intent=new Intent(MainActivity.this, MonthWrite.class);
                break;
            case R.id.buttonYear:
                intent=new Intent(MainActivity.this, YearWrite.class);
                break;
        }
        startActivity(intent);
    }
    public void legacyRequest(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_READ:
//                if(grantResults[0]!= PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this, "You need to agree this legacy", Toast.LENGTH_SHORT).show();
//                    finish();
//                }else{
//
//                }
                break;
            default:
                break;
        }
    }
}
