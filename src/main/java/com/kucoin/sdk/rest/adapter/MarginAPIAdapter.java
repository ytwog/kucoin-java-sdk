/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.rest.adapter;

import com.kucoin.sdk.ProxySettings;
import com.kucoin.sdk.rest.impl.retrofit.AuthRetrofitAPIImpl;
import com.kucoin.sdk.rest.interfaces.MarginAPI;
import com.kucoin.sdk.rest.interfaces.retrofit.MarginAPIRetrofit;
import com.kucoin.sdk.rest.request.MarginOrderCreateRequest;
import com.kucoin.sdk.rest.response.MarginAccountResponse;
import com.kucoin.sdk.rest.response.MarginConfigResponse;
import com.kucoin.sdk.rest.response.MarginOrderCreateResponse;
import com.kucoin.sdk.rest.response.MarginPriceStrategyResponse;
import com.kucoin.sdk.rest.response.MarkPriceResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ezreal on 2020/12/08.
 */
public class MarginAPIAdapter extends AuthRetrofitAPIImpl<MarginAPIRetrofit> implements MarginAPI {

    public MarginAPIAdapter(
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
    public MarkPriceResponse getMarkPrice(String symbol) throws IOException{
        return executeSync(getAPIImpl().getMarkPrice(symbol));
    }

    @Override
    public MarginConfigResponse getMarginConfig() throws IOException {
        return executeSync(getAPIImpl().getMarginConfig());
    }

    @Override
    public MarginAccountResponse getMarginAccount() throws IOException {
        return executeSync(getAPIImpl().getMarginAccount());
    }

    @Override
    public MarginOrderCreateResponse createMarginOrder(MarginOrderCreateRequest request) throws IOException {
        return executeSync(getAPIImpl().createMarginOrder(request));
    }

    @Override
    public List<MarginPriceStrategyResponse> getMarginPriceStrategy(String marginModel) throws IOException {
        return executeSync(getAPIImpl().getMarginPriceStrategy(marginModel));
    }
}
