package com.lly.order.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lly.order.R;
import com.lly.order.activity.EActivity;
import com.lly.order.entity.FoodEntity;
import com.lly.order.view.SectionedBaseAdapter;

import java.util.List;


public class TestSectionedAdapter extends SectionedBaseAdapter {

    //数据源
    private List<FoodEntity.category> categories;
    //上下文对象
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static final int Duration = 300;

    private onItemClickLocation onItemClickLocation;

    public TestSectionedAdapter(Context context, List<FoodEntity.category> categories) {
        this.categories = categories;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickLocation(onItemClickLocation onItemClickLocation) {
        this.onItemClickLocation = onItemClickLocation;
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return categories.size();
    }

    @Override
    public int getCountForSection(int section) {
        return categories.get(section).getFoodbeans().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.textItem);
            viewHolder.imgbtn_add = (ImageButton) convertView.findViewById(R.id.btn_add);
            viewHolder.imgbtn_cancle = (ImageButton) convertView.findViewById(R.id.btn_cancle);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(categories.get(section).getFoodbeans().get(position).getDishes());
        if (categories.get(section).getFoodbeans().get(position).getCount() > 0) {
            viewHolder.imgbtn_cancle.setVisibility(View.VISIBLE);
            viewHolder.tv_number.setVisibility(View.VISIBLE);
            viewHolder.tv_number.setText(categories.get(section).getFoodbeans().get(position).getCount() + "");
        } else {
            viewHolder.imgbtn_cancle.setVisibility(View.INVISIBLE);
            viewHolder.tv_number.setVisibility(View.INVISIBLE);
        }
        viewHolder.imgbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.imgbtn_cancle.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
                categories.get(section).getFoodbeans().get(position).setCount(categories.get(section).getFoodbeans().get(position).getCount() + 1);
                int count = categories.get(section).getFoodbeans().get(position).getCount();
                viewHolder.tv_number.setText(count + "");
                int[] location = new int[2];
                viewHolder.imgbtn_add.getLocationOnScreen(location);
                onItemClickLocation.onLocation(location[0], location[1]);
                if (count == 1) {
                    PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", viewHolder.imgbtn_add.getX(), 0f);
                    PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("rotation", 0, 720);
                    ObjectAnimator.ofPropertyValuesHolder(viewHolder.imgbtn_cancle, p1, p2).setDuration(Duration).start();
                    PropertyValuesHolder pp1 = PropertyValuesHolder.ofFloat("translationX", (viewHolder.imgbtn_add.getX() - viewHolder.tv_number.getX()), 0f);
                    PropertyValuesHolder pp2 = PropertyValuesHolder.ofFloat("rotation", 0, 360);
                    ObjectAnimator ObjectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(viewHolder.tv_number, pp1, pp2);
                    ObjectAnimator1.setStartDelay(50);//延迟
                    ObjectAnimator1.setDuration(Duration);
                    ObjectAnimator1.start();
                }
            }
        });

        viewHolder.imgbtn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = categories.get(section).getFoodbeans().get(position).getCount();
                categories.get(section).getFoodbeans().get(position).setCount(count - 1);
                ((EActivity) mContext).setTextNum();
                if (count == 1) {
                    PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX",
                            0f, viewHolder.imgbtn_add.getX());
                    PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("rotation", 0,
                            -720);
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(viewHolder.imgbtn_cancle, p1, p2);
                    objectAnimator1.setDuration(Duration);
                    objectAnimator1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            viewHolder.imgbtn_cancle.setVisibility(View.INVISIBLE);
                        }
                    });
                    objectAnimator1.start();
                    PropertyValuesHolder pp1 = PropertyValuesHolder.ofFloat("translationX",
                            0f, (viewHolder.imgbtn_add.getX() - viewHolder.tv_number.getX()));
                    PropertyValuesHolder pp2 = PropertyValuesHolder.ofFloat("rotation", 0,
                            -360);
                    ObjectAnimator objectAnimator2 =
                            ObjectAnimator.ofPropertyValuesHolder(viewHolder.tv_number, pp1, pp2);
                    objectAnimator2.setDuration(Duration);
                    objectAnimator2.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            viewHolder.tv_number.setVisibility(View.INVISIBLE);
                        }
                    });
                    objectAnimator2.start();
                } else {
                    viewHolder.tv_number.setText(categories.get(section).getFoodbeans().get(position).getCount() + "");
                }
            }
        });
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(categories.get(section).getName());
        return layout;
    }

    class ViewHolder {

        private TextView tv_title;
        private ImageButton imgbtn_add;//添加的按钮
        private ImageButton imgbtn_cancle;//取消的按钮
        private TextView tv_number;//数量
    }

    public interface onItemClickLocation {
        public void onLocation(int x, int y);
    }

}
