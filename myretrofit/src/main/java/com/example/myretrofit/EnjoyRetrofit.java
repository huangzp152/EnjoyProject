package com.example.myretrofit;

import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class EnjoyRetrofit {

    private HttpUrl baseUrl; //域名
    private okhttp3.Call.Factory callFactory;

    public EnjoyRetrofit(Call.Factory callFactory, HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
    }

    /**
     * 返回接口的对象，通过动态代理
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return Proxy.newProxyInstance(service)
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


