package com.example.administrator.myapplication;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.*;

public class ViewUtil {

    public static void injectContentView(Activity activity) {
        Class a = activity.getClass();
        if (a.isAnnotationPresent(ContentViewInject.class)) {
            ContentViewInject contentView = (ContentViewInject) a.getAnnotation(ContentViewInject.class);
            int layoutId = contentView.def();
            try {
                Method m = a.getMethod("setContentView", int.class);
                m.setAccessible(true);
                m.invoke(activity, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void injectView(Activity activity) {
        Class a = activity.getClass();
        Field[] fields = a.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ViewInject.class)) {
                ViewInject contentView = f.getAnnotation(ViewInject.class);
                int viewId = contentView.value();
                try {
                    Method m = a.getMethod("findViewById", int.class);
                    m.setAccessible(true);
                    Object obj = m.invoke(activity, viewId);
                    f.setAccessible(true);
                    f.set(activity, obj);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                }

            }
        }
    }

    public static void injectEvents(Activity activity) {
        Class a = activity.getClass();
//        得到所有方法
        Method[] methods = a.getDeclaredMethods();
        for (Method m : methods) {
//            得到被onclick注解的方法
            if (m.isAnnotationPresent(OnClick.class)) {
//                得到该方法的注解
                OnClick click = m.getAnnotation(OnClick.class);
//                得到注解的值
                int[] ids = click.value();
//                得到注解上的注解
                EventBase eventBase = click.annotationType().getAnnotation(EventBase.class);
//                得到event注解中的值
                String setMethod = eventBase.listenerSetter();
                Class<?> listenerClass = eventBase.listenerType();
                String methodName = eventBase.methodName();

//                 动态代理
                DynamicHandler handler = new DynamicHandler(activity);
                Object obj = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class<?>[]{listenerClass}, handler);
                handler.addMethod(methodName, m);
                // 为每个view设置点击事件
                for (int viewId : ids) {
                    try {
                        Method findViewByIdMethod = a.getMethod("findViewById", int.class);
                        findViewByIdMethod.setAccessible(true);
                        View view = (View) findViewByIdMethod.invoke(activity, viewId);
                        Method setEventListenerMethod = view.getClass().getMethod(setMethod, listenerClass);
                        setEventListenerMethod.setAccessible(true);
                        setEventListenerMethod.invoke(view, obj);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
