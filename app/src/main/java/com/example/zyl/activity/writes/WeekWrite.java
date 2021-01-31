package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.MainActivity;
import com.example.zyl.R;
import com.example.zyl.StrUtil;
import com.example.zyl.activity.PhotoSelectActivity;
import com.example.zyl.activity.TextActivity;
import com.example.zyl.model.WeekDataModel;
import com.example.zyl.recycle.adapter.BaseRecyclerAdapter;
import com.example.zyl.recycle.holder.MyRVViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class WeekWrite extends Activity implements View.OnClickListener {
    public static final int ON_PHOTO_SELECTED = 3;
    public static final int sdds = 4;

    private ImageView buttonBack;

    private TextView buttonOversee;
    private TextView textType;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView weekRecyclerView;
    List<WeekDataModel> lists = new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myweek);

        setUi();
    }

    MyAdapter weekRecycleviewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUi() {
        buttonBack = findViewById(R.id.week_head_nav).findViewById(R.id.head_back);
        buttonOversee = findViewById(R.id.week_head_nav).findViewById(R.id.head_text_oversee);
        buttonAddPicture = findViewById(R.id.week_bottom_nav).findViewById(R.id.choosePhoto);
        buttonPullBack = findViewById(R.id.week_head_nav).findViewById(R.id.head_pullback);
        buttonAddText = findViewById(R.id.week_bottom_nav).findViewById(R.id.chooseText);
        textType = findViewById(R.id.week_head_nav).findViewById(R.id.head_text);
        textType.setText("Week");
        weekRecyclerView = findViewById(R.id.week_recyclerview);
        ///////////////
        List<WeekDataModel> temp=DataSupport.findAll(WeekDataModel.class);
        lists.addAll(temp);
        weekRecycleviewAdapter = new MyAdapter(WeekWrite.this, lists, R.layout.single_block);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        weekRecyclerView.setAdapter(weekRecycleviewAdapter);
        weekRecyclerView.setLayoutManager(linearLayout);
        //////////////

        buttonBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);
        textType.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.head_back:
                intent = new Intent(WeekWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:
                break;
           /* case R.id.choosePhoto:
                Log.d("WeekWrite","clikced");
                AlbumHelper.openGalary(this);
                break;*/
            case R.id.chooseText:
                intent = new Intent(WeekWrite.this, TextActivity.class);
                startActivityForResult(intent, sdds);
                break;
            case R.id.choosePhoto:
                intent = new Intent(WeekWrite.this, PhotoSelectActivity.class);
                startActivityForResult(intent, ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("WeekWrite", "" + requestCode);

        switch (requestCode) {
            case AlbumHelper.OPEN_ALBUM:
                String path = AlbumHelper.utilHandler(this, data);
                setNewPicPos(path,"");
                break;
            case ON_PHOTO_SELECTED:
//                    if(data != null) {
                String img_path = data.getStringExtra("img_path");
                setNewPicPos(img_path,"");
//                    }
                break;
            case sdds:
//                    if(data != null) {
                String text = data.getStringExtra("text");
                setNewPicPos("",text);
//                    }
                break;

            case Constants.CAMERA_REQUEST_CODE:
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void setNewPicPos(String path,String text) {
//        ImageView imageView=new ImageView(this);
//        imageView.setImageURI(Uri.parse(path));
//        imageView.setX(200);
//        imageView.setY(200);
//        weekRecyclerView.addView(imageView);

        WeekDataModel bean = new WeekDataModel();
        if (!StrUtil.isEmpty(path)) {
            bean.setPic(path);
        }
        if (!StrUtil.isEmpty(text)) {
            bean.setStr(text);
        }
        if ( StrUtil.isEmpty(path)&&StrUtil.isEmpty(text)) {
            return;
        }
        bean.save();
        lists.add(bean);
        weekRecycleviewAdapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    class MyAdapter extends BaseRecyclerAdapter<WeekDataModel> {


        private int selPosi;

        public void setSelPosi(int selPosi) {
            this.selPosi = selPosi;
        }

        public MyAdapter(Context context, List<WeekDataModel> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final WeekDataModel bean, int position) {
            if (null == holder || null == bean)
                return;
            //init view
            ImageView imageView = holder.getView(R.id.imageView);
            TextView date_name = holder.getView(R.id.date_name);
            //set view
            if (!StrUtil.isEmpty(bean.getPic())) {
                imageView.setImageURI(Uri.parse(bean.getPic()));
            }
            if (!StrUtil.isEmpty(bean.getStr())) {
                date_name.setText(bean.getStr());
            }


        }
    }

}
