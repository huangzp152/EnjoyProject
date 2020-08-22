package com.example.annotationtest;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Stanley(value = "22", id = "3")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    enum WeekDay {
        SUNDAY,MONDAY
    }

    private static int mCUrrentDAy;

//    @IntDef({SUNDAY,MONDAY})
//    @Target({ElementType.FIELD, ElementType.TYPE_PARAMETER})//给参数key和value打标签，作限定
//    @Retention(RetentionPolicy.SOURCE)

    //needfix
//    @interface WeekDay {//注解
//    }
//
//    public  static void setCurrentDAy(@WeekDay int currentDAy) {
//        mCUrrentDAy = currentDAy;
//    }

}
