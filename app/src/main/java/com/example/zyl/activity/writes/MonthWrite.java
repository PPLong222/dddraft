package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.zyl.activity.view.MonthSelectorView;
import com.example.zyl.model.MonthDataModel;
import com.example.zyl.recycle.BaseRecyclerAdapter;
import com.example.zyl.recycle.MyRVViewHolder;
import com.example.zyl.recycle.adapter.WeekRecycleviewAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.zyl.activity.writes.WeekWrite.sdds;

public class MonthWrite extends Activity implements View.OnClickListener{
    private ImageView buttonBack;
    private TextView buttonOversee;
    private TextView textType;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView monthRecyclerView;
    private MonthSelectorView selectorView;
    List<MonthDataModel> lists = new ArrayList();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymonth);

        setUi();
    }

    MyAdapter weekRecycleviewAdapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUi() {
        buttonBack = findViewById(R.id.month_head_nav).findViewById(R.id.head_back);
        buttonOversee=findViewById(R.id.month_head_nav).findViewById(R.id.head_text_oversee);
        buttonPullBack=findViewById(R.id.month_head_nav).findViewById(R.id.head_pullback);
        textType=findViewById(R.id.month_head_nav).findViewById(R.id.head_text);
        textType.setText("Month");
        buttonAddPicture = findViewById(R.id.month_bottom_nav).findViewById(R.id.choosePhoto);
        buttonAddText = findViewById(R.id.month_bottom_nav).findViewById(R.id.chooseText);
        monthRecyclerView = findViewById(R.id.month_recyclerview);

        List<MonthDataModel> temp= DataSupport.findAll(MonthDataModel.class);
        lists.addAll(temp);
        weekRecycleviewAdapter = new MyAdapter(MonthWrite.this, lists, R.layout.single_block);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        monthRecyclerView.setAdapter(weekRecycleviewAdapter);
        monthRecyclerView.setLayoutManager(linearLayout);

        //////////////

        buttonBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_back:
                Intent intent=new Intent(MonthWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:
                break;

            case R.id.choosePhoto:
                intent = new Intent(MonthWrite.this, PhotoSelectActivity.class);
                startActivityForResult(intent, Constants.ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:
                // Show monthSelectorView and add callback of it
                selectorView = new MonthSelectorView(this);
                selectorView.setListener(new MonthSelectorView.OnMonthDialogDismissedListener() {
                    @Override
                    public void onDismissed(int year, int month) {
                        Toast.makeText(MonthWrite.this, year+"-"+month, Toast.LENGTH_SHORT).show();
                    }
                });
                selectorView.show();
                break;
            case R.id.chooseText:
                intent = new Intent(MonthWrite.this, TextActivity.class);
                startActivityForResult(intent, sdds);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {



            case AlbumHelper.OPEN_ALBUM:
                String path = AlbumHelper.utilHandler(this, data);
                setNewPicPos(path,"");
                break;
            case Constants.ON_PHOTO_SELECTED:
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
        }
    }
    private void setNewPicPos(String path,String text) {
//        ImageView imageView=new ImageView(this);
//        imageView.setImageURI(Uri.parse(path));
//        imageView.setX(200);
//        imageView.setY(200);
//        weekRecyclerView.addView(imageView);

        MonthDataModel bean = new MonthDataModel();
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
    class MyAdapter extends BaseRecyclerAdapter<MonthDataModel> {


        private int selPosi;

        public void setSelPosi(int selPosi) {
            this.selPosi = selPosi;
        }

        public MyAdapter(Context context, List<MonthDataModel> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final MonthDataModel bean, int position) {
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
