/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk;

import java.io.IOException;
import java.util.Optional;

import com.kucoin.sdk.rest.adapter.*;
import com.kucoin.sdk.rest.interfaces.*;
import org.apache.commons.lang3.StringUtils;

import com.kucoin.sdk.constants.APIConstants;
import com.kucoin.sdk.impl.KucoinPrivateWSClientImpl;
import com.kucoin.sdk.impl.KucoinPublicWSClientImpl;
import com.kucoin.sdk.impl.KucoinRestClientImpl;
import com.kucoin.sdk.websocket.ChooseServerStrategy;
import com.kucoin.sdk.websocket.RandomChooseStrategy;

import lombok.Getter;

/**
 * Created by chenshiwei on 2019/1/9.
 */
@Getter
public class KucoinClientBuilder {

    private String apiKey;

    private String secret;

    private String passPhrase;

    private Optional<ProxySettings> proxySettingsO = Optional.empty();

    private boolean useProxy;

    /**
     * Version number of api-key, default 2
     */
    private Integer apiKeyVersion = 2;

    private String baseUrl;

    private UserAPI userAPI;

    private AccountAPI accountAPI;

    private DepositAPI depositAPI;

    private FillAPI fillAPI;

    private OrderAPI orderAPI;

    private WithdrawalAPI withdrawalAPI;

    private CurrencyAPI currencyAPI;

    private TimeAPI timeAPI;

    private CommonAPI commonAPI;

    private SymbolAPI symbolAPI;

    private OrderBookAPI orderBookAPI;

    private HistoryAPI historyAPI;

    private StopOrderAPI stopOrderAPI;

    private ChooseServerStrategy chooseServerStrategy;

    private MarginAPI marginAPI;

    private LoanAPI loanAPI;

    private IsolatedAPI isolatedAPI;

    public KucoinRestClient buildRestClient() {
        if (StringUtils.isBlank(baseUrl)) baseUrl = APIConstants.API_BASE_URL;
        if (userAPI == null) userAPI = new UserAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (accountAPI == null) accountAPI = new AccountAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (depositAPI == null) depositAPI = new DepositAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (withdrawalAPI == null) withdrawalAPI = new WithdrawalAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (fillAPI == null) fillAPI = new FillAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (orderAPI == null) orderAPI = new OrderAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (stopOrderAPI == null) stopOrderAPI = new StopOrderAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (marginAPI == null) marginAPI = new MarginAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (loanAPI == null) loanAPI = new LoanAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (isolatedAPI == null) isolatedAPI = new IsolatedAPIAdapter(baseUrl, apiKey, secret, proxySettingsO, useProxy, passPhrase, apiKeyVersion);
        if (currencyAPI == null) currencyAPI = new CurrencyAPIAdaptor(baseUrl, proxySettingsO, useProxy);
        if (timeAPI == null) timeAPI = new TimeAPIAdapter(baseUrl, proxySettingsO, useProxy);
        if (commonAPI == null) commonAPI = new CommonAPIAdapter(baseUrl, proxySettingsO, useProxy);
        if (symbolAPI == null) symbolAPI = new SymbolAPIAdaptor(baseUrl, proxySettingsO, useProxy);
        if (orderBookAPI == null) orderBookAPI = new OrderBookAPIAdapter(baseUrl, proxySettingsO, useProxy);
        if (historyAPI == null) historyAPI = new HistoryAPIAdapter(baseUrl, proxySettingsO, useProxy);
        return new KucoinRestClientImpl(this);
    }

    public KucoinPublicWSClient buildPublicWSClient() throws IOException {
        if (StringUtils.isBlank(baseUrl)) baseUrl = APIConstants.API_BASE_URL;
        if (chooseServerStrategy == null) chooseServerStrategy = new RandomChooseStrategy();
        KucoinPublicWSClientImpl client = new KucoinPublicWSClientImpl(this);
        client.connect();
        return client;
    }

    public KucoinPrivateWSClient buildPrivateWSClient() throws IOException {
        if (StringUtils.isBlank(baseUrl)) baseUrl = APIConstants.API_BASE_URL;
        if (chooseServerStrategy == null) chooseServerStrategy = new RandomChooseStrategy();
        KucoinPrivateWSClientImpl client = new KucoinPrivateWSClientImpl(this);
        client.connect();
        return client;
    }

    public KucoinClientBuilder withApiKey(String apiKey, String secret, String passPhrase) {
        this.apiKey = apiKey;
        this.secret = secret;
        this.passPhrase = passPhrase;
        return this;
    }

    public KucoinClientBuilder withProxy(Optional<ProxySettings> proxySettingsO, boolean useProxy) {
        this.proxySettingsO = proxySettingsO;
        this.useProxy = useProxy;
        return this;
    }

    public KucoinClientBuilder withApiKeyVersion(Integer apiKeyVersion) {
        this.apiKeyVersion = apiKeyVersion;
        return this;
    }

    public KucoinClientBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public KucoinClientBuilder withDepositAPI(DepositAPI depositAPI) {
        this.depositAPI = depositAPI;
        return this;
    }

    public KucoinClientBuilder withFillAPI(FillAPI fillAPI) {
        this.fillAPI = fillAPI;
        return this;
    }

    public KucoinClientBuilder withOrderAPI(OrderAPI orderAPI) {
        this.orderAPI = orderAPI;
        return this;
    }

    public KucoinClientBuilder withStopOrderAPI(StopOrderAPI stopOrderAPI) {
        this.stopOrderAPI = stopOrderAPI;
        return this;
    }

    public KucoinClientBuilder withWithdrawalAPI(WithdrawalAPI withdrawalAPI) {
        this.withdrawalAPI = withdrawalAPI;
        return this;
    }

    public KucoinClientBuilder withAccountAPI(AccountAPI accountAPI) {
        this.accountAPI = accountAPI;
        return this;
    }

    public KucoinClientBuilder withSymbolAPI(SymbolAPI symbolAPI) {
        this.symbolAPI = symbolAPI;
        return this;
    }

    public KucoinClientBuilder withOrderBookAPI(OrderBookAPI orderBookAPI) {
        this.orderBookAPI = orderBookAPI;
        return this;
    }

    public KucoinClientBuilder withHistoryAPI(HistoryAPI historyAPI) {
        this.historyAPI = historyAPI;
        return this;
    }

    public KucoinClientBuilder withCurrencyAPI(CurrencyAPI currencyAPI) {
        this.currencyAPI = currencyAPI;
        return this;
    }

    public KucoinClientBuilder withTimeAPI(TimeAPI timeAPI) {
        this.timeAPI = timeAPI;
        return this;
    }

    public KucoinClientBuilder withChooseServerStrategy(ChooseServerStrategy chooseServerStrategy) {
        this.chooseServerStrategy = chooseServerStrategy;
        return this;
    }
}
