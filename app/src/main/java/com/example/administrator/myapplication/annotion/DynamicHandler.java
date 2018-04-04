package com.example.administrator.myapplication.annotion;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class DynamicHandler implements InvocationHandler {

    private final HashMap<String, Method> methodMap = new HashMap<>(
            1);
    // 因为传进来的为activity，使用弱引用主要是为了防止内存泄漏
    private WeakReference<Object> handlerRef;

    public DynamicHandler(Object object) {
        this.handlerRef = new WeakReference<Object>(object);
    }

    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }

    // 当回到OnClickListener的OnClick方法的时候，它会调用这里的invoke方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        获取activity对象
        Object act = handlerRef.get();
        if (act != null) {
//            method对应的就是回调方法onclick
            String methodName = method.getName();
//            得到activity中真正执行的click方法
            method = methodMap.get(methodName);
            if (method != null) {
                return method.invoke(act, args);
            }
        }

        return null;
    }
}
