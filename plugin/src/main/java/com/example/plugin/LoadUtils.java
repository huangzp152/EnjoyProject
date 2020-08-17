package com.example.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

public class LoadUtils {
    private final static String apkPath = "/sdcard/plugin-debug.apk";
        public static Resources loadResource(Context context) {
        //1.创建assetmanager
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //2.从插件加载资源 （反射）
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.c);
            //反射获取的名字.invoke(对象实例，参数）
            addAssetPathMethod.invoke(assetManager, apkPath);
            //3。创建resource对象返回给插件
            Resources resources = context.getResources();
            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
