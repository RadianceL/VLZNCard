package com.example.eddie.card.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.eddie.card.R;
import com.example.eddie.card.util.Loader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardView card_item_1,card_item_2,card_item_3,card_item_4;
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.content);

        card_item_1 = (CardView) findViewById(R.id.card_item_1);
        card_item_2 = (CardView) findViewById(R.id.card_item_2);
        card_item_3 = (CardView) findViewById(R.id.card_item_3);
        card_item_4 = (CardView) findViewById(R.id.card_item_4);

        card_item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), first.class));
            }
        });

        card_item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), sec.class));
            }
        });

        card_item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        card_item_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDialog();
            }
        });

        initBanner();
    }

    private void mShowDialog() {

        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("沈阳维力智能设备有限公司");
        build.setMessage("联系人：李为民\n电话：13386836511");

        build.show();


    }

    private void initBanner(){
        List list_path = new ArrayList<>();
        List list_title = new ArrayList<>();
        list_path.add(R.drawable.banner_photo_1);
        list_path.add(R.drawable.banner_photo_2);
        list_path.add(R.drawable.banner_photo_3);
        list_path.add(R.drawable.banner_photo_4);
        list_path.add(R.drawable.banner_photo_5);

        list_title.add("工程实例：沈水湾公园");
        list_title.add("工程实例：香格蔚蓝");
        list_title.add("工程实例：辽宁尊荣4S店");
        list_title.add("工程实例：枫和美苑");
        list_title.add("工程实例：沈阳航空管理局");

        banner = (Banner) findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setImageLoader(new Loader());
        banner.setImages(list_path);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setBannerTitles(list_title);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }
}
