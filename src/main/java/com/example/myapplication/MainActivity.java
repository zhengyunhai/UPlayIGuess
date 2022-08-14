package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
    private Button but1;
    private GifDrawable gifFromResource;
    private GifImageView gifImageView;
    private List weiimgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fullscreen(true);
        setContentView(R.layout.activity_main);
        int rdm_tag= (int) (Math.random()*100)%7;
        //rdm_tag=2;
        weiimgs=new ArrayList();
        weiimgs.add(R.drawable.wel_img01);
        weiimgs.add(R.drawable.wel_img02);
        //weiimgs.add(R.drawable.wel_img03);
        //weiimgs.add(R.drawable.wel_img04);
        //weiimgs.add(R.drawable.wel_img05);
        weiimgs.add(R.drawable.wel_img06);
        weiimgs.add(R.drawable.wel_img07);
        weiimgs.add(R.drawable.wel_img08);
        //weiimgs.add(R.drawable.wel_img09);
        //weiimgs.add(R.drawable.wel_img10);
        weiimgs.add(R.drawable.wel_img11);
        weiimgs.add(R.drawable.ic_launcher);
        //rdm_tag=10;
        try {
            gifFromResource = new GifDrawable(getResources(), (Integer) weiimgs.get(rdm_tag));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gifImageView=findViewById(R.id.wel_img);
        gifImageView.setImageDrawable(gifFromResource);

        but1=findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ButtonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fullscreen(boolean enable) {
        if (enable) { //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }
}