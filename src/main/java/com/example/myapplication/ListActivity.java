package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private TextView text_show;
    private TextView text_counter;
    private TextView text_shortinfo;
    private Button pre;
    private Button next;

    private InputStream ins;

    private List<loadRes> items;
    private int tagInShoworder;
    private loadRes show_text;
    private List showorder;

    private CountDownTimer countDownTimer;

    List itemlist;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        fullscreen(true);
        setContentView(R.layout.activity_list);
        text_show=findViewById(R.id.text_show);
        text_shortinfo=findViewById(R.id.shortinfo);
        try {
            ins = getAssets().open("data.xml");
        } catch (IOException  e) {
            e.printStackTrace();
        }
        Intent intent=getIntent();
        String but_str=intent.getStringExtra("but_str");
        switch (but_str){
            case "电影":items=lodadata(ins,"film");break;
            case "电视剧":items=lodadata(ins,"teleplay");break;
            case "歌曲":items=lodadata(ins,"song");break;
            //case "游戏":items=lodadata(ins,"game");break;
            //case "成语":items=lodadata(ins,"word");break;
            //case "物品":items=lodadata(ins,"thing");break;
            //case "综艺":items=lodadata(ins,"funtv");break;
            case "综合":items=lodaalldata(ins);break;
        }
        showorder=new ArrayList();
        for (int i=0;i<items.size();i++)showorder.add(i,i);
        Collections.shuffle(showorder);//乱序处理

        //按钮功能
        pre=findViewById(R.id.but_pre);
        next=findViewById(R.id.but_next);
        tagInShoworder=0;
        show_text= items.get((Integer) showorder.get(tagInShoworder));
        text_show.setText(show_text.getName());
        text_shortinfo.setText(show_text.getType());
        //点击pre按钮
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tagInShoworder>0){
                    stopTimer();
                    tagInShoworder--;
                    show_text= items.get((Integer) showorder.get(tagInShoworder));
                    text_show.setText(show_text.getName());
                    text_shortinfo.setText(show_text.getType());
                }
            }
        });
        //点击next按钮
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tagInShoworder<items.size()-1) {
                    stopTimer();
                    tagInShoworder++;
                    int tag_tmp=(Integer) showorder.get(tagInShoworder);
                    show_text= items.get(tag_tmp);
                    text_show.setText(show_text.getName());
                    text_shortinfo.setText(show_text.getType());
                    startTimer();
                }
            }
        });
        startTimer();
    }

    //加载xml全部数据
    private List<loadRes> lodaalldata(InputStream inputStream) {
        XmlPullParserFactory factory;
        XmlPullParser parser = null;
        int eventType = 0;
        List<loadRes> res = null;

        try {
            factory=XmlPullParserFactory.newInstance();
            parser=factory.newPullParser();
            parser.setInput(inputStream,"UTF-8");
            eventType=parser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        res=new ArrayList<loadRes>();
                        itemlist=new ArrayList();
                        itemlist.add("film");
                        itemlist.add("teleplay");
                        itemlist.add("song");
                        itemlist.add("game");
                        itemlist.add("word");
                        itemlist.add("thing");
                        itemlist.add("funtv");
                        break;
                    case XmlPullParser.START_TAG:
                        String name_tmp=parser.getName();
                        if(itemlist.contains(name_tmp))
                            res.add(new loadRes(parser.getAttributeValue(0),parser.getAttributeValue(1)));
                        break;
                    case XmlPullParser.END_TAG:break;
                }
                eventType=parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    //结束倒计时
    private void stopTimer() {
        text_counter.setText("");
        if (countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
    }

    //开始倒计时
    private void startTimer() {
        text_counter=findViewById(R.id.text_counter);
        int tim=20;
        countDownTimer=new CountDownTimer(tim*1000,1000) {
            @Override
            public void onTick(long l) {
                int t= (int) (l/1000);
                //if (t<=11)text_counter.setText(String.valueOf(t-1));
                text_counter.setText(String.valueOf(t));
            }

            @Override
            public void onFinish() {
                if(tagInShoworder<items.size()-1){
                    text_counter.setText("");
                    tagInShoworder++;
                    int tag_tmp=(Integer) showorder.get(tagInShoworder);
                    show_text= items.get(tag_tmp);
                    text_show.setText(show_text.getName());
                    text_shortinfo.setText(show_text.getType());
                    this.start();
                }else stopTimer();
            }
        };
        countDownTimer.start();
    }

    //加载xml指定类型数据
    private List<loadRes> lodadata(InputStream inputStream, String itemname) {
        XmlPullParserFactory factory;
        XmlPullParser parser = null;
        int eventType = 0;
        List<loadRes> res = null;
        try {
            factory=XmlPullParserFactory.newInstance();
            parser=factory.newPullParser();
            parser.setInput(inputStream,"UTF-8");
            eventType=parser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        res=new ArrayList();
                        break;
                    case XmlPullParser.START_TAG:
                        String name_tmp=parser.getName();
                        if(itemname.equals(name_tmp))
                            res.add(new loadRes(parser.getAttributeValue(0),parser.getAttributeValue(1)));
                        break;
                    case XmlPullParser.END_TAG:break;
                }
                    eventType=parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    //控制状态栏显示
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