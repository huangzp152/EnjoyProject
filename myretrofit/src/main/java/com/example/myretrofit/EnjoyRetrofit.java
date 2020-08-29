package com.example.myretrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class EnjoyRetrofit {

    public HttpUrl baseUrl; //域名
    public okhttp3.Call.Factory callFactory;
    public ParameterHandler[] parameterHandlers;

    private static Map<Method, ServiceMethod> serviceMethodCache = new HashMap<Method, ServiceMethod>();

    public EnjoyRetrofit(Call.Factory callFactory, HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
    }

    private ServiceMethod loadServiceMethod(Method method) {
        //获取ServiceMethod，有缓存就拿缓存的
        ServiceMethod result = serviceMethodCache.get(method);

        if (result != null) return result;
        synchronized (serviceMethodCache) {//让线程依次进来
            result = serviceMethodCache.get(method);//避免另外一个线程再重新解析
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return null;
    }

    /**
     * 返回接口的对象，通过动态代理
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //解析这个method上的所有注解信息
                ServiceMethod serviceMethod = loadServiceMethod(method);
                return serviceMethod.invoke(objects);
            }
        });
    }


    public static final class Builder {
        private HttpUrl baseUrl; //域名
        private okhttp3.Call.Factory callFactory;

        public Builder callFactory(okhttp3.Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public EnjoyRetrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("base URL required");
            }
            okhttp3.Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }
            return new EnjoyRetrofit(callFactory, baseUrl);
        }
    }
}


