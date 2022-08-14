package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ButtonActivity extends AppCompatActivity {


    private Button but_dianying;
    private Button but_dianshiju;
    private Button but_gequ;
    private Button but_youxi;
    private Button but_chengyu;
    private Button but_wupin;
    private Button but_zongyi;
    private Button but_zonghe;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fullscreen(true);
        setContentView(R.layout.activity_button);
        but_dianying=findViewById(R.id.but_dianying);
        but_dianshiju=findViewById(R.id.but_dianshiju);
        but_gequ=findViewById(R.id.but_gequ);
        but_youxi=findViewById(R.id.but_youxi);
        but_chengyu=findViewById(R.id.but_chengyu);
        but_wupin=findViewById(R.id.but_wupin);
        but_zongyi=findViewById(R.id.but_zongyi);
        but_zonghe=findViewById(R.id.but_zonghe);

        //text=findViewById(R.id.text_title_1);


        but_dianying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = but_dianying.getText().toString();
                Intent intent = new Intent(ButtonActivity.this, ListActivity.class);
                intent.putExtra("but_str", str);
                ButtonActivity.this.startActivity(intent);
            }
        });
        but_dianshiju.setOnClickListener(view -> {
            String str=but_dianshiju.getText().toString();
            Intent intent=new Intent(ButtonActivity.this,ListActivity.class);
            intent.putExtra("but_str",str);
            startActivity(intent);
        });
        but_gequ.setOnClickListener(view -> {
            String str=but_gequ.getText().toString();
            Intent intent=new Intent(ButtonActivity.this,ListActivity.class);
            intent.putExtra("but_str",str);
            startActivity(intent);
        });
        /*
        * 其他暂无
        * */
        but_zonghe.setOnClickListener(view -> {
            String str=but_zonghe.getText().toString();
            Intent intent=new Intent(ButtonActivity.this,ListActivity.class);
            intent.putExtra("but_str",str);
            startActivity(intent);
        });


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