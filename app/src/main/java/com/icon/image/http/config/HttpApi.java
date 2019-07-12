package com.icon.image.http.config;

/**
 * 请求地址相关配置
 */

public class HttpApi {

    private static HttpApi api;

    private HttpApi() {
    }

    public static HttpApi getInstance() {
        if (api == null) {
            api = new HttpApi();
        }
        return api;
    }

    public String getImageApi(String type, int page, int count) {
        return String.format("http://gank.io/api/data/%s/%s/%s", type, count, page);
    }

}
