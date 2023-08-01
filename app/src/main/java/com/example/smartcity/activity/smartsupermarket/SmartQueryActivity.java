package com.example.smartcity.activity.smartsupermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartcity.Help.smartsupermarket.ItemInfo;
import com.example.smartcity.Help.smartsupermarket.MyDb;
import com.example.smartcity.R;
import com.example.smartcity.adapter.smart.SmartQueryAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SmartQueryActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText etQueryId, etQueryStartDate, etQueryStopDate;
    private Button btnQuery, btnClearStartdate, btnClearStopdate;
    ListView lv;
    private List<Map<String, Object>> list = new ArrayList<>();
    MyDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_query_activity);

        InitView();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitView() {
        db = new MyDb(this);
        etQueryId = findViewById(R.id.et_query_id);
        etQueryStartDate = findViewById(R.id.et_query_startdate);
        etQueryStopDate = findViewById(R.id.et_query_stopdate);
        btnClearStartdate = findViewById(R.id.btn_clear_startdate);
        btnClearStopdate = findViewById(R.id.btn_clear_stopdate);
        btnClearStartdate.setOnTouchListener(this);
        btnClearStopdate.setOnTouchListener(this);
        etQueryStartDate.setOnTouchListener(this);
        etQueryStopDate.setOnTouchListener(this);

        btnQuery = findViewById(R.id.btn_query_);
        lv = findViewById(R.id.lv_query_);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = etQueryId.getText().toString().trim();
                startDate = etQueryStartDate.getText().toString().trim();
                stopDate = etQueryStopDate.getText().toString().trim();

                /**
                 * 点击查询按钮
                 */
                QueryEvent();

            }
        });
    }

    String id, startDate, stopDate;

    private void QueryEvent(){
        /**
         * 限制一些条件
         */
        if (!TextUtils.isEmpty(startDate) && TextUtils.isEmpty(stopDate)) {
            Toast.makeText(this, "请输入结束日期!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(stopDate)) {
            Toast.makeText(this, "请输入生产日期!", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * 如果都为空,默认查询所有
         */
        if (TextUtils.isEmpty(id) && TextUtils.isEmpty(startDate) && TextUtils.isEmpty(stopDate)) {
            List<ItemInfo> itemInfos = db.QueryAll();
            SmartQueryAdapter my = new SmartQueryAdapter(SmartQueryActivity.this, itemInfos);
            lv.setAdapter(my);
        }
        /**
         * 如果只有订单号
         */
        if (!TextUtils.isEmpty(id) && TextUtils.isEmpty(startDate) && TextUtils.isEmpty(stopDate)) {
            List<ItemInfo> itemInfos = db.QueryByID(id);
            SmartQueryAdapter my = new SmartQueryAdapter(SmartQueryActivity.this, itemInfos);
            lv.setAdapter(my);
        }
        /**
         * 生产日期 结束日期
         */
        if (TextUtils.isEmpty(id) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(stopDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date1 = sdf.parse(startDate); // 将选择好的生产日期转化成时间戳
                long startTime = date1.getTime();

                Date date2 = sdf.parse(stopDate);
                long nowTime = date2.getTime();

                List<ItemInfo> itemInfos = db.QueryStartDate_StopDate(startTime, nowTime);
                SmartQueryAdapter my = new SmartQueryAdapter(SmartQueryActivity.this, itemInfos);
                lv.setAdapter(my);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        /**
         * 订单号 生产日期 结束日期
         */
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(stopDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date1 = sdf.parse(startDate); // 将选择好的生产日期转化成时间戳
                long startTime = date1.getTime();

                Date date2 = sdf.parse(stopDate);
                long nowTime = date2.getTime();

                List<ItemInfo> itemInfos = db.QueryStartDate_StopDate(id, startTime, nowTime);
                SmartQueryAdapter my = new SmartQueryAdapter(SmartQueryActivity.this, itemInfos);
                lv.setAdapter(my);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "查询成功!", Toast.LENGTH_SHORT).show();
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
                case R.id.et_query_startdate:
                    DatePickerDialog dpd = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartQueryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    int Newmonth = month + 1;
                                    etQueryStartDate.setText(year + "-" + Newmonth + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
                                }
                            }, _hour, _minute, false);
                            time.show();
                        }
                    }, _year, _month, _day);
                    dpd.show();
                    break;
                case R.id.et_query_stopdate:
                    DatePickerDialog dpd1 = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            TimePickerDialog time = new TimePickerDialog(SmartQueryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    int Newmonth = month + 1;
                                    etQueryStopDate.setText(year + "-" + Newmonth + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
                                }
                            }, _hour, _minute, false);
                            time.show();
                        }
                    }, _year, _month, _day);
                    dpd1.show();
                    break;
                case R.id.btn_clear_startdate:
                    etQueryStartDate.setText("");
                    break;
                case R.id.btn_clear_stopdate:
                    etQueryStopDate.setText("");
                    break;
            }
        }

        return true;
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



















