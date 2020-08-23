package com.example.reflecttest;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

public class InjectUtils {

    public static void injectview(Activity activity) {
        Class <? extends Activity> cls = activity.getClass();

//        cls.getSuperclass().getDeclaredField()

        for (Field declaredField : cls.getDeclaredFields()) {
            //判断属性是否被InjectView声明（这个会影响性能）
            if (declaredField.isAnnotationPresent(InjectView.class)) {
                //这个方法是用来获得注解类的对象的，并且可以调用这个对象的其他声明的方法
                InjectView injectView = declaredField.getAnnotation(InjectView.class);
                injectView.j();
                int id = injectView.value();
                View view = activity.findViewById(id);
                declaredField.setAccessible(true);
                try {
                    //反射拿到activity的view对象，实现了activity中的view跟他们的注解中填的id的关联
                    //就是TextVIew tv = activity.findViewById(R.id.tttvvv);
                    declaredField.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
