package com.baidu.paddle.lite.demo.yolo_detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.paddle.lite.demo.bean.User;


public class ChoiceActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        //康复师登陆
        //获取按钮
        Button button = findViewById(R.id.button3);

        //按钮进行监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(ChoiceActivity.this,KangfuLoginActivity.class);
                startActivity(intent);
            }
        });

        //获取按钮
        Button button2 = findViewById(R.id.button4);

        //按钮进行监听
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(ChoiceActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //获取按钮
        Button button5 = findViewById(R.id.visitor);

        //按钮进行监听
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().setAccount("visitor");
                User.getInstance().setPassword(null);
                User.getInstance().setLogin(true);

                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(ChoiceActivity.this,MainYoukeActivity.class);
                startActivity(intent);
            }
        });

    }
}
