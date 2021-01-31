package com.example.zyl.activity.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zyl.R;

import java.util.Calendar;
import java.util.Date;

public class MonthSelectorView extends AlertDialog {

    private NumberPicker monthPicker;
    private NumberPicker yearPicker;
    private ImageView buttonConfirm;
    private OnMonthDialogDismissedListener listener;
    public MonthSelectorView(Context context) {
        super(context);
        View view= LayoutInflater.from(context).inflate(R.layout.month_selector,null);
        initPicker(view);
        setView(view);
    }

    public void setListener(OnMonthDialogDismissedListener listener) {
        this.listener = listener;
    }

    private void initPicker(View view) {
        monthPicker = view.findViewById(R.id.month_picker);
        yearPicker = view.findViewById(R.id.year_picker);
        buttonConfirm = view.findViewById(R.id.monthselector_confirm);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(1);
        yearPicker.setMinValue(1970);
        yearPicker.setMaxValue(2099);
        yearPicker.setValue(Calendar.getInstance().get(Calendar.YEAR));

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDismissed(yearPicker.getValue(),monthPicker.getValue());
                dismiss();
            }
        });
    }
    // callback listener
    public interface OnMonthDialogDismissedListener{
        void onDismissed(int year, int month);
    }
}
