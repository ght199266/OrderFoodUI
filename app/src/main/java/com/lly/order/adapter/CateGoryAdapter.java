package com.lly.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lly.order.R;
import com.lly.order.entity.FoodEntity;

import java.util.List;

/**
 * CateGoryAdapter[v 1.0.0]
 * classes:com.caicai.caicai.elme.adapter.CateGoryAdapter
 *
 * @author lileiyi
 * @date 2015/11/19
 * @time 11:20
 * @description
 */
public class CateGoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<FoodEntity.category> mGoryAdapterList;
    private LayoutInflater mLayoutInflater;

    public CateGoryAdapter(Context mContext, List<FoodEntity.category> mGoryAdapterList) {
        this.mContext = mContext;
        this.mGoryAdapterList = mGoryAdapterList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    private int mIndex;

    @Override
    public int getCount() {
        return mGoryAdapterList.size();
    }

    /**
     * 设置当前的位置
     *
     * @param mIndex
     */
    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return mGoryAdapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.e_item, parent, false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(mGoryAdapterList.get(position).getName());
        if (position == mIndex) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.gray002));
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
    }

}
