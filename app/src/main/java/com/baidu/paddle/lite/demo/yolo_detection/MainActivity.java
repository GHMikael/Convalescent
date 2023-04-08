package com.baidu.paddle.lite.demo.yolo_detection;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Spinner;


import com.baidu.paddle.lite.demo.bean.HeroBean;
import com.baidu.paddle.lite.demo.common.MsAdapter;
import com.baidu.paddle.lite.demo.component.ToastShow;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private Button start;
    private Spinner process;
    private Spinner course;
    private Context mContext;
    private String[] processNameList;
    private String[] courseNameList;
    ToastShow myToast;


    //判断是否为刚进去时触发 onItemSelected 的标志
    private boolean one_selected = false;
    private boolean two_selected = false;
    private ArrayList<HeroBean> processData = null;
    private ArrayList<HeroBean> courseData = null;
    private MsAdapter processAdadpter = null;
    private MsAdapter courseAdadpter = null;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_main);
        initView();
        Button button2 = findViewById(R.id.button14);

        //按钮进行监听
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(MainActivity.this,MyActivity.class);
                startActivity(intent);
            }
        });

    }

    private void bindViews() {
        process = (Spinner) findViewById(R.id.process_selector);
        course = (Spinner) findViewById(R.id.course_selector);

        // 添加选择器绑定
        processNameList =getResources().getStringArray(R.array.process_names);
        courseNameList =getResources().getStringArray(R.array.course_names);

        processData.add(new HeroBean(R.drawable.bed, processNameList[0]));
        processData.add(new HeroBean(R.drawable.outbed, processNameList[1]));
        processData.add(new HeroBean(R.drawable.hospital, processNameList[2]));

        courseData.add(new HeroBean(R.drawable.arm, courseNameList[0]));
        courseData.add(new HeroBean(R.drawable.leg, courseNameList[1]));
        courseData.add(new HeroBean(R.drawable.neck, courseNameList[2]));
        courseData.add(new HeroBean(R.drawable.waist, courseNameList[3]));



        processAdadpter = new MsAdapter<HeroBean>(processData,R.layout.spinner_item_process) {
            @Override
            public void bindView(MsAdapter.ViewHolder holder, HeroBean obj) {
                holder.setImageResource(R.id.process_icon,obj.getIcon());
                holder.setText(R.id.process_name, obj.getName());
            }
        };

        courseAdadpter = new MsAdapter<HeroBean>(courseData,R.layout.spinner_item_course) {
            @Override
            public void bindView(MsAdapter.ViewHolder holder, HeroBean obj) {
                holder.setImageResource(R.id.course_icon,obj.getIcon());
                holder.setText(R.id.course_name, obj.getName());
            }
        };

        process.setAdapter(processAdadpter);
        process.setOnItemSelectedListener(this);

        course.setAdapter(courseAdadpter);
        course.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }



    private void  initView() {
        mContext = MainActivity.this;
        processData =new ArrayList<HeroBean>();
        courseData=new ArrayList<HeroBean>();

        start = findViewById(R.id.btn_entry);

        start.setOnClickListener(this);
        findViewById(R.id.button13).setOnClickListener(this);
        findViewById(R.id.button12).setOnClickListener(this);
        bindViews();
    }








    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btn_entry:
                i = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(i);
                break;
            case R.id.button13:
                myToast =new ToastShow(MainActivity.this);
                myToast.toastShow("您已处于训练模式");
                break;
            case R.id.button12:
                i = new Intent(MainActivity.this,FindActivity.class);
                startActivity(i);
                break;

        }
    }





    /**
     * 显示对话框
     */


}
