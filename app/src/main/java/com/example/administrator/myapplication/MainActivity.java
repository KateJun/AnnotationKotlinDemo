package com.example.administrator.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
//        is24(30);
//        try {
//            doJson();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        editText.setText(System.currentTimeMillis() + "");
    }

    private void doJson() throws Exception {
        String txt = getFromAssets("dian24.txt");
        if (TextUtils.isEmpty(txt)) return;
        String TAG = "JSON";
        String[] arr = txt.split(" ");
        Log.i(TAG, arr.length + ":" + txt);
        StringBuilder sb = new StringBuilder();
        ArrayList<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < arr.length - 6; i += 7) {
            JSONObject job = new JSONObject();
            job.put("grade", arr[i]);
            job.put("data4", arr[i + 1] + "," + arr[i + 2] + "," + arr[i + 3] + "," + arr[i + 4]);
            job.put("averTime", arr[i + 5]);
            job.put("calcPow", arr[i + 6]);

//            Log.i(TAG,job.toString());

            Log.i(TAG, list.size() + arr[i] + ":" + list.toString());

            list.add(job);
        }
        Log.i(TAG, list.size() + ":" + list.toString());
        write(list.toString());
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void write(String info) {
//        try {
//            File file = new File(Environment.getExternalStorageDirectory(),
//                    "json.txt");
//            FileOutputStream fos = new FileOutputStream(file);
//
//            fos.write(info.getBytes());
//            fos.close();
//            System.out.println("写入成功：");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            File file = new File(getSdCardPath(),
                    "json.txt");
            //第二个参数意义是说是否以append方式添加内容
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(info);
            bw.flush();
            System.out.println("写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = this.getPackageResourcePath();
        }
        return sdpath;

    }

    /**
     * 获取默认的文件路径
     *
     * @return
     */
    public String getDefaultFilePath() {
        String filepath = "";
        File file = new File(Environment.getExternalStorageDirectory(),
                "json.txt");
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath = "不适用";
        }
        return filepath;
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

    static double LING = 1E-6;
    static final int CONT = 4;
    static final int VOLUE = 24;
    double number[] = new double[]{1, 2, 3, 4};
    String exp[] = new String[CONT];
    boolean m_judge = false; //判断是否有解。
    int count = 0;

    public boolean is24(int n) {
        if (n == 1)
            return (Math.abs(number[0] - 24) < 24);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // 进行组合
                double a, b;
                String expa, expb;
                a = number[i]; // 保存起来，在方法最后再恢复，以便继续计算
                b = number[j]; // 保存起来，在方法最后再恢复，以便继续计算
                number[j] = number[n - 1]; // 将最后一个数挪过来
                expa = exp[i]; // 保存起来，在方法最后再恢复，以便继续计算
                expb = exp[j]; // 保存起来，在方法最后再恢复，以便继续计算
                exp[j] = exp[n - 1]; // 将最后一个式子挪过来j'
                exp[i] = "(" + expa + "+" + expb + ")"; // 看看加法能否算出,如果能算出，返回true
                number[i] = a + b;
                if (is24(n - 1))
                    return true;
                exp[i] = "(" + expa + "-" + expb + ")"; // 看看减法能否算
                number[i] = a - b;
                if (is24(n - 1))
                    return true;
                exp[i] = "(" + expb + "-" + expa + ")";
                number[i] = b - a;
                if (is24(n - 1))
                    return true;
                exp[i] = "(" + expa + "*" + expb + ")"; // 看看乘法能否算
                number[i] = a * b;
                if (is24(n - 1))
                    return true;
                if (b != 0) {
                    exp[i] = "(" + expa + "/" + expb + ")"; // 看看除法能否算
                    number[i] = a / b;
                    if (is24(n - 1))
                        return true;
                }
                if (a != 0) {
                    exp[i] = "(" + expb + "/" + expa + ")";
                    number[i] = b / a;
                    if (is24(n - 1))
                        return true;
                }
                //如果以上的加、减、乘、除都不能得到有效的结果，则恢复数据进行下一轮的计算。
                number[i] = a; // 恢复
                number[j] = b;
                exp[i] = expa;
                exp[j] = expb;
            }
        }
        return false;
    }
}
