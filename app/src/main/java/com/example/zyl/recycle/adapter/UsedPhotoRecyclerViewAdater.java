package com.example.zyl.recycle.adapter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zyl.R;
import com.example.zyl.model.AllUsedPictures;

import java.io.FileNotFoundException;
import java.util.List;

public class UsedPhotoRecyclerViewAdater extends RecyclerView.Adapter<UsedPhotoRecyclerViewAdater.ViewHolder> {
    private List<AllUsedPictures> myLists;
    private OnUsedImgClickedListener onUsedImgClickedListener;
    public UsedPhotoRecyclerViewAdater(List myLists) {
        this.myLists = myLists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.single_img);
        }
    }

    public void setOnUsedImgClickedListener(OnUsedImgClickedListener onUsedImgClickedListener) {
        this.onUsedImgClickedListener = onUsedImgClickedListener;
    }
    public interface OnUsedImgClickedListener{
        void onImgClicked(View view,int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(myLists != null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_img, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path=myLists.get(position).getImgUrl();
        // use Glide to load pictures async.
        Glide.with(holder.itemView.getContext()).load(path).into(holder.imageView);
        // set listener to evert ImgView , with a callback outside.
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               onUsedImgClickedListener.onImgClicked(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

}


