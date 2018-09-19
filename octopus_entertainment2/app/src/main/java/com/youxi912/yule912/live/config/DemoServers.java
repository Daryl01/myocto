package com.youxi912.yule912.live.config;

/**
 * Demo应用服务器地址（第三方APP请不要使用）
 */
public class DemoServers {

    private static final String API_SERVER_TEST = "https://app.netease.im/api"; // 测试
    private static final String API_SERVER = "https://app.netease.im/api"; // 线上

    public static final String apiServer() {
        return ServerConfig.testServer() ? API_SERVER_TEST : API_SERVER;
    }
}