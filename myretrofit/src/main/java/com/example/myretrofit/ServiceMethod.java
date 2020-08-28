package com.example.myretrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 记录请求类型，参数，完整地址
 */
public class ServiceMethod {

    public static class Builder {
        private final EnjoyRetrofit enjoyRetrofit;
        private Annotation[] methodAnnotations;
        private Annotation[][] parameterAnotations;

        String httpMethod;
        String relativeUrl;
        Boolean hasBody;

        public Builder(EnjoyRetrofit enjoyRetrofit, Method method) {
            this.enjoyRetrofit = enjoyRetrofit;
            //获取方法所有的注解
            methodAnnotations = method.getAnnotations();
            //获得方法参数的所有注解,一个方法多个注解，一个参数也有多个注解
            parameterAnotations = method.getParameterAnnotations();

        }

        /**
         * 做具体的解析
         * @return
         */
        public ServiceMethod build() {
            //解析方法上的注解（暂时只解析post和get注解）
            for(Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation instanceof POST) {
                    //找到之后就要记录下来
                    this.httpMethod = "POST";
                    //记录请求url的path
                    this.relativeUrl = ((POST)methodAnnotation).value();
                    this.hasBody = true;
                } else if (methodAnnotation instanceof GET) {
                    this.httpMethod = "GET";
                    this.relativeUrl = ((GET)methodAnnotation).value();
                    this.hasBody = false;
                }
            }

            //解析方法参数的注解
            int  length = parameterAnotations.length;
            for (int i = 0; i < length; i++) {
                Annotation[] annotations = parameterAnotations[i];
            }
            return null;
        }
    }
}
