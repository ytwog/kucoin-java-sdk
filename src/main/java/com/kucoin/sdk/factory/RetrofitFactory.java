/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.factory;

import java.util.Optional;

import com.kucoin.sdk.KucoinObjectMapper;
import com.kucoin.sdk.ProxySettings;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by chenshiwei on 2019/1/10.
 */
public class RetrofitFactory {

    private static final Converter.Factory CONVERTER_FACTORY = JacksonConverterFactory.create(KucoinObjectMapper.INSTANCE);

    public static Retrofit getPublicRetorfit(
            String baseUrl,
            Optional<ProxySettings> proxySettingsO,
            boolean useProxy)
    {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(CONVERTER_FACTORY)
                .client(HttpClientFactory.getPublicClient(proxySettingsO, useProxy))
                .build();

    }

    public static Retrofit getAuthRetorfit(
            String baseUrl,
            String apiKey,
            String secret,
            Optional<ProxySettings> proxySettingsO,
            boolean useProxy,
            String passPhrase,
            Integer apiKeyVersion)
    {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(CONVERTER_FACTORY)
                .client(HttpClientFactory.getAuthClient(apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion))
                .build();

    }
}