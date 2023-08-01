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
public class SmartQueryAdapter extends BaseAdapter {

    private Context context;
    private List<ItemInfo> list;
    private LayoutInflater inflater;

    public SmartQueryAdapter(Context _context, List<ItemInfo> _item) {
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
            view = inflater.inflate(R.layout.items, null);
            // 创建视图控件
            TextView tvId = view.findViewById(R.id.tvNo);
            TextView tvGoods = view.findViewById(R.id.tvGoods);
            TextView tvPrice = view.findViewById(R.id.tvAmount);
            TextView tvStartDate = view.findViewById(R.id.tvStartDate);
            TextView tvStopDate = view.findViewById(R.id.tvStopDate);

            viewHolder.tvId = tvId;
            viewHolder.tvGoods = tvGoods;
            viewHolder.tvPrice = tvPrice;
            viewHolder.tvStartDate = tvStartDate;
            viewHolder.tvStopDate = tvStopDate;

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // 为控件填充数据
        ItemInfo itemBean = list.get(position);
        viewHolder.tvId.setText(itemBean.getId());
        viewHolder.tvGoods.setText(itemBean.getGoods());
        viewHolder.tvPrice.setText(itemBean.getPrice());
        viewHolder.tvStartDate.setText(itemBean.getStartDate());
        viewHolder.tvStopDate.setText(itemBean.getStopDate());
        return view;
    }

    class ViewHolder {
        TextView tvId, tvGoods, tvPrice, tvStartDate, tvStopDate;
    }
}


