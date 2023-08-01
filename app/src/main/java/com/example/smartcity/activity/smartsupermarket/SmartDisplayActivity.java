package com.example.smartcity.activity.smartsupermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.Help.smartsupermarket.ItemInfo;
import com.example.smartcity.Help.smartsupermarket.MyDb;
import com.example.smartcity.R;
import com.example.smartcity.adapter.smart.SmartDisplayAdapter;

import java.util.List;

public class SmartDisplayActivity extends AppCompatActivity {

    private MyDb db;
    private ListView lv;
    private TextView tvHelpID;
    private LayoutInflater loadSmartItemDisplay;
    List<ItemInfo> info = null;

    @Override
    protected void onResume() {
        super.onResume();
        info=null;
        info = db.QueryAll();
        SmartDisplayAdapter base = new SmartDisplayAdapter(SmartDisplayActivity.this, info);
        lv.setAdapter(base);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_display);

        InitView();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo item = info.get(position);
                Dialog dialog = new Dialog(SmartDisplayActivity.this, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = LayoutInflater.from(SmartDisplayActivity.this).inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = db.deleteFromDbById(item.get_id());
                        if (row > 0) {
                            info=null;
                            info = db.QueryAll();
                            SmartDisplayAdapter base = new SmartDisplayAdapter(SmartDisplayActivity.this, info);
                            lv.setAdapter(base);
                        }
                        dialog.dismiss();
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SmartDisplayActivity.this, SmartItemEditActivity.class);
                        intent.putExtra("item",item);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo item = info.get(position);
                Intent intent = new Intent(SmartDisplayActivity.this,SmartItemDisplayInfoActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化展示数据到ListView
     */
    private void InitView() {
        lv = findViewById(R.id.lv);
        db = new MyDb(this);
        info = db.QueryAll();
        SmartDisplayAdapter base = new SmartDisplayAdapter(this, info);
        lv.setAdapter(base);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Toast.makeText(this, "就不让你返回!", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}









