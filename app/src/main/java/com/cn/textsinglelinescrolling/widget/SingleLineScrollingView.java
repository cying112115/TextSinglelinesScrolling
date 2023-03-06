package com.cn.textsinglelinescrolling.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.cn.textsinglelinescrolling.R;
import com.cn.textsinglelinescrolling.model.NewsModel;
import com.cn.textsinglelinescrolling.utils.RxTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenning on 2023-03-06
 */
public class SingleLineScrollingView extends LinearLayout {
    /**
     *
     */
    private int milliSeconds = 8;
    private RxTimer rxTimer1 = new RxTimer();
    private RxTimer rxTimer2 = new RxTimer();
    private RxTimer rxTimer3 = new RxTimer();

    private RxTimer rxTimerA = new RxTimer();
    private RxTimer rxTimerB = new RxTimer();

    private int _x1;
    private int _x2;
    private int _x3;
    private int mScrollX1;
    private int mScrollX2;
    private int mScrollX3;

    private boolean scrollEnd1 = false;
    private boolean scrollEnd2 = false;
    private boolean scrollEnd3 = false;

    private HorizontalScrollView scrollView1;
    private HorizontalScrollView scrollView2;
    private HorizontalScrollView scrollView3;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;

    private NewsModel newsModel1;
    private NewsModel newsModel2;
    private NewsModel newsModel3;

    private List<NewsModel> datas = new ArrayList<>();
    private View view;

    /**
     * 由于第一条内容固定，第二条内容开始的下标为1
     */
    private int index = 1;

    private AnimatorSet animatorSet;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public SingleLineScrollingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_singleline_scrolling_view, null);
        addView(view, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        scrollView1 = view.findViewById(R.id.scroll_view1);
        scrollView1.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            mScrollX1 = scrollX;
        });
        scrollView1.setOnTouchListener((v, motionEvent) -> {
            return true; // 禁止手动触摸滑动
        });
        scrollView2 = view.findViewById(R.id.scroll_view2);
        scrollView2.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            mScrollX2 = scrollX;
        });
        scrollView2.setOnTouchListener((v, motionEvent) -> {
            return true; // 禁止手动触摸滑动
        });
        scrollView3 = view.findViewById(R.id.scroll_view3);
        scrollView3.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            mScrollX3 = scrollX;
        });
        scrollView3.setOnTouchListener((v, motionEvent) -> {
            return true; // 禁止手动触摸滑动
        });
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);

        initListener(context);
        initAnimators();
    }

    private void initListener(Context context) {
        tv1.setOnClickListener(v -> {
            openDetailPage(newsModel1, context);
        });
        tv2.setOnClickListener(v -> {
            openDetailPage(newsModel2, context);
        });
        tv3.setOnClickListener(v -> {
            openDetailPage(newsModel3, context);
        });
    }

    private void openDetailPage(NewsModel bean, Context mContext) {
        if (bean == null) return;
        Toast.makeText(mContext, bean.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void repeat() {
        if (scrollEnd1 && scrollEnd2 && scrollEnd3) {
            scrollEnd1 = false;
            scrollEnd2 = false;
            scrollEnd3 = false;
            rxTimerA.timer(5000, number -> {
                resetTextValue();
                resetValueAndTimers();
            });
        }
    }

    public void setDataValue(List<NewsModel> list) {
        datas.clear();
        datas.addAll(list);
        switch (datas.size()) {
            case 0:
                ll1.setVisibility(GONE);
                ll2.setVisibility(GONE);
                ll3.setVisibility(GONE);
                break;
            case 1:
                ll1.setVisibility(VISIBLE);
                ll2.setVisibility(GONE);
                ll3.setVisibility(GONE);
                break;
            case 2:
                ll1.setVisibility(VISIBLE);
                ll2.setVisibility(VISIBLE);
                ll3.setVisibility(GONE);
                break;
            default:
                ll1.setVisibility(VISIBLE);
                ll2.setVisibility(VISIBLE);
                ll3.setVisibility(VISIBLE);
                break;
        }

        index = 1;
        scrollEnd1 = false;
        scrollEnd2 = false;
        scrollEnd3 = false;

        resetTextValue();
        rxTimerB.timer(3000, number -> {
            setTimer1();
            setTimer2();
            setTimer3();
        });
    }

    private void resetTextValue() {
        try {

            scrollView1.scrollTo(0, 0);
            scrollView2.scrollTo(0, 0);
            scrollView3.scrollTo(0, 0);

            if (datas.size() > 0) {
                newsModel1 = datas.get(0);
                tv1.setText(newsModel1.getTitle());
            }
            if (datas.size() > 1) {
                newsModel2 = datas.get(index);
                tv2.setText(newsModel2.getTitle());
                index = index + 1;
                if (index >= datas.size()) {
                    index = 1;
                }
            }
            if (datas.size() > 2) {
                newsModel3 = datas.get(index);
                tv3.setText(newsModel3.getTitle());
                index = index + 1;
                if (index >= datas.size()) {
                    index = 1;
                }
            }

            if (animatorSet != null) {
                animatorSet.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAnimators() {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animatorOut1 = ObjectAnimator.ofFloat(tv1, "alpha", 1f, 0.5f);
        ObjectAnimator animatorOut2 = ObjectAnimator.ofFloat(tv2, "alpha", 1f, 0.5f);
        ObjectAnimator animatorOut3 = ObjectAnimator.ofFloat(tv3, "alpha", 1f, 0.5f);
        ObjectAnimator animatorIn1 = ObjectAnimator.ofFloat(tv1, "alpha", 0.5f, 1f);
        ObjectAnimator animatorIn2 = ObjectAnimator.ofFloat(tv2, "alpha", 0.5f, 1f);
        ObjectAnimator animatorIn3 = ObjectAnimator.ofFloat(tv3, "alpha", 0.5f, 1f);
        animators.add(animatorOut1);
        animators.add(animatorOut2);
        animators.add(animatorOut3);
        animators.add(animatorIn1);
        animators.add(animatorIn2);
        animators.add(animatorIn3);

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(200);
        animatorSet.playTogether(animators);
    }

    private void resetValueAndTimers() {
        _x1 = 0;
        _x2 = 0;
        _x3 = 0;
        mScrollX1 = 0;
        mScrollX2 = 0;
        mScrollX3 = 0;
        rxTimerB.timer(1500, number -> {
            setTimer1();
            setTimer2();
            setTimer3();
        });

    }

    private void setTimer1() {
        _x1 = 0;
        mScrollX1 = 0;
        rxTimer1.interval(milliSeconds, aLong -> {
            _x1 += 1;
            scrollView1.scrollTo(_x1, 0);

            if (_x1 > mScrollX1) {
                rxTimer1.cancel();
                scrollEnd1 = true;
                repeat();
            }

        });
    }

    private void setTimer2() {
        _x2 = 0;
        mScrollX2 = 0;
        rxTimer2.interval(milliSeconds, aLong -> {
            _x2 += 1;
            scrollView2.scrollTo(_x2, 0);

            if (_x2 > mScrollX2) {
                rxTimer2.cancel();
                scrollEnd2 = true;
                repeat();
            }

        });
    }

    private void setTimer3() {
        _x3 = 0;
        mScrollX3 = 0;
        rxTimer3.interval(milliSeconds, aLong -> {
            _x3 += 1;
            scrollView3.scrollTo(_x3, 0);

            if (_x3 > mScrollX3) {
                rxTimer3.cancel();
                scrollEnd3 = true;
                repeat();
            }

        });
    }

}
