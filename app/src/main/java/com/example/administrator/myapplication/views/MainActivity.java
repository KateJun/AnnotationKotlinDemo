package com.example.administrator.myapplication.views;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.annotion.ContentViewInject;
import com.example.administrator.myapplication.annotion.OnClick;
import com.example.administrator.myapplication.annotion.Shape;
import com.example.administrator.myapplication.annotion.ViewInject;
import com.example.administrator.myapplication.annotion.util.ViewUtil;
import com.example.administrator.myapplication.util.FileUtil;
import com.kotlin.lib.FirstKotLin;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


@ContentViewInject(def = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private FirstKotLin obj;

    @ViewInject(R.id.textView)
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ViewUtil.injectContentView(this);
        ViewUtil.injectView(this);
        ViewUtil.injectEvents(this);
//        editText = (EditText) findViewById(R.id.textView);
    }

    @OnClick({R.id.button})
    public void click(View v) {
        System.out.println("click---------------------------");
        exeKL(1);

//        try {
//        FileUtil.Entity.doJson(this);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        editText.setText(System.currentTimeMillis() + "");
    }



    //////////////////////////
    public void exeKL(@Shape int a) {
        if (obj == null)
            obj = new FirstKotLin();
//        obj.syso(1);
//        obj.syso("a");
//        obj.syso(null);
//        boolean b = obj.assetInt();
//        System.out.println("b=" + b + obj.parseInts("9"));
        obj.doRequest();
    }


}
