package com.example.zyl.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ViewCutHelper {
    private static String Tag = ViewCutHelper.class.getSimpleName();
    private String dirFileOfYear = "/draft/viewCuts/byYear";

    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(c);
        else
            c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);
        return b;
    }
    //save bitmap to system storage and return its uri.
    public static String saveImg(Bitmap bitmap, int year, Context context) {
        try {
            String sdcardPath = System.getenv("EXTERNAL_STORAGE");      //获得sd卡路径
            String dir = sdcardPath + "/draft/viewCuts/byYear";                             //图片保存的文件夹名
            File file = new File(dir);                                           //已File来构建
            if (!file.exists()) {                                               //如果不存在  就mkdirs()创建此文件夹
                file.mkdirs();
            }
            Log.i("SaveImg", "file uri==>" + dir);
            File mFile = new File(dir + year + ".jpg");                            //将要保存的图片文件
            Log.d(Tag, "ss" + mFile.exists());
            FileOutputStream outputStream = new FileOutputStream(mFile);             //构建输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //compress到输出outputStream
            Uri uri = Uri.fromFile(mFile);                                             //获得图片的uri
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //发送广播通知更新图库，这样系统图库可以找到这张图片
            return uri.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String url) {
        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }

    }

    public void renameFile(String filePath, String fileType, int year) {
        String originType = filePath.substring(filePath.lastIndexOf("."), filePath.length());
        String sdcardPath = System.getenv("EXTERNAL_STORAGE");      //获得sd卡路径
        File file = new File(sdcardPath + dirFileOfYear + year + originType);
        File newFile = new File(sdcardPath + dirFileOfYear + year + fileType);

        file.renameTo(newFile);

    }

}
