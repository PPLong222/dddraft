package com.example.dddraft;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final int OPEN_ALBUM = 2;
    private String TAG = "AlbumHelper";

    public static void openGalary(Activity activity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        activity.startActivityForResult(intent, OPEN_ALBUM);
    }

    //get  Image Path from uri
    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = "";
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //get true ImagePath from intent data
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String utilHandler(Context context, Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {

            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {

                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.d("Album", "111111111" + imagePath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }


}