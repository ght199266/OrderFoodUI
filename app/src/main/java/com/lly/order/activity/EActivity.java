package com.lly.order.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lly.order.R;
import com.lly.order.adapter.CateGoryAdapter;
import com.lly.order.adapter.TestSectionedAdapter;
import com.lly.order.entity.FoodEntity;
import com.lly.order.view.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * EActivity[v 1.0.0]
 * classes:com.caicai.caicai.elme.EActivity
 *
 * @author lileiyi
 * @date 2015/11/19
 * @time 10:55
 * @description
 */
public class EActivity extends AppCompatActivity implements TestSectionedAdapter.onItemClickLocation {

    //左侧类别列表
    private ListView mListView;
    private CateGoryAdapter mCateGoryAdapter;

    //右侧食物列表
    private PinnedHeaderListView pinnedHeaderListView;
    private TestSectionedAdapter testSectionedAdapter;

    private List<FoodEntity.category> categories = new ArrayList<>();

    //保存head的位置
    private HashMap<Integer, Integer> location = new HashMap<>();
    private int mSection;

    //购物车相关参数
    private ImageView imageView2;
    private float imageViewX;
    private float imageViewY;
    private TextView tv_num;


    //    rel_layout
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_layout);
        initView();
        getData();
        setAdapter();
        setOnClicListener();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int[] location = new int[2];
        imageView2.getLocationOnScreen(location);
        imageViewX = location[0];
        imageViewY = location[1];
    }

    /**
     * 统一点击时间
     */
    private void setOnClicListener() {
        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    pinnedHeaderListView.setSelection(0);
                } else {
                    pinnedHeaderListView.setSelection(location.get(position - 1));

                }
                mCateGoryAdapter.setIndex(position);
            }
        });

        pinnedHeaderListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int section = testSectionedAdapter.getSectionForPosition(firstVisibleItem);//获取当前的head
                if (mSection != section) {
                    mCateGoryAdapter.setIndex(section);
                    mSection = section;
                }
            }
        });

    }

    //获取数据
    private void getData() {
        List<FoodEntity.category.foodbean> foodbeanList;
        int num = 0;
        for (int i = 0; i < 8; i++) { //三种类型
            num++;
            FoodEntity.category category = new FoodEntity.category();
            category.setName("类型" + i);
            foodbeanList = new ArrayList<>();
            int random = new Random().nextInt(6) + 3;//随机
            for (int j = 0; j < random; j++) {
                num++;
                FoodEntity.category.foodbean foodbean = new FoodEntity.category.foodbean();
                foodbean.setDishes("鲍鱼" + j);
                foodbeanList.add(foodbean);
            }
            category.setFoodbeans(foodbeanList);
            categories.add(category);
            location.put(i, num);
        }
    }


    /**
     * 初始化View
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        mListView = (ListView) findViewById(R.id.my_listview);
        pinnedHeaderListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        tv_num = (TextView) findViewById(R.id.tv_nm);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);
    }

    /**
     * 填充适配器
     */
    private void setAdapter() {
        //列表数据
        mCateGoryAdapter = new CateGoryAdapter(EActivity.this, categories);
        mListView.setAdapter(mCateGoryAdapter);

        testSectionedAdapter = new TestSectionedAdapter(EActivity.this, categories);
        pinnedHeaderListView.setAdapter(testSectionedAdapter);

        testSectionedAdapter.setOnItemClickLocation(this);
    }

    /**
     * 显示一个Toast
     */
    public void showToast(String str) {
        Toast.makeText(EActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    //得到添加食物按钮的点击坐标
    public void onLocation(final int x, final int y) {
        //生成一个View
        final ImageView img_view = new ImageView(this);
        img_view.setImageResource(R.mipmap.food_button_add);
        relativeLayout.addView(img_view);
        //把View的位置移动到和点击的一个位置
        img_view.setX(x);
        img_view.setY(y - 48);//-48

        // 计算位移
        float endX = imageViewX - x;
        float endY = imageViewY - y;

        //执行位移和透明度动画
        TranslateAnimation translateAnimationx = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationx.setInterpolator(new LinearInterpolator());//匀速
        img_view.setAnimation(translateAnimationx);


        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setStartOffset(50);
        img_view.setAnimation(translateAnimationY);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.1f);
        img_view.setAnimation(alphaAnimation);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationx);
        set.addAnimation(alphaAnimation);
        set.setDuration(500);// 动画的执行时间
        img_view.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setTextNum();
                relativeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(img_view);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    //
    public void setTextNum() {
        int num = 0;
        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < categories.get(i).getFoodbeans().size(); j++) {
                if (categories.get(i).getFoodbeans().get(j).getCount() >= 0) {
                    num += categories.get(i).getFoodbeans().get(j).getCount();
                }
            }
        }
        if (num == 0) {
            tv_num.setVisibility(View.GONE);
        } else {
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText(num + "");
        }

    }

}
