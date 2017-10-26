package com.wkp.expandview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.wkp.expandview_lib.view.ExpandView;

public class MainActivity extends AppCompatActivity {

    private static final String[] items = {"雨水滴在我的外套","已找到","每分每秒","来啊，互相伤害啊","等你到天涯海角","遇见了你才知道你对我多重要",
            "123","456","789","abc","def","收起"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ExpandView expandView = (ExpandView) findViewById(R.id.ev);
        //设置数据
        expandView.setTextItems(items);
        //条目点击监听
        expandView.setOnItemClickListener(new ExpandView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewGroup parent, int position) {
                if (position == items.length - 1) {
                    //收起隐藏条目
                    expandView.packUpItems();
                }
            }
        });
    }
}
