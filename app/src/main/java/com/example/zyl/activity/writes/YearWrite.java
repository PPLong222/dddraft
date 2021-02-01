package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.Helper.EditTestStyleHelper;
import com.example.zyl.Helper.ViewCutHelper;
import com.example.zyl.MainActivity;
import com.example.zyl.R;
import com.example.zyl.activity.ImageOutputActivity;
import com.example.zyl.activity.PhotoSelectActivity;
import com.example.zyl.activity.view.DrugImageView;
import com.example.zyl.activity.view.TextStyleDialog;
import com.example.zyl.activity.view.WriteableEditText;
import com.example.zyl.activity.view.YearSelectorView;
import com.example.zyl.model.CurrentTextStyleModel;

import java.time.format.TextStyle;

import top.defaults.colorpicker.ColorPickerPopup;

import static com.example.zyl.Helper.Constants.ON_OUTPUT_CANCEL;

public class YearWrite extends Activity implements View.OnClickListener {
    private ImageView buttonBack;
    private TextView buttonOversee;
    private TextView textType;
    private Button textStyleButton;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView yearRecyclerView;
    private YearSelectorView selectorView;
    private LinearLayoutManager linearLayout;
    private ScrollView scrollView;
    private RelativeLayout scrollLinearLayout;
    private int currentYear = 1970;
    private TextStyleDialog textStyleDialog;
    private CurrentTextStyleModel currentTextStyle;

    public YearWrite() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myyear);

        setUi();
    }

    private void setUi() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        currentTextStyle = new CurrentTextStyleModel();

        buttonBack = findViewById(R.id.year_head_nav).findViewById(R.id.head_back);
        buttonOversee = findViewById(R.id.year_head_nav).findViewById(R.id.head_text_oversee);
        textType = findViewById(R.id.year_head_nav).findViewById(R.id.head_text);
        textStyleButton = findViewById(R.id.year_bottom_nav).findViewById(R.id.textStyle_choose);
        textType.setText("Year");
        buttonPullBack = findViewById(R.id.year_head_nav).findViewById(R.id.head_pullback);
        buttonAddPicture = findViewById(R.id.year_bottom_nav).findViewById(R.id.choosePhoto);
        buttonAddText = findViewById(R.id.year_bottom_nav).findViewById(R.id.chooseText);
        scrollView = findViewById(R.id.year_scrollView);

        scrollLinearLayout = findViewById(R.id.scrollview_inside);
        for (int i = 1; i <= 12; i++) {
            // BY linear layout
           /* View view = LayoutInflater.from(this).inflate(R.layout.single_year_rec, null);
            TextView viewById = view.findViewById(R.id.year_month);
            viewById.setText(i+"月");
            scrollLinearLayout.addView(view,i-1);*/
            int height = 1029;
            View view = LayoutInflater.from(this).inflate(R.layout.single_year_rec, null);
            TextView viewById = view.findViewById(R.id.year_month);
            viewById.setText(i + "月");

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
            params.topMargin = (i - 1) * 1029;
            scrollLinearLayout.addView(view, params);

        }


        //yearRecyclerView = findViewById(R.id.year_recyclerview);

     /*   YearRecyclerviewAdapter yearRecyclerviewAdapter = new YearRecyclerviewAdapter(new ArrayList<String>());
        linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yearRecyclerView.setAdapter(yearRecyclerviewAdapter);
        yearRecyclerView.setLayoutManager(linearLayout);
        yearRecyclerView.setItemViewCacheSize(13);*/
        //////////////

        buttonBack.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
        textStyleButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back:
                Intent intent = new Intent(YearWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:

                Bitmap bitmapFromView = ViewCutHelper.shotScrollView(scrollView);
                String path = ViewCutHelper.saveImg(bitmapFromView, currentYear, this);
                intent = new Intent(YearWrite.this, ImageOutputActivity.class);
                intent.putExtra("img_path", path);
                startActivityForResult(intent, ON_OUTPUT_CANCEL);
                break;
            case R.id.chooseText:
                generateText(isViewVisible(scrollLinearLayout.getChildAt(0)));
                break;
            case R.id.choosePhoto:
                intent = new Intent(YearWrite.this, PhotoSelectActivity.class);
                intent.putExtra("year", currentYear);
                startActivityForResult(intent, Constants.ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:
                selectorView = new YearSelectorView(YearWrite.this);
                selectorView.setListener(new YearSelectorView.OnYearSelectorDismissedListener() {
                    @Override
                    public void onDismissed(int year, int month) {
                        // get selectYearFrom AlertDialog
                        Log.d("YearWrite", year + "");
                        currentYear = year;
                    }
                });
                selectorView.show();
                break;
            case R.id.textStyle_choose:
                textStyleDialog = new TextStyleDialog(this);
                textStyleDialog.setOnDismissedListener(new TextStyleDialog.OnTextStyleDialogDismissedListener() {
                    @Override
                    public void OnDismissed(String textStyle, boolean isBold, boolean isItalic, boolean isUnderLine, int r) {
                        currentTextStyle.setBold(isBold);
                        currentTextStyle.setColor(r);
                        currentTextStyle.setItalic(isItalic);
                        currentTextStyle.setUnderLine(isUnderLine);
                        currentTextStyle.setTextStyle(textStyle);
                    }
                });
                textStyleDialog.show();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.OPEN_ALBUM:
                String path = AlbumHelper.utilHandler(this, data);
                break;
            case Constants.ON_PHOTO_SELECTED:
                if(data != null) {
                   /* String img_path = data.getStringExtra("img_path");
                    int currentPosition = linearLayout.findFirstCompletelyVisibleItemPosition();
                    if(currentPosition!=-1) {
                        generateImg(img_path,currentPosition);

                    }*/
                    String img_path = data.getStringExtra("img_path");
                    generateImg(img_path, isViewVisible(scrollLinearLayout.getChildAt(0)));

                }
        }
    }


    private void generateText(int recTop) {
        WriteableEditText editText = new WriteableEditText(this);
        editText.setEditTextListener(new WriteableEditText.OnEditTextListener() {
            @Override
            public void onClicked(double x, double y) {

            }

            @Override
            public void onMove(View v, int rawx, int rawy, int x, int y) {
                Rect scrollBounds = new Rect();
                scrollView.getDrawingRect(scrollBounds);
                v.setX(rawx - x);
                v.setY(rawy - y + scrollBounds.top);
                Log.d("YearWrite", v.getY() + "" + v.getX() + "");
            }
        });
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = recTop;
        editText.setLayoutParams(params);

        editText.setPadding(30, 5, 5, 30);
        //set editText Style
        EditTestStyleHelper.setTestStyle(this, editText.getEditText(), currentTextStyle);
        RelativeLayout layout = findViewById(R.id.scrollview_inside);
        layout.addView(editText);
    }

    private void generateImg(String url, int recTop) {
        DrugImageView imageView = new DrugImageView(this);
        imageView.setListener(new DrugImageView.OnImageDragListener() {
            @Override
            public void onDragDown() {
                isScrollable(true);
            }

            @Override
            public void onDragUp() {
                isScrollable(false);
            }

            @Override
            public void onClicked(double x,double y) {

            }

            @Override
            public void onMove(View v,int rawx,int rawy,int x,int y) {
                Rect scrollBounds = new Rect();
                scrollView.getDrawingRect(scrollBounds);
                v.setX(rawx-x);
                v.setY(rawy-y+scrollBounds.top);
            }
        });
        Glide.with(this).load(url).into(imageView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400,400);
        params.topMargin = recTop;
        Toast.makeText(this, "" + recTop, Toast.LENGTH_SHORT).show();
        imageView.setLayoutParams(params);

        imageView.setPadding(30,5,5,30);

        RelativeLayout layout = findViewById(R.id.scrollview_inside);
        layout.addView(imageView);

    }

    private void isScrollable(boolean bool) {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return bool;
            }
        });
    }

    private int isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);

        float top = view.getTop();
        float height =view.getHeight();
        int i = (int) (scrollBounds.top / height);

        //Log.d("YearWrite","top" + top  +"bottom"+bottom);
        Log.d("YearWrite123","view height " +height+"  scroll top"+scrollBounds.top);
        Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
       /* if (scrollBounds.top <= top && scrollBounds.bottom >= bottom) {
            return i;
        } else {
            return false;
        }*/
       return scrollBounds.top;
    }

}
