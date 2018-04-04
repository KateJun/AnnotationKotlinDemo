package com.example.administrator.myapplication.annotion;

import android.support.annotation.IntDef;


@IntDef(flag = true, value={1,2,3,4})
public @interface Shape {

    int def() default 0;
}
