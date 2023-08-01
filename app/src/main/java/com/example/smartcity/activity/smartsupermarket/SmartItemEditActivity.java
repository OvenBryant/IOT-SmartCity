package com.example.smartcity.activity.smartsupermarket;

import androidx.annotation.Nullable;
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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.ims.ImsMmTelManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
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
import java.util.Calendar;

public class SmartItemEditActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    MyDb myDb;
    private ImageView img;
    private EditText etID, etGoods, etPrice, etStartDate, etStopDate;
    private Button btnCamera, btnChoosePhoto, btnCancel, btnUpdate;

    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;

    private Uri imageUri;
    private String imageBase64;
    private String _id; // 主键


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_item_edit);

        InitView();

        Intent intent = getIntent();
        ItemInfo item = (ItemInfo) intent.getSerializableExtra("item");
        if (item != null) {
            _id = item.get_id();
            img.setImageBitmap(ImageUtil.base64ToImage(item.getPic()));
            imageBase64 = item.getPic();
            etID.setText(item.getId());
            etGoods.setText(item.getGoods());
            etPrice.setText(item.getPrice());
            etStartDate.setText(item.getStartDate());
            etStopDate.setText(item.getStopDate());
        }
        myDb = new MyDb(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitView() {
        img = findViewById(R.id.edit_img);

        etID = findViewById(R.id.edit_id);
        etGoods = findViewById(R.id.edit_goods);
        etPrice = findViewById(R.id.edit_price);
        etStartDate = findViewById(R.id.edit_start_date);
        etStopDate = findViewById(R.id.edit_stop_date);

        btnCamera = findViewById(R.id.edit_btn_camera);
        btnChoosePhoto = findViewById(R.id.edit_btn_choose_photo);
        btnCancel = findViewById(R.id.edit_btn_cancel);
        btnUpdate = findViewById(R.id.edit_btn_update);

        btnCamera.setOnClickListener(this);
        btnChoosePhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        etStartDate.setOnTouchListener(this); // 触摸就会触发
        etStopDate.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn_camera:
                checkPermissionAndCamera();
                break;
            case R.id.edit_btn_choose_photo:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // 真正的去打开相册
                    openAlbum();
                } else {
                    // 去申请权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
                break;
            case R.id.edit_btn_cancel:
                finish();
                break;
            case R.id.edit_btn_update:
                if (Validate()) {
                    String id = etID.getText().toString(); // 订单号
                    String price = etPrice.getText().toString(); // 金额
                    String goods = etGoods.getText().toString(); // 物品名称
                    String startDate = etStartDate.getText().toString(); // 生产日期
                    String stopDate = etStopDate.getText().toString(); // 停止日期
                    String data = imageBase64;
                    if (data.length() <= 2097000) {
                        ItemInfo item = new ItemInfo();
                        item.set_id(_id);  // ------------------
                        item.setId(id);
                        item.setPrice(price);
                        item.setGoods(goods);
                        item.setStartDate(startDate);
                        item.setStopDate(stopDate);
                        item.setPic(data);
                        item.setPic1("");
                        item.setPic2("");

                        // 根据名字更新数据
                        long l = myDb.UpdateData(item);
                        if (l > 0) {
                            Toast.makeText(this, "数据已更新!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "数据更新失败!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "图片不好看,亲,换一张图片吧?", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    /**
     * 验证
     *
     * @return
     */
    private boolean Validate() {
        if (TextUtils.isEmpty(etID.getText().toString().trim())) {
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
            Toast.makeText(this, "请选择商品图片", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkPermissionAndCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 真正的执行去拍照
            doTake();
        } else {
            // 去申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
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

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
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
                    img.setImageBitmap(bitmap);
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
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            img.setImageBitmap(bitmap);
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
            img.setImageBitmap(bitmap);
            imageBase64 = ImageUtil.imageToBase64(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                case R.id.edit_start_date:
                    DatePickerDialog dpd = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartItemEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                case R.id.edit_stop_date:
                    DatePickerDialog dpd2 = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartItemEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            }
        }
        return true;
    }
}










