package com.example.plugin;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BaseActivity extends Activity {

    private Context mContext;
    private final static String apkPath = "/sdcard/plugin-debug.apk";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources resources = LoadUtils.loadResource(getApplication());
        mContext = new ContextThemeWrapper(getBaseContext(), 0);

        //为了反射获得context的mResources对象
        Class<? extends Context> clazz = mContext.getClass();
        Field mResourcesField = null;
        try {
            mResourcesField = clazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(mContext, resources);//设值进去
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //重写
//    @Override
//    public Resources getResources() {
//        if (getApplication() != null && getApplication().getResources() != null) {
//            return getApplication().getResources();
//        }
//        return super.getResources();
//    }
    public static Resources loadResource(Context context) {
        //1.创建assetmanager
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //2.从插件加载资源 （反射）
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
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
