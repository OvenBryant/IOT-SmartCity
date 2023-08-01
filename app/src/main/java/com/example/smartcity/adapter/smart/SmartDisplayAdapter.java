package com.example.smartcity.adapter.smart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcity.Help.smartsupermarket.ImageUtil;
import com.example.smartcity.Help.smartsupermarket.ItemInfo;
import com.example.smartcity.R;

import java.util.List;

import static com.example.smartcity.R.id.tv_smart_goods;

/**
 * @author Amy
 * @date -
 **/
public class SmartDisplayAdapter extends BaseAdapter {

    private Context context;
    private List<ItemInfo> list;
    private LayoutInflater inflater;

    public SmartDisplayAdapter(Context _context, List<ItemInfo> _item) {
        this.context = _context;
        this.list = _item;
        inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            // 创建视图控件
            view = inflater.inflate(R.layout.smart_item_display_layout, null);
            ImageView pic = view.findViewById(R.id.img_smart_pic);
            TextView id = view.findViewById(R.id.tv_smart_id);
            TextView goods = view.findViewById(R.id.tv_smart_goods);
            TextView price = view.findViewById(R.id.tv_smart_price);
            TextView start_date = view.findViewById(R.id.tv_smart_start_date);
            TextView stop_date = view.findViewById(R.id.tv_smart_stop_date);

            viewHolder.pic = pic;
            viewHolder.tvId = id;
            viewHolder.tvGoods = goods;
            viewHolder.tvPrice = price;
            viewHolder.tvStartDate = start_date;
            viewHolder.tvStopDate = stop_date;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // 为控件填充数据
        ItemInfo itemBean = list.get(position);
        viewHolder.pic.setImageBitmap(ImageUtil.base64ToImage(itemBean.getPic()));
        viewHolder.tvId.setText(itemBean.getId());
        viewHolder.tvGoods.setText(itemBean.getGoods());
        viewHolder.tvPrice.setText(itemBean.getPrice());
        viewHolder.tvStartDate.setText(itemBean.getStartDate());
        viewHolder.tvStopDate.setText(itemBean.getStopDate());
        return view;
    }
    //        ItemInfo itemBean = list.get(position);
//        pic.setImageBitmap(ImageUtil.base64ToImage(itemBean.getPic()));
//        id.setText(itemBean.getId());
//        goods.setText(itemBean.getGoods());
//        price.setText(itemBean.getPrice());
//        start_date.setText(itemBean.getStartDate());
//        stop_date.setText(itemBean.getStopDate());
//        Log.i("当前主键是: ", itemBean.get_id());
//        return view;
    class ViewHolder {
        TextView tvId, tvGoods, tvPrice, tvStartDate, tvStopDate;
        ImageView pic;
    }
}


