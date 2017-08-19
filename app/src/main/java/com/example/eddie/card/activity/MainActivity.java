package com.example.eddie.card.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eddie.card.R;
import com.example.eddie.card.activity.first;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.content);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDialog();
            }
        });

    }

    private void mShowDialog() {

        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("沈阳维力智能设备有限公司");
        build.setMessage("联系人：李为民\n电话：13386836511");

        build.show();
    }

    public void first(View view) {
        startActivity(new Intent(this, first.class));
    }

    public void sec(View view) {
        startActivity(new Intent(this,sec.class));
    }

    public void thr(View view) {
    }

    public void fou(View view) {
    }
}
