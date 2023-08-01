package com.example.smartcity.activity.smartsupermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartcity.Help.smartsupermarket.ImageUtil;
import com.example.smartcity.Help.smartsupermarket.MyDb;
import com.example.smartcity.R;

public class SmartSupermarketActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGoodsInsert, btnGoodsDisplay,btnVideo,btnQuery;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_supermarket);
        getSupportActionBar().setTitle("智能商超");

        IninView();

    }
    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//        String image64 = spfRecord.getString("image_64", "");
//        img.setImageBitmap(ImageUtil.base64ToImage(image64));
    }

    private void IninView() {
        btnGoodsInsert = findViewById(R.id.btn_goods_insert);
        btnGoodsInsert.setOnClickListener(this);
        btnGoodsDisplay = findViewById(R.id.btn_goods_display);
        btnGoodsDisplay.setOnClickListener(this);
        //btnVideo = findViewById(R.id.btn_Video);
        //btnVideo.setOnClickListener(this);
        btnQuery = findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = null;
        switch (v.getId()) {
            case R.id.btn_goods_insert:
                intent = new Intent(this, SmartInsertActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_goods_display:
                intent = new Intent(this, SmartDisplayActivity.class);
                startActivity(intent);
                break;
//            case R.id.btn_Video:
//                intent = new Intent(this,SmartVideoActivity.class);
//                startActivity(intent);
//                Toast.makeText(this, "摄像头未开发!请联系开发者!", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.btn_query:
                intent = new Intent(this,SmartQueryActivity.class);
                startActivity(intent);
                break;

        }
    }
}








