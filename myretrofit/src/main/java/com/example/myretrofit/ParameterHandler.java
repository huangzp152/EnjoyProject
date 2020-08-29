package com.example.myretrofit;

public abstract class ParameterHandler {
    abstract void apply(ServiceMethod serviceMethod, String value);

    public static class QueryParameterHandler extends ParameterHandler{
        String key;
        public QueryParameterHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addQueryParameters(key, value);
        }
    }

    public static class FieldParameterHandler extends ParameterHandler{
        String key;
        public FieldParameterHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {

        }
    }

}
