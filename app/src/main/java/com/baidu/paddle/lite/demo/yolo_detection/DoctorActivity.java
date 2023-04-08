package com.baidu.paddle.lite.demo.yolo_detection;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.paddle.lite.demo.bean.Data;
import com.baidu.paddle.lite.demo.bean.PatientCache;
import com.baidu.paddle.lite.demo.bean.User;
import com.baidu.paddle.lite.demo.component.CreateDialog;
import com.baidu.paddle.lite.demo.component.MyCallBack;
import com.baidu.paddle.lite.demo.component.MyDialog;
import com.baidu.paddle.lite.demo.component.NetClient;
import com.baidu.paddle.lite.demo.component.ToastShow;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DoctorActivity extends Activity implements View.OnClickListener{

    // TODO: 2023/3/22  

    private static List<Data> dataList = new ArrayList<>();

    ToastShow myToast;
    private static final String URL = "jdbc:mysql://43.143.108.99:3306/demo?serverTimezone=UTC&UserSSL=false";
    private static final String USER = "wxy";
    private static final String PASSWORD = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


//        demoTextView = findViewById(R.id.textView6);
//        demoTextView.setOnClickListener(this);

        //使用直接网站获取数据失败②

        init();

    }
    public void init(){
        TextView demoTextView;
        demoTextView = findViewById(R.id.textView6);
        demoTextView.setText("患者账号\t"+"训练数量\t"+"训练动作\t"+"平均分\t"+"最大分数\t"+"最小分数\t"+"训练日期");

        Button button;
        button = findViewById(R.id.getdata);
        button.setOnClickListener(this);

        Button button1;
        button1 = findViewById(R.id.insert);
        button1.setOnClickListener(this);

        Button button2;
        button2 = findViewById(R.id.delete);
        button2.setOnClickListener(this);

        Button button3;
        button3 = findViewById(R.id.view);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case R.id.getdata:
                getData();
                break;
            case R.id.insert:
                showEditDialog();

                //showDialog();

                break;
            case R.id.view:
                button();
                break;
            case R.id.delete:
                intoMyInfo();
                break;
        }
    }

    public void showEditDialog() {
        CreateDialog createDialog;
        createDialog = new CreateDialog(this,R.style.mdialog,onClickListener);
        createDialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:

                    PatientCache.setAccount(CreateDialog.account.getText().toString().trim());
                    choosePerson();
                    break;
            }
        }
    };
    private void showDialog() {
        MyDialog dialog = new MyDialog(DoctorActivity.this, R.style.mdialog,
                new MyDialog.OncloseListener() {
                    @Override
                    public void onClick(boolean confirm) {
                        if (confirm) {
                            // TODO: 2023/3/29

                            choosePerson();

                        } else {
                            // TODO:
                        }
                    }
                });
        dialog.show();
    }
    public void intoMyInfo(){
        Intent i = new Intent(DoctorActivity.this,DoctorMyActivity.class);
        startActivity(i);

    }
    public void button(){
        int j = 0;
        int[] textViews = {R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView11, R.id.textView12
                , R.id.textView13, R.id.textView14, R.id.textView15,R.id.textView16};
        for (int i = dataList.size()-1; i > dataList.size() - 11;i--){
            TextView demoTextView;
            demoTextView = findViewById(textViews[j]);
            Data data = dataList.get(i);
            j++;
            demoTextView.setText(data.getAccount()+"      "+data.getActionCount()+
                    "             "+data.getActionId()+"          "
                    +data.getAveScore()+"       "+data.getMaxScore()
                    +"          "+data.getMinScore()+"           "+data.getDateTime());
        }

    }

    public void choosePerson(){
        Intent i= new Intent(DoctorActivity.this,PersonActivity.class);
        startActivity(i);
    }
    public void uploadPracticeData(){
        String userName;
        userName = "18752411776";
        if (User.getInstance().isLogin()) {
            userName = User.getInstance().getAccount(); // 设置用户名字
        }
        //接口地址
        String basePath = "http://43.143.108.99:5000/uploadPracticeData";
        //？+参数  &用于链接多个参数
        //?参数名1={具体值}&参数名2={}...
        String data = "?account=" + userName + "&actionId=" + 2 + "&actionCount=" + 20 + "&maxScore=" + 80 + "&minScore=" + 60 + "&aveScore=" + 70;
        System.out.println("data========"+data);
        NetClient.getNetClient().callNet(basePath + data, new MyCallBack() {
            //重载回调函数
            @Override
            public void onFailure(int code) {
                System.out.println(code);
                // 服务器连接失败
                myToast.toastShow("ServerError");
            }

            @Override
            public void onResponse(String json) {
                System.out.println("json:" + json);
                String result = json;
                if (result.equals("uploadPracticeDataSuccess")) {
                    myToast =new ToastShow(DoctorActivity.this);
                    myToast.toastShow("患者训练方案更改成功！");

                } else {
                    myToast.toastShow("上传失败");
                }
            }
        });
    }


    public void delete(){
        String basePath ="http://43.143.108.99:5000/deleteData";
        String data="?account=18752411776";
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

                    Data.setDataList(JSONObject.parseArray(result,Data.class));
                    dataList = Data.getDataList();
                    myToast =new ToastShow(DoctorActivity.this);
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

        //实现连接jdbc封装
        //通过使用jdbc完成与mysql数据库的连接，调用数据库的数据
        //失败③
        public Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "select * from data";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    info.put("account", resultSet.getString(1));
                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return info;
        }
        //执行view的渲染
        public void onPostExecute(Map<String, String> result) {
                TextView textViewAddress = findViewById(R.id.textView6);
                textViewAddress.setText("Hello"+result.get("account"));
        }
//    public static void main(String[] args) {
//        System.out.println(DataDao.getList().get(1).getAccount());
//        Map<String, String> info = new HashMap<>();
//
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            String sql = "select * from data";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                info.put("account", resultSet.getString(1));
//            }
//        } catch (Exception e) {
//            Log.e("InfoAsyncTask", "Error reading school information", e);
//        }
//
//        System.out.println(info);
//    }

}
