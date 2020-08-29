package com.example.myretrofit;

import com.example.myretrofit.retrofit.Field;
import com.example.myretrofit.retrofit.GET;
import com.example.myretrofit.retrofit.POST;
import com.example.myretrofit.retrofit.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * 记录请求类型，参数，完整地址
 */
public class ServiceMethod {
    private String httpMethod;
    private HttpUrl baseUrl;
    private okhttp3.Call.Factory callFactory;
    private String relativeUrl;
    private Boolean hasBody;
    private ParameterHandler[] parameterHandlers;
    private FormBody.Builder formBuild;
    private FormBody formBody;
    private HttpUrl.Builder urlBuilder;


    public ServiceMethod(Builder builder) {
        baseUrl = builder.enjoyRetrofit.baseUrl;
        callFactory = builder.enjoyRetrofit.callFactory;
        httpMethod = builder.httpMethod;
        hasBody = builder.hasBody;
        parameterHandlers = builder.enjoyRetrofit.parameterHandlers;

        if (hasBody) {
            formBuild = new FormBody.Builder();
        }
    }

    public Object invoke(Object[] args) {
        /**
         * 1.处理请求地址和参数
         *
         */
        for (int i = 0 ; i< parameterHandlers.length; i++) {
            ParameterHandler parameterHandler = parameterHandlers[i];
            parameterHandler.apply(this, args[i].toString());
        }

        //获取最终请求地址
        HttpUrl url;
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        url = urlBuilder.build();

        //请求体
        if (formBuild != null) {
            formBody = formBuild.build();
        }

        Request request = new Request.Builder().url(url).method(httpMethod, formBody).build();
        return callFactory.newCall(request);
    }

    //k-v 放在url中（get）
    public void addQueryParameters(String key, String value) {
        if (urlBuilder == null) {
            urlBuilder = new HttpUrl.Builder();
        }
        urlBuilder.addQueryParameter(key, value);
    }

    //k-v 放在请求体中（post）
    public void addFieldParameters(String key, String value) {
        formBuild.add(key,value);
    }

    public static class Builder {
        private final EnjoyRetrofit enjoyRetrofit;
        private Annotation[] methodAnnotations;
        private Annotation[][] parameterAnotations;
        private ParameterHandler[] parameterHandlers;
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

            //2 解析方法参数的注解
            int  length = parameterAnotations.length;
            parameterHandlers =  new ParameterHandler[length];
            for (int i = 0; i < length; i++) {
                Annotation[] annotations = parameterAnotations[i];
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Field) {
                        //得到注解上的value
                        String value = ((Field) annotation).value();
                        //这里要报错，或者提示使用query注解
                        parameterHandlers[i] = new ParameterHandler.FieldParameterHandler(value);
                    } else if (annotation instanceof Query) {
                        String value = ((Query) annotation).value();
                        parameterHandlers[i] = new ParameterHandler.QueryParameterHandler(value);
                    }

                }
            }
            return new ServiceMethod(this);
        }
    }
}
