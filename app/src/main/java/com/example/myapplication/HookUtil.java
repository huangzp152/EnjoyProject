package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {

    public static void hookAMS() throws Exception {


        Class<?> iactivityMAnagerClass = Class.forName("android.app.IActivityManager");//为什么源码里没有这个
        //创建代理对象，代理IActivityManager对象，修改其接口的所有的方法
        /*
        ActivityManager.getService()
                .startActivity(whoThread, who.getBasePackageName(), intent,
                        intent.resolveTypeIfNeeded(who.getContentResolver()),
                        token, target != null ? target.mEmbeddedID : null,
                        requestCode, 0, null, options);
         */
        Class<?> clazz = Class.forName("android.app.ActivityManager");//ActivityManager.getService()
        Field singletonField = clazz.getDeclaredField("IActivityManagerSingleton");
        singletonField.setAccessible(true);
        //singleton的对象
        Object singleton = singletonField.get(null);//静态
        //mInstance的对象
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        final Object mInstance = mInstanceField.get(singleton);

        /*
        ActivityManager.Singleton.mInstance
        mInstance -> Singleton的对象 -> ActivityManager.class对象
         */
        //动态代理的对象，里面用来改变逻辑的
        Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{iactivityMAnagerClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("startActivity".equals(method.getName())) {//过滤
                            int index = 0;
                            for(int i = 0; i < args.length; i++) {
                                if (args[i] instanceof Intent) {
                                    index = i;
                                    break;
                                }
                            }
                            //启动插件的intent
                            Intent intent = (Intent) args[index];
                            //替换
                            Intent proxyINtent = new Intent();
                            proxyINtent.setClassName("com.example.myapplication",
                                    ProxyActivity.class.getName());
                            args[index] = proxyINtent;
                        }
                        return method.invoke(mInstance, args);//minstance 就是系统本来要调用的对象，反射去拿，就是这个：ActivityManager.getService()
                    }
                });
        //替换系统调用的那个对象
        mInstanceField.set(singleton, mInstanceProxy);//mInstance = mInstanceProxy


    }

//    public static Resources loadResource(Context context) {
//        //1.创建assetmanager
//        try {
//            AssetManager assetManager = AssetManager.class.newInstance();
//            //2.从插件加载资源 （反射）
//            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.c);
//            //反射获取的名字.invoke(对象实例，参数）
//            addAssetPathMethod.invoke(assetManager, apkPath);
//            //3。创建resource对象返回给插件
//            Resources resources = context.getResources();
//            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
