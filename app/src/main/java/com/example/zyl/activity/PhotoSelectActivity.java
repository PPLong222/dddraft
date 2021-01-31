package com.example.zyl.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.CameraHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.R;
import com.example.zyl.activity.writes.WeekWrite;
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

import static com.example.zyl.Helper.AlbumHelper.OPEN_ALBUM;

public class PhotoSelectActivity extends Activity implements View.OnClickListener{

    private ImageButton imgButtonBack;
    private ImageButton imgButtonCamera;
    private ImageButton imgButtonGallery;
    private RecyclerView usedPhotoRecyclverView;
    private List<AllUsedPictures> allUsedPicturesList;
    private Uri imageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoselect_layout);

        dbConnectionOfAllPictures();
        setUi();
    }
    //connect database
    private void dbConnectionOfAllPictures(){
        Connector.getDatabase();
        allUsedPicturesList=DataSupport.findAll(AllUsedPictures.class);
        for(int i=0;i<allUsedPicturesList.size();i++){
            Log.d("11111",allUsedPicturesList.get(i).getImgUrl().toString());
        }
    }


    @SuppressLint("CutPasteId")
    private void setUi(){
        imgButtonBack=findViewById(R.id.head_panel).findViewById(R.id.button_back);
        imgButtonCamera=findViewById(R.id.head_panel).findViewById(R.id.button_camera);
        imgButtonGallery=findViewById(R.id.head_panel).findViewById(R.id.button_galary);
        usedPhotoRecyclverView=findViewById(R.id.used_photo_recycler);

        imgButtonBack.setOnClickListener(this);
        imgButtonCamera.setOnClickListener(this);
        imgButtonGallery.setOnClickListener(this);
        //Recylerview Settings
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        UsedPhotoRecyclerViewAdater adapter = new UsedPhotoRecyclerViewAdater(allUsedPicturesList);
        AllUsedPicturesItemDecoration decoration = new AllUsedPicturesItemDecoration();
        // callback methods for adapter
        adapter.setOnUsedImgClickedListener(new UsedPhotoRecyclerViewAdater.OnUsedImgClickedListener() {
            @Override
            public void onImgClicked(View view, int position) {
                onSingleImgClicked(position);
            }
        });
        usedPhotoRecyclverView.setAdapter(adapter);
        usedPhotoRecyclverView.addItemDecoration(decoration);
        usedPhotoRecyclverView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_back:
                // need provider or something to keep its state
                /*intent=new Intent(PhotoSelectActivity.this, WeekWrite.class);
                startActivity(intent);*/
                finish();
                break;
            case R.id.button_camera:
                getImages();
                break;
            case R.id.button_galary:
                AlbumHelper.openGalary(this);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("photo",requestCode+"");
            switch (requestCode) {
                case Constants.OPEN_ALBUM:
                    String path = AlbumHelper.utilHandler(this,data);
                    //save url of this photo
                    AllUsedPictures usedPictures = new AllUsedPictures();
                    usedPictures.setImgUrl(path);
                    usedPictures.save();
                    //need state save
                    Intent intent = new Intent();
                    intent.putExtra("img_path",path);
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
                    case Constants.CAMERA_REQUEST_CODE:
                        AllUsedPictures pictures = new AllUsedPictures();
                        pictures.setImgUrl(imageUri.toString());
                        pictures.save();
                        break;
        }


    }
    private void onSingleImgClicked(int index){
        Intent intent = new Intent();
        intent.putExtra("img_path",allUsedPicturesList.get(index).getImgUrl());
        setResult(RESULT_OK,intent);
        finish();
    }

    public  void getImages(){
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date())+".jpg";
        File outputImage=new File(getExternalCacheDir(),imageName);
        try{
            if(outputImage.exists())
                outputImage.delete();
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(PhotoSelectActivity.this,"com.example.zyl.fileprovider",outputImage);
        }else{
            imageUri= Uri.fromFile(outputImage);
        }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,Constants.CAMERA_REQUEST_CODE);
    }


}
