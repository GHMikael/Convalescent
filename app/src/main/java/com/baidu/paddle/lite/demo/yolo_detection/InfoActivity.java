package com.baidu.paddle.lite.demo.yolo_detection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.paddle.lite.demo.bean.Data;
import com.baidu.paddle.lite.demo.bean.PatientCache;
import com.baidu.paddle.lite.demo.bean.User;
import com.baidu.paddle.lite.demo.component.MyCallBack;
import com.baidu.paddle.lite.demo.component.NetClient;
import com.baidu.paddle.lite.demo.component.ToastShow;

import java.util.List;


public class InfoActivity extends Activity implements View.OnClickListener{

    ToastShow myToast;

    private static List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button button = findViewById(R.id.getData);
        button.setOnClickListener(this);
        Button button1 = findViewById(R.id.view);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case R.id.getData:
                getData();
                break;
            case R.id.view:
                init();
                break;
        }
    }
    public void init(){

        System.out.println(userList.size());
        TextView textView;
        textView = findViewById(R.id.textView8);
        textView.setText(PatientCache.getActionId()+"");

        TextView textView1;
        textView1 = findViewById(R.id.textView10);
        textView1.setText(PatientCache.getInfo()+"");

        TextView textView2;
        textView2 = findViewById(R.id.textView19);
        textView2.setText(User.getInstance().getAccount());

    }

    public void getData(){
        String basePath ="http://43.143.108.99:5000/getData";
        System.out.println(basePath);

        NetClient.getNetClient().callNet(basePath , new MyCallBack() {
            @Override
            public void onFailure(int code) {
                System.out.println("服务器连接失败");
                System.out.println(code);
                // 服务器连接失败
                // 此时是网络问题，服务器宕机，本地网络终端
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myToast.toastShow("ServerError");
                    }
                });
            }
            //此方法为成功接收到api返回值
            //String json就是接收到的值

            @Override
            public void onResponse(String json) {

                String result = json;
                //根据接收到的值进行校验
                // 登录成功
                if(result!=null){
                    System.out.println(result);

                    userList = JSONObject.parseArray(result, User.class);
                    System.out.println(userList.size());

                    for(int i = 0;i < userList.size();i++){
                        if(userList.get(i).getAccount().equals(User.getInstance().getAccount())){
                            PatientCache.setActionId(userList.get(i).getActionId());
                            PatientCache.setInfo(userList.get(i).getInfo());
                        }
                    }
                    System.out.println(PatientCache.getActionId());
                    System.out.println(PatientCache.getInfo());

                    myToast =new ToastShow(InfoActivity.this);
                    myToast.toastShow("刷新数据成功！");

                }else if(result.equals("false")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取失败
                            myToast.toastShow("获取失败!");
                        }
                    });
                }
            }
        });
    }

}