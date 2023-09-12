/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.rest.adapter;

import com.kucoin.sdk.ProxySettings;
import com.kucoin.sdk.rest.impl.retrofit.AuthRetrofitAPIImpl;
import com.kucoin.sdk.rest.interfaces.OrderBookAPI;
import com.kucoin.sdk.rest.interfaces.retrofit.OrderBookAPIRetrofit;
import com.kucoin.sdk.rest.response.OrderBookResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by chenshiwei on 2019/1/22.
 */
public class OrderBookAPIAdapter extends AuthRetrofitAPIImpl<OrderBookAPIRetrofit> implements OrderBookAPI {

    public OrderBookAPIAdapter(
            String baseUrl,
            String apiKey,
            String secret,
            Optional<ProxySettings> proxySettingsO,
            boolean useProxy,
            String passPhrase,
            Integer apiKeyVersion)
    {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.secret = secret;
        this.proxySettingsO = proxySettingsO;
        this.useProxy = useProxy;
        this.passPhrase = passPhrase;
        this.apiKeyVersion = apiKeyVersion;
    }

    @Override
    public OrderBookResponse getTop100Level2OrderBook(String symbol) throws IOException {
        return super.executeSync(getAPIImpl().getTop100Level2OrderBook(symbol));
    }

    @Override
    public OrderBookResponse getTop20Level2OrderBook(String symbol) throws IOException {
        return super.executeSync(getAPIImpl().getTop20Level2OrderBook(symbol));
    }

    @Override
    public OrderBookResponse getAllLevel2OrderBook(String symbol) throws IOException {
        return super.executeSync(getAPIImpl().getAllLevel2OrderBook(symbol));
    }
}
