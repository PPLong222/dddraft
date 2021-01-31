package com.example.zyl.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraHelper {
    // 申请相机权限的requestCode

    public static Uri mCameraUri;

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    public static String mCameraImagePath;

    // 是否是Android 10以上手机
    public static boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;
    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    public static void checkPermissionAndCamera(Activity activity) {
        int hasCameraPermission = ContextCompat.checkSelfPermission(activity.getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera(activity);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},
                    Constants.PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 调起相机拍照
     */
    public static void openCamera(Activity activity) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri(activity);
            } else {
                try {
                    photoFile = createImageFile(activity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                Log.d("CameraHelper",photoUri.toString());
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                activity.startActivityForResult(captureIntent, Constants.CAMERA_REQUEST_CODE);
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    public static Uri createImageUri(Activity activity) {
        Log.d("createImageUri","in");
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return activity.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    public static File createImageFile(Activity activity) throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }
    public static String getmCameraImagePath(){
        return mCameraImagePath;
    }

    ////////////////////

}
