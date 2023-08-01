package com.example.smartcity.activity.smartsupermarket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.Help.smartsupermarket.ImageUtil;
import com.example.smartcity.Help.smartsupermarket.ItemInfo;
import com.example.smartcity.Help.smartsupermarket.QRCodeUtil;
import com.example.smartcity.R;
import com.example.smartcity.main.MainActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;

public class SmartItemDisplayInfoActivity extends AppCompatActivity {

    private ImageView img;
    private TextView tvId, tvGoods, tvPrice, tvStartDate, tvStopDate;
    Button btnQrcode;
    ItemInfo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_item_display_info);

        InitView();

        Intent intent = getIntent();
        item = (ItemInfo) intent.getSerializableExtra("item");
        if (item != null) {
            img.setImageBitmap(ImageUtil.base64ToImage(item.getPic()));
            tvId.setText(item.getId());
            tvGoods.setText(item.getGoods());
            tvPrice.setText(item.getPrice());
            tvStartDate.setText(item.getStartDate());
            tvStopDate.setText(item.getStopDate());
        }
    }

    private void InitView() {
        img = findViewById(R.id.smart_img);
        tvId = findViewById(R.id.smart_id);
        tvGoods = findViewById(R.id.smart_name);
        tvPrice = findViewById(R.id.smart_price);
        tvStartDate = findViewById(R.id.smart_startdate);
        tvStopDate = findViewById(R.id.smart_stopdate);
        btnQrcode = findViewById(R.id.btn_QRcode);

        btnQrcode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String id = item.getId();  // 当前订单号
                String goods = item.getGoods(); // 当前物品
                String price = item.getPrice(); // 当前价格
                String startDate = item.getStartDate(); // 当前生产日期
                String stopDate = item.getStopDate(); // 当前结束日期
                String info = "当前的订单号是: "+id+" 当前物品的昵称是: "+goods+" 当前价格为: "+
                        price+" 当前的生产日期为: "+startDate+" 当前结束日期是: "+stopDate;

                Dialog dialog = new Dialog(SmartItemDisplayInfoActivity.this,
                        android.R.style.ThemeOverlay_Material_Dialog_Alert);

                View dialogView = LayoutInflater.from(SmartItemDisplayInfoActivity.this)
                        .inflate(R.layout.qrcode_layout, null);

                ImageView imgQRCode = dialogView.findViewById(R.id.img_qrcode);
                DisplayQRcode(ImageUtil.base64ToImage(item.getPic()),info,dialog,dialogView,imgQRCode);
            }
        });
    }

    /**
     * 生成二维码
     */
    private void DisplayQRcode(Bitmap bitmap,String info,Dialog dialog,View dialogView,ImageView imgQRCode) {
        final String filePath = getFileRoot(SmartItemDisplayInfoActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
//                        boolean success = QRCodeUtil.createQRImage(contentET.getText().toString().trim(), 800, 800,
//                                addLogoCB.isChecked() ? BitmapFactory.decodeResource(getResources(), R.drawable.btn_press) : null,
//                                filePath);

                boolean success = QRCodeUtil.createQRImage(info, 800, 800,
                        null,
                        filePath);

                Log.i("------>", success + "");

                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgQRCode.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();

        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }



    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
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













