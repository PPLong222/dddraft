package com.example.zyl.activity.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;

import com.example.zyl.R;

public class YearSelectorView extends AlertDialog {
    private OnYearSelectorDismissedListener listener;
    private DatePicker datePicker;
    private Button buttonConfirm;
    public YearSelectorView(Context context) {
        super(context);
        View view= LayoutInflater.from(context).inflate(R.layout.year_selector,null);
        setView(view);

        setUi(view);
    }

    public void setListener(OnYearSelectorDismissedListener listener) {
        this.listener = listener;
    }

    public interface OnYearSelectorDismissedListener{
        void onDismissed(int year,int month);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUi(View view){
        datePicker = view.findViewById(R.id.year_datePicker);
        buttonConfirm = view.findViewById(R.id.year_button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDismissed(datePicker.getYear(),datePicker.getMonth());
                dismiss();
            }
        });
    }
}
