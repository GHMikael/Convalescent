package com.baidu.paddle.lite.demo.yolo_detection;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;

import android.widget.TextView;

import com.baidu.paddle.lite.demo.component.MyDialog;
import com.baidu.paddle.lite.demo.bean.User;
import com.baidu.paddle.lite.demo.dao.DataDao;


public class DoctorMyActivity extends Activity implements View.OnClickListener{

    private ImageView userImg;
    private TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_doctormy);
        initView();

    }




    private void  initView() {

        userImg =findViewById(R.id.userImg1); // 用户头像
        userName = findViewById(R.id.userName1); // 用户名字

        TextView textView;
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this);


        userImg.setOnClickListener(this);
        initUser();

        TextView textView1;
        textView1 = findViewById(R.id.textView2);
        textView1.setOnClickListener(this);



    }

    /**
     * 初始化用户
     */
    public void initUser(){
        //如果已经登录过，则直接获取用户信息
        if (User.getInstance().isLogin()) {
            userName.setText(User.getInstance().getAccount());// 设置用户名字
        }
    }







    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.userImg1:
                showDialog();
                break;
//            case R.id.textView:
//                Intent i= new Intent(DoctorMyActivity.this,DataActivity.class);
//                startActivity(i);
//                break;
//            case R.id.textView2:
//                Intent j = new Intent(DoctorMyActivity.this,InfoActivity.class);
//                startActivity(j);
//                break;
        }
    }


    public void logout(){
        if(User.getInstance().isLogin()){
            User.getInstance().setLogin(false);
        }
        Intent i = new Intent(DoctorMyActivity.this, KangfuLoginActivity.class);
        finish();
        startActivity(i);
    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        MyDialog dialog = new MyDialog(DoctorMyActivity.this, R.style.mdialog,
                new MyDialog.OncloseListener() {
                    @Override
                    public void onClick(boolean confirm) {
                        if (confirm) {
                            // TODO: 切换用户/退出登录
                            logout();
                        } else {
                            // TODO:
                        }
                    }
                });
        dialog.show();
    }

}
