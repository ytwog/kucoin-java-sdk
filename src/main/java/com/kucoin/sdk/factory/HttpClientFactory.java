/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.factory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Optional;
import java.util.Properties;

import com.kucoin.sdk.ProxySettings;
import com.kucoin.sdk.rest.interceptor.AuthenticationInterceptor;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by chenshiwei on 2019/1/18.
 */
public class HttpClientFactory {

    /**
     * Proxy settings are used by Rest Client only atm
     */
    public static OkHttpClient getPublicClient(Optional<ProxySettings> proxySettingsO, boolean useProxy) {
        return buildHttpClient(null, proxySettingsO, useProxy);
    }

    public static OkHttpClient getAuthClient(
            String apiKey,
            String secret,
            Optional<ProxySettings> proxySettingsO,
            boolean useProxy,
            String passPhrase,
            Integer apiKeyVersion)
    {
        return buildHttpClient(
                new AuthenticationInterceptor(apiKey, secret, passPhrase, apiKeyVersion),
                proxySettingsO,
                useProxy);
    }

    private static OkHttpClient buildHttpClient(
            Interceptor interceptor,
            Optional<ProxySettings> proxySettingsO,
            boolean useProxy)
    {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(100);
        dispatcher.setMaxRequests(100);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(dispatcher);

        if (proxySettingsO.isPresent()) {
            updateWithProxyBySettings(builder, proxySettingsO.get());
        } else if (useProxy) {
            updateWithProxyBySystemProperties(builder);
        }

        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    private static void updateWithProxyBySystemProperties(OkHttpClient.Builder builder) {
        Properties systemProperties = System.getProperties();
        updateWithProxy(
                builder,
                systemProperties.getProperty("https.proxyHost"),
                systemProperties.getProperty("https.proxyPort"),
                Authenticator.NONE);
    }

    private static void updateWithProxyBySettings(OkHttpClient.Builder builder, ProxySettings proxySettings) {
        String credential = Credentials.basic(proxySettings.getLogin(), proxySettings.getPassword());
        Authenticator proxyAuthenticator = (route, response) -> response.request().newBuilder()
                .header("Proxy-Authorization", credential)
                .build();
        updateWithProxy(
                builder,
                proxySettings.getIp(),
                proxySettings.getPort(),
                proxyAuthenticator
        );
    }

    private static void updateWithProxy(OkHttpClient.Builder builder, String ip, String port, Authenticator proxyAuthenticator) {
        builder
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, Integer.parseInt(port))))
                .proxyAuthenticator(proxyAuthenticator);
    }
}
