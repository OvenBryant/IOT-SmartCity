package com.example.smartcity.Help.smartsupermarket;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Amy
 * @date -
 **/
public class MyDb extends SQLiteOpenHelper {

    /**
     * _id 主键
     * pic 图像
     * pic1 pic2
     * id  订单号
     * goods 物品
     * price 单价
     * start_date 生产时间
     * stop_date 结束时间
     */
    private static final String TABLE = "info";
    private static final String TableSql = "create table " + TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,pic text,pic1 text,pic2 text,id text,goods text,price text,start_date text,stop_date text)";

    public MyDb(Context _context) {
        super(_context, "MyDb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 根据主键更新数据库
     */
    public int UpdateData(ItemInfo item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pic", item.getPic());
        values.put("pic1", item.getPic1());
        values.put("pic2", item.getPic2());
        values.put("id", item.getId());
        values.put("goods", item.getGoods());
        values.put("price", item.getPrice());
        values.put("start_date", item.getStartDate());
        values.put("stop_date", item.getStopDate());

        return db.update(TABLE, values, "_id like ?", new String[]{item.get_id()});
    }


    /**
     * 插入数据的方法
     *
     * @param item
     * @return
     */
    public long InsertData(ItemInfo item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pic", item.getPic());
        values.put("pic1", item.getPic1());
        values.put("pic2", item.getPic2());
        values.put("id", item.getId());
        values.put("goods", item.getGoods());
        values.put("price", item.getPrice());
        values.put("start_date", item.getStartDate());
        values.put("stop_date", item.getStopDate());
        /**
         * 插入错误,返回-1
         */
        return db.insert(TABLE, null, values);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<ItemInfo> QueryAll() {
        SQLiteDatabase db = getReadableDatabase();

        List<ItemInfo> list = new ArrayList<>();

        //db.query(TABLE_NAME,new String[]{"name","score"},"name like ?",new String[]{name},null,null,null);
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String pic = cursor.getString(cursor.getColumnIndex("pic"));
                String pic1 = cursor.getString(cursor.getColumnIndex("pic1"));
                String pic2 = cursor.getString(cursor.getColumnIndex("pic2"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String goods = cursor.getString(cursor.getColumnIndex("goods"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                String stop_date = cursor.getString(cursor.getColumnIndex("stop_date"));

//                if(!(TextUtils.isEmpty(pic))&&(TextUtils.isEmpty(pic1))&&(TextUtils.isEmpty(pic2)))
//                {
//                    pics = pic;
//                }
//                if((!TextUtils.isEmpty(pic1))&&(TextUtils.isEmpty(pic2)))
//                {
//                    pics = pic+pic1;
//                }
//                if(!(TextUtils.isEmpty(pic1))&&!(TextUtils.isEmpty(pic2)))
//                {
//                    pics = pic+pic1+pic2;
//                }
//                Log.i("pic",pic);
//                Log.i("pic1",pic1);
//                Log.i("pic2",pic2);
                String pics = pic + pic1 + pic2;
                ItemInfo info = new ItemInfo();
                info.set_id(_id);
                info.setPic(pics);
                info.setId(id);
                info.setGoods(goods);
                info.setPrice(price);
                info.setStartDate(start_date);
                info.setStopDate(stop_date);
                list.add(info);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE, "_id like ?", new String[]{id});
    }

    /**
     * 根据订单号查询数据 模糊查询
     *
     * @param _idNo
     */
    public List<ItemInfo> QueryByID(String _idNo) {
        SQLiteDatabase db = getWritableDatabase();
        List<ItemInfo> list = new ArrayList<>();
        //db.query(TABLE_NAME,new String[]{"name","score"},"name like ?",new String[]{name},null,null,null);
        Cursor cursor = db.query(TABLE, new String[]{"id", "goods", "price", "start_date", "stop_date"}, "id like ?", new String[]{"%" + _idNo + "%"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String goods = cursor.getString(cursor.getColumnIndex("goods"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
                String stopDate = cursor.getString(cursor.getColumnIndex("stop_date"));

                ItemInfo item = new ItemInfo();
                item.setId(id);
                item.setGoods(goods);
                item.setPrice(price);
                item.setStartDate(startDate);
                item.setStopDate(stopDate);

                list.add(item);
            }
            cursor.close();
        }
        return list;
    }


    /**
     * 查询大于生产日期的条目小于结束日期的条目
     *
     * @param _startDate
     * @return
     */
    public List<ItemInfo> QueryStartDate_StopDate(long _startDate, long _stopDate) {
        SQLiteDatabase db = getReadableDatabase();

        List<ItemInfo> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //db.query(TABLE_NAME,new String[]{"name","score"},"name like ?",new String[]{name},null,null,null);
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
//                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String goods = cursor.getString(cursor.getColumnIndex("goods"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                String stop_date = cursor.getString(cursor.getColumnIndex("stop_date"));

                try {
                    Date date = sdf.parse(start_date);
                    long time = date.getTime(); // 获得生产日期的时间戳
                    if (_startDate <= time && time <= _stopDate) {
                        Log.i("-------->","-----------");
                        ItemInfo info = new ItemInfo();
                        info.setId(id);  // 订单号
                        info.setGoods(goods); // 物品
                        info.setPrice(price); // 金额
                        info.setStartDate(start_date); // 生产日期
                        info.setStopDate(stop_date);  // 结束日期
                        list.add(info);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 根据订单号 查询大于生产日期的条目小于结束日期的条目
     *
     * @param _startDate
     * @return
     */
    public List<ItemInfo> QueryStartDate_StopDate(String _id,long _startDate, long _stopDate) {
        SQLiteDatabase db = getReadableDatabase();

        List<ItemInfo> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //db.query(TABLE_NAME,new String[]{"name","score"},"name like ?",new String[]{name},null,null,null);
        Cursor cursor = db.query(TABLE, new String[]{"id", "goods", "price", "start_date", "stop_date"}, "id like ?", new String[]{_id}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String goods = cursor.getString(cursor.getColumnIndex("goods"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String start_date = cursor.getString(cursor.getColumnIndex("start_date"));
                String stop_date = cursor.getString(cursor.getColumnIndex("stop_date"));

                try {
                    Date date = sdf.parse(start_date);
                    long time = date.getTime(); // 获得生产日期的时间戳
                    if (_startDate <= time && time <= _stopDate) {
                        ItemInfo info = new ItemInfo();
                        info.setId(id);  // 订单号
                        info.setGoods(goods); // 物品
                        info.setPrice(price); // 金额
                        info.setStartDate(start_date); // 生产日期
                        info.setStopDate(stop_date);  // 结束日期
                        list.add(info);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return list;
    }
}


