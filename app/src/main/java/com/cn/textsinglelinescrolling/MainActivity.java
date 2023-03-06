package com.cn.textsinglelinescrolling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cn.textsinglelinescrolling.model.NewsModel;
import com.cn.textsinglelinescrolling.widget.SingleLineScrollingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SingleLineScrollingView lineScrollingView;

    private List<NewsModel> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineScrollingView = findViewById(R.id.single_line_scrolling_view);

        datas.add(new NewsModel("两会新华时评｜增速5%：释放推动经济整体好转的积极信号"));
        datas.add(new NewsModel("第一观察丨总书记在江苏代表团深入阐释“首要任务”"));
        datas.add(new NewsModel("微镜头·习近平总书记两会“下团组”"));
        datas.add(new NewsModel("五集政论片《中国的民主》第一集《选想选的人》"));
        datas.add(new NewsModel("学习进行时丨习近平总书记和江苏的故事"));

        lineScrollingView.setDataValue(datas);
    }
}