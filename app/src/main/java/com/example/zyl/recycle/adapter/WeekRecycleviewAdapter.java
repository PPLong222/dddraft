package com.example.zyl.recycle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zyl.R;

import java.util.List;
import java.util.zip.Inflater;

public class WeekRecycleviewAdapter extends RecyclerView.Adapter {
    private List lists;

    public WeekRecycleviewAdapter(List lists) {
        this.lists = lists;
    }

    public static class ViewHoler extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private ImageView imageView1;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
//            imageView=itemView.findViewById(R.id.imageView);
//            imageView1=itemView.findViewById(R.id.imageView1);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_block,parent,false);
        ViewHoler holer=new ViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
