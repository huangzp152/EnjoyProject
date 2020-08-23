package com.example.reflecttest;

import androidx.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    @IdRes int value();//表名限定注解的值，要是一个资源id
    int j();
}
