package com.example.annotationtest;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IntDef
@Target(ElementType.TYPE)//元注解，type表示可以作用在class上，加了就只能作用在类上面加
@Retention(RetentionPolicy.CLASS)//就是定义在什么阶段（代码阶段，被编译器忽略/编译成class后，被jvm忽略/运行时）注解还保留存在，是一种保留级别
public @interface Stanley {
    String value() default "xxx";//value是默认的，不用value的话，注解使用的地方就要加上方法名作为key
    String id();
}
