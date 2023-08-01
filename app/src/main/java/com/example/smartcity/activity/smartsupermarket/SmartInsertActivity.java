package com.example.smartcity.activity.smartsupermarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartcity.Help.smartsupermarket.ImageUtil;
import com.example.smartcity.Help.smartsupermarket.ItemInfo;
import com.example.smartcity.Help.smartsupermarket.MyDb;
import com.example.smartcity.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Calendar;

public class SmartInsertActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "tag";
    private MyDb db;
    private EditText etId, etGoods, etPrice, etStartDate, etStopDate;
    private Button BtnInsert, BtnCamera, BtnChoosePhoto;
    private ImageView ivAvatar;

    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;

    private Uri imageUri;
    private String imageBase64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_insert);

        InitView();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitView() {
        etId = findViewById(R.id.et_id);
        etGoods = findViewById(R.id.et_goods);
        etPrice = findViewById(R.id.et_price);
        etStartDate = findViewById(R.id.et_start_date);
        etStopDate = findViewById(R.id.et_stop_date);
        BtnInsert = findViewById(R.id.btn_insert);
        BtnCamera = findViewById(R.id.btn_camera);
        BtnChoosePhoto = findViewById(R.id.btn_choose_photo);
        ivAvatar = findViewById(R.id.img_pic);

        etStartDate.setOnTouchListener(this); // 触摸就会触发
        etStopDate.setOnTouchListener(this);
        BtnInsert.setOnTouchListener(this);
        BtnCamera.setOnTouchListener(this);
        BtnChoosePhoto.setOnTouchListener(this);

        db = new MyDb(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Calendar calendar = Calendar.getInstance();
        int _year = calendar.get(Calendar.YEAR);
        int _month = calendar.get(Calendar.MONTH);
        int _day = calendar.get(Calendar.DAY_OF_MONTH);
        int _hour = calendar.get(Calendar.HOUR);
        int _minute = calendar.get(Calendar.MINUTE);

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.et_start_date:
                    DatePickerDialog dpd = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartInsertActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    int Newmonth = month + 1;
                                    etStartDate.setText(year + "-" + Newmonth + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
                                }
                            }, _hour, _minute, false);
                            time.show();
                        }
                    }, _year, _month, _day);
                    dpd.show();
                    break;

                case R.id.et_stop_date:
                    DatePickerDialog dpd2 = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartInsertActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    int Newmonth = month + 1;
                                    etStopDate.setText(year + "-" + Newmonth + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
                                }
                            }, _hour, _minute, false);
                            time.show();
                        }
                    }, _year, _month, _day);
                    dpd2.show();
                    break;

                case R.id.btn_insert:
                    if (Validate()) {
                        String id = etId.getText().toString().trim();
                        String goods = etGoods.getText().toString().trim();
                        String price = etPrice.getText().toString().trim();
                        String startDate = etStartDate.getText().toString().trim();
                        String stopDate = etStopDate.getText().toString().trim();
                        String data = imageBase64;
                        if (data.length() <= 2097000) {
                            ItemInfo info = new ItemInfo();
                            info.setPic(data);
                            info.setId(id);
                            info.setGoods(goods);
                            info.setPrice(price);
                            info.setStartDate(startDate);
                            info.setStopDate(stopDate);
                            // 把数据插入到数据库中
                            long l = db.InsertData(info);
                            if (l != -1) {
                                Toast.makeText(this, "添加数据成功!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "添加数据失败!", Toast.LENGTH_SHORT).show();
                            }
                        }
//                        } else if (data.length() >= 2097000 && data.length() <= 2097000 * 2) {
//                            String str1 = data.substring(0, 2097000);
//                            String str2 = data.substring(2097000, data.length());
//                            Log.i("pic.length插入--->", data.length() + "");
//                            ItemInfo info = new ItemInfo();
//                            info.setPic(str1);
//                            info.setPic1(str2);
//                            info.setPic2("");
//                            info.setId(id);
//                            info.setGoods(goods);
//                            info.setPrice(price);
//                            info.setStartDate(startDate);
//                            info.setStopDate(stopDate);
//                            // 把数据插入到数据库中
//                            long l = db.InsertData(info);
//                            if (l != -1) {
//                                Toast.makeText(this, "添加数据成功! 图片2", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(this, "添加数据失败!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else if (data.length() >= 2097000*2 && data.length() <= 2097000 * 4) {  // 5943306
//                            String str1 = data.substring(0, 2097000);
//                            String str2 = data.substring(2097000, 2097000 * 2);
//                            String str3 = data.substring(2097000 * 2, data.length());
//                            Log.i("pic.length插入--->", data.length() + "");
//                            ItemInfo info = new ItemInfo();
//                            info.setPic(str1);
//                            info.setPic1(str2);
//                            info.setPic2(str3);
//                            info.setId(id);
//                            info.setGoods(goods);
//                            info.setPrice(price);
//                            info.setStartDate(startDate);
//                            info.setStopDate(stopDate);
//                            // 把数据插入到数据库中
//                            long l = db.InsertData(info);
//                            if (l != -1) {
//                                Toast.makeText(this, "添加数据成功! 图片4", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(this, "添加数据失败!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        else {
                            Toast.makeText(this, "图片不好看,亲,换一张图片吧?", Toast.LENGTH_SHORT).show();
                        }
//                        if(data.length()>2097000&&data.length()<=4194000) // 1.9<data<3.8
//                        {
//                            Log.i("pic.length插入--->",data.length()+"");
//                            String str1 = data.substring(0, 2097000);
//                            String str2 = data.substring(2097000,4194000);
//                            ItemInfo info = new ItemInfo();
//                            info.setPic(str1);
//                            info.setPic1(str2);
//                            info.setPic2("");
//                            info.setId(id);
//                            info.setGoods(goods);
//                            info.setPrice(price);
//                            info.setStartDate(startDate);
//                            info.setStopDate(stopDate);
//                            // 把数据插入到数据库中
//                            long l = db.InsertData(info);
//                            if (l != -1) {
//                                Toast.makeText(this, "添加数据成功!图片1.9~3.8", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(this, "添加数据失败!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        if(data.length()>4194000&&data.length()<=6000000) // 3.8<data<5.7
//                        {
//                            Log.i("pic.length插入--->",data.length()+"");
//                            String str1 = data.substring(0, 2097000);
//                            String str2 = data.substring(2097000,4194000);
//                            String str3 = data.substring(4194000);
//                            ItemInfo info = new ItemInfo();
//                            info.setPic(str1);
//                            info.setPic1(str2);
//                            info.setPic2(str3);
//                            info.setId(id);
//                            info.setGoods(goods);
//                            info.setPrice(price);
//                            info.setStartDate(startDate);
//                            info.setStopDate(stopDate);
//                            // 把数据插入到数据库中
//                            long l = db.InsertData(info);
//                            if (l != -1) {
//                                Toast.makeText(this, "添加数据成功!图片1.9~5.7", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(this, "添加数据失败!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        if(data.length()>6000000)
//                        {
//                            Log.i("pic.length插入--->",data.length()+"");
//                            Toast.makeText(this, "图片不好看,亲咋们换一个吧?", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }

//                        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//                        SharedPreferences.Editor edit = spfRecord.edit();
//                        edit.putString("image_64", imageBase64);
//                        edit.apply();
//                        this.finish();

                    }
                    break;

                case R.id.btn_camera:
                    checkPermissionAndCamera();
                    break;
                case R.id.btn_choose_photo:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // 真正的去打开相册
                        openAlbum();
                    } else {
                        // 去申请权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                    break;
            }
        }
        return true;
    }



    /**
     * 验证商品的信息过滤
     * @return
     */
    private boolean Validate() {
        if (TextUtils.isEmpty(etId.getText().toString().trim())) {
            Toast.makeText(this, "订单不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etGoods.getText().toString().trim())) {
            Toast.makeText(this, "物品不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            Toast.makeText(this, "金额不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etStartDate.getText().toString().trim())) {
            Toast.makeText(this, "生产日期不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etStopDate.getText().toString().trim())) {
            Toast.makeText(this, "保质日期不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(imageBase64)) {
            Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查相机权限
     */
    private void checkPermissionAndCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 真正的执行去拍照
            doTake();
        } else {
            // 去申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTake();
            } else {
                Toast.makeText(this, "你没有获得摄像头权限~", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "你没有获得访问相册的权限~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    private void doTake() {
        File imageTemp = new File(getExternalCacheDir(), "imageOut.jpeg");
        if (imageTemp.exists()) {
            imageTemp.delete();
        }
        try {
            imageTemp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 24) {
            // contentProvider
            imageUri = FileProvider.getUriForFile(this, "com.example.smartcity.fileprovider", imageTemp);
        } else {
            imageUri = Uri.fromFile(imageTemp);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE) {
            if (resultCode == RESULT_OK) {
                // 获取拍摄的照片
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivAvatar.setImageBitmap(bitmap);
                    imageBase64 = ImageUtil.imageToBase64(bitmap);
                } catch (FileNotFoundException e) {

                }
            }
        } else if (requestCode == REQUEST_CODE_CHOOSE) {

            if (Build.VERSION.SDK_INT < 19) {
                handleImageBeforeApi19(data);
            } else {
                handleImageOnApi19(data);
            }
        }
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        Log.d(TAG, "displayImage: ------------" + imagePath);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivAvatar.setImageBitmap(bitmap);
            imageBase64 = ImageUtil.imageToBase64(bitmap);
        }
    }

    @TargetApi(19)
    private void handleImageOnApi19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri, documentId);
                    return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void resolveMSFContent(Uri uri, String documentId) {

        File file = new File(getCacheDir(), "temp_file" + getContentResolver().getType(uri).split("/")[1]);

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivAvatar.setImageBitmap(bitmap);
            imageBase64 = ImageUtil.imageToBase64(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}