/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.rest.adapter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.kucoin.sdk.ProxySettings;
import com.kucoin.sdk.rest.impl.retrofit.PublicRetrofitAPIImpl;
import com.kucoin.sdk.rest.interfaces.SymbolAPI;
import com.kucoin.sdk.rest.interfaces.retrofit.SymbolAPIRetrofit;
import com.kucoin.sdk.rest.response.AllTickersResponse;
import com.kucoin.sdk.rest.response.SymbolResponse;
import com.kucoin.sdk.rest.response.SymbolTickResponse;
import com.kucoin.sdk.rest.response.TickerResponse;

/**
 * Created by chenshiwei on 2019/1/18.
 */
public class SymbolAPIAdaptor extends PublicRetrofitAPIImpl<SymbolAPIRetrofit> implements SymbolAPI {

    public SymbolAPIAdaptor(String baseUrl, Optional<ProxySettings> proxySettingsO, boolean useProxy) {
        this.baseUrl = baseUrl;
        this.proxySettingsO = proxySettingsO;
        this.useProxy = useProxy;
    }

    @Override
    public List<SymbolResponse> getSymbols() throws IOException {
        return super.executeSync(getAPIImpl().getSymbols());
    }

    @Override
    public TickerResponse getTicker(String symbol) throws IOException {
        return super.executeSync(getAPIImpl().getTicker(symbol));
    }

    @Override
    public AllTickersResponse getAllTickers() throws IOException {
        return super.executeSync(getAPIImpl().getAllTickers());
    }

    @Override
    public SymbolTickResponse get24hrStats(String symbol) throws IOException {
        return super.executeSync(getAPIImpl(). getMarketStats(symbol));
    }

    @Override
    public List<String> getMarketList() throws IOException {
        return super.executeSync(getAPIImpl().getMarketList());
    }

    @Override
    public List<SymbolResponse> getSymbolList(String market) throws IOException {
        return super.executeSync(getAPIImpl().getSymbolList(market));
    }
}
