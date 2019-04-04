package com.autoserve.abc.service.http.core;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 缓存 HttpClientConnection 的工厂
 *
 * @author shensheng.ss 2014年4月25日 下午3:07:07
 */
class HttpClientFactory {

    private static final int connectTimeout          = 30000;
    private static final int socketTimeout           = 10000;

    private static final int maxTotalConns           = 2000;
    private static final int defaultMaxConnsPerRoute = 100;

    static CloseableHttpClient getCloseableHttpClient() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotalConns);
        cm.setDefaultMaxPerRoute(defaultMaxConnsPerRoute);

        // 后续：
        // 1. 参数=>minas
        // 2. 扩展考虑根据具体应用，配置连接数
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();

    }

}
