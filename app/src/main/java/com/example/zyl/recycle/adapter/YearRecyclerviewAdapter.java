package com.example.zyl.recycle.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zyl.Helper.Constants;
import com.example.zyl.R;

import java.util.ArrayList;

public class YearRecyclerviewAdapter extends RecyclerView.Adapter<YearRecyclerviewAdapter.ViewHolder> {
    private ArrayList<String >arrayList = new ArrayList<>();

    public YearRecyclerviewAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_year_rec,null);
        ViewHolder holder = new ViewHolder(view);
        arrayList.add("");
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.monthText.setText((position+1)+"月");
        holder.setIsRecyclable(false);
        holder.editText.setText(arrayList.get(position));
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                arrayList.set(position,s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return Constants.MONTH_NUMBER;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView monthText;
        public EditText editText;
        public ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.year_editText);
            monthText = itemView.findViewById(R.id.year_month);
            parentLayout = itemView.findViewById(R.id.year_inner_layout);
        }
    }

}
