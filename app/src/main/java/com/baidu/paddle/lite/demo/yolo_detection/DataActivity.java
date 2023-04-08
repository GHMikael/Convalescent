package com.baidu.paddle.lite.demo.yolo_detection;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.paddle.lite.demo.bean.Data;
import com.baidu.paddle.lite.demo.bean.User;
import com.baidu.paddle.lite.demo.component.MyCallBack;
import com.baidu.paddle.lite.demo.component.NetClient;
import com.baidu.paddle.lite.demo.component.ToastShow;

import java.util.ArrayList;
import java.util.List;


public class DataActivity extends Activity implements View.OnClickListener{

    private static List<Data> dataList = new ArrayList<>();
    private String userName;

    ToastShow myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        init();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case R.id.getdata:
                getData();
                break;
            case R.id.view:
                button();
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    public void init(){
        if (User.getInstance().isLogin()) {
            userName = User.getInstance().getAccount(); // 设置用户名字
        }
        TextView demoTextView;
        demoTextView = findViewById(R.id.textView6);
        demoTextView.setText("我的账号\t"+"训练数量\t"+"训练动作\t"+"平均分\t"+"最大分数\t"+"最小分数\t"+"训练日期");

        Button button;
        button = findViewById(R.id.getdata);
        button.setOnClickListener(this);


        Button button2;
        button2 = findViewById(R.id.delete);
        button2.setOnClickListener(this);

        Button button3;
        button3 = findViewById(R.id.view);
        button3.setOnClickListener(this);

    }

    public void button(){
        int j = 0;
        int[] textViews = {R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView11, R.id.textView12
                , R.id.textView13, R.id.textView14, R.id.textView15,R.id.textView16};
        for (int i = dataList.size()-1; i > dataList.size() - 8;i--){
            TextView demoTextView;
            demoTextView = findViewById(textViews[j]);
            if(dataList.get(i).getAccount().equals(userName)){

                Data data = dataList.get(i);
                j++;
                demoTextView.setText(data.getAccount()+"      "+data.getActionCount()+
                        "             "+data.getActionId()+"          "
                        +data.getAveScore()+"       "+data.getMaxScore()
                        +"          "+data.getMinScore()+"           "+data.getDateTime());
            }
        }

    }

    public void delete(){

        int j = 0;
        int[] textViews = {R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView11, R.id.textView12
                , R.id.textView13, R.id.textView14, R.id.textView15,R.id.textView16};
        for (int i = dataList.size()-1; i > dataList.size() - 8;i--){
            TextView demoTextView;
            demoTextView = findViewById(textViews[j]);
                j++;
                demoTextView.setText("");
        }
        String basePath ="http://43.143.108.99:5000/deleteData";

        if (User.getInstance().isLogin()) {
            userName = User.getInstance().getAccount(); // 设置用户名字
        }
        String data="?account="+userName;
        System.out.println(basePath+data);
        NetClient.getNetClient().callNet(basePath + data, new MyCallBack() {
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
                if(result.equals("deleteDataSuccess")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 未注册
                            myToast.toastShow("删除数据成功!");
                        }
                    });

                }else if(result.equals("deleteDataFailure")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取失败
                            myToast.toastShow("删除失败！");
                        }
                    });
                }
            }
        });
    }


    public void getData(){
        String basePath ="http://43.143.108.99:5000/ViewPracticeData";
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

                    dataList= JSONObject.parseArray(result, Data.class);
                    myToast =new ToastShow(DataActivity.this);
                    myToast.toastShow("获取数据成功！");

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