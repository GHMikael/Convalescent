package com.baidu.paddle.lite.demo.yolo_detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.paddle.lite.demo.bean.Data;
import com.baidu.paddle.lite.demo.bean.PatientCache;
import com.baidu.paddle.lite.demo.component.ChooseActionId;
import com.baidu.paddle.lite.demo.component.CreateDialog;
import com.baidu.paddle.lite.demo.component.MyCallBack;
import com.baidu.paddle.lite.demo.component.NetClient;
import com.baidu.paddle.lite.demo.component.ToastShow;

import java.util.List;


public class PersonActivity extends Activity implements View.OnClickListener {

    ToastShow myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Button button;
        button = findViewById(R.id.update_actionId);
        button.setOnClickListener(this);

        init();


    }


    public void init(){

        TextView demoTextView;
        demoTextView = findViewById(R.id.textView6);
        demoTextView.setText("患者账号\t"+"训练数量\t"+"训练动作\t"+"平均分\t"+"最大分数\t"+"最小分数\t"+"训练日期");

        List<Data> dataList = Data.getDataList();
        int j = 0;
        int[] textViews = {R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView11, R.id.textView12
                , R.id.textView13, R.id.textView14, R.id.textView15,R.id.textView16};
        for (int i = dataList.size()-1; i > dataList.size() - 8;i--){
            TextView demoTextView1;
            demoTextView1 = findViewById(textViews[j]);
            if(dataList.get(i).getAccount().equals(PatientCache.getAccount())){

                Data data = dataList.get(i);
                j++;
                demoTextView1.setText(data.getAccount()+"      "+data.getActionCount()+
                        "             "+data.getActionId()+"          "
                        +data.getAveScore()+"       "+data.getMaxScore()
                        +"          "+data.getMinScore()+"           "+data.getDateTime());
            }
        }
    }
    public void showEditDialog() {
        ChooseActionId chooseActionId;
        chooseActionId = new ChooseActionId(this,R.style.mdialog,onClickListener);
        chooseActionId.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:

                    PatientCache.setInfo(ChooseActionId.text_info.getText().toString().trim());
                    PatientCache.setActionId(Integer.parseInt(ChooseActionId.actionId.getText().toString()));
                    upData();

                    Intent i = new Intent(PersonActivity.this,PersonActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };

    private void upData() {
        String basePath = "http://43.143.108.99:5000/updata";
        String data = "?account=" + PatientCache.getAccount() + "&actionId=" + PatientCache.getActionId()+"&info=" + PatientCache.getInfo();
        NetClient.getNetClient().callNet(basePath + data, new MyCallBack() {
            @Override
            public void onFailure(int code) {
                System.out.println(code);
                // 服务器连接失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myToast.toastShow("ServerError");
                    }
                });
            }

            @Override
            public void onResponse(String json) {
                System.out.println("json:" + json);
                String result = json;
                if (result.equals("success")) {
                    ToastShow myToast = new ToastShow(PersonActivity.this);
                    myToast.toastShow("患者训练方案更改成功！");

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myToast.toastShow("注册失败");
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_actionId:
                showEditDialog();
                break;
        }
    }
}