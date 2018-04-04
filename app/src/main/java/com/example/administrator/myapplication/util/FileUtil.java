package com.example.administrator.myapplication.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public enum  FileUtil {
    Entity;



    public void doJson(Context ct) throws Exception {
        String txt = getFromAssets(ct,"dian24.txt");
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
        write(ct,list.toString());
    }

    public String getFromAssets(Context ct, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(ct.getResources().getAssets().open(fileName));
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


    private void write(Context ct ,String info) {
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
            File file = new File(getSdCardPath(ct),
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
    public String getSdCardPath(Context ct) {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = ct.getPackageResourcePath();
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
}
