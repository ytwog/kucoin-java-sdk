/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kucoin.sdk.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinPrivateWSClient;
import com.kucoin.sdk.constants.APIConstants;
import com.kucoin.sdk.factory.HttpClientFactory;
import com.kucoin.sdk.model.enums.PrivateChannelEnum;
import com.kucoin.sdk.rest.adapter.WebsocketPrivateAPIAdaptor;
import com.kucoin.sdk.rest.interfaces.WebsocketPrivateAPI;
import com.kucoin.sdk.rest.response.WebsocketTokenResponse;
import com.kucoin.sdk.websocket.ChooseServerStrategy;
import com.kucoin.sdk.websocket.KucoinAPICallback;
import com.kucoin.sdk.websocket.event.*;
import com.kucoin.sdk.websocket.impl.BaseWebsocketImpl;
import com.kucoin.sdk.websocket.listener.KucoinPrivateWebsocketListener;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by chenshiwei on 2019/1/18.
 */
public class KucoinPrivateWSClientImpl extends BaseWebsocketImpl implements KucoinPrivateWSClient {

    private final WebsocketPrivateAPI websocketPrivateAPI;
    private final KucoinPrivateWebsocketListener listener;

    public KucoinPrivateWSClientImpl(KucoinClientBuilder kucoinClientBuilder) {
        this(
                HttpClientFactory.getPublicClient(Optional.empty(), false),
                new KucoinPrivateWebsocketListener(),
                kucoinClientBuilder.getChooseServerStrategy(),
                new WebsocketPrivateAPIAdaptor(kucoinClientBuilder.getBaseUrl(),
                        kucoinClientBuilder.getApiKey(),
                        kucoinClientBuilder.getSecret(),
                        kucoinClientBuilder.getPassPhrase(),
                        kucoinClientBuilder.getApiKeyVersion()));
    }

    private KucoinPrivateWSClientImpl(OkHttpClient client,
                                      KucoinPrivateWebsocketListener listener,
                                      ChooseServerStrategy chooseServerStrategy,
                                      WebsocketPrivateAPI websocketPublicAPI) {
        super(client, listener, chooseServerStrategy);
        this.listener = listener;
        this.websocketPrivateAPI = websocketPublicAPI;
    }

    @Override
    protected WebsocketTokenResponse requestToken() throws IOException {
        return this.websocketPrivateAPI.getPrivateToken();
    }

    @Override
    @Deprecated
    public String onOrderActivate(KucoinAPICallback<KucoinEvent<OrderActivateEvent>> callback, String... symbols) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_ACTIVATE_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_ACTIVATE_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<OrderActivateEvent>>() {
                    });
        }
        String topic = APIConstants.API_ACTIVATE_TOPIC_PREFIX + String.join(",", symbols);
        return subscribe(topic, true, true);
    }

    @Override
    public String onAccountBalance(KucoinAPICallback<KucoinEvent<AccountChangeEvent>> callback) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_BALANCE_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_BALANCE_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<AccountChangeEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_BALANCE_TOPIC_PREFIX, true, true);
    }

    @Override
    public String onOrderChange(KucoinAPICallback<KucoinEvent<OrderChangeEvent>> callback) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_ORDER_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_ORDER_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<OrderChangeEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_ORDER_TOPIC_PREFIX, true, true);
    }

    @Override
    public String onOrderV2Change(KucoinAPICallback<KucoinEvent<OrderChangeEvent>> callback) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_ORDER_V2_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_ORDER_V2_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<OrderChangeEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_ORDER_V2_TOPIC_PREFIX, true, true);
    }

    @Override
    public String onMarginPosition(KucoinAPICallback<KucoinEvent<MarginPositionEvent>> callback) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_MARGIN_POSITION_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_MARGIN_POSITION_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<MarginPositionEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_MARGIN_POSITION_TOPIC_PREFIX, true, true);
    }

    @Override
    public String onMarginLoan(KucoinAPICallback<KucoinEvent<MarginLoanEvent>> callback, String symbol) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_MARGIN_LOAN_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_MARGIN_LOAN_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<MarginLoanEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_MARGIN_LOAN_TOPIC_PREFIX + symbol, true, true);
    }

    @Override
    public String onAdvancedOrder(KucoinAPICallback<KucoinEvent<? extends AdvancedOrderEvent>> callback, String... symbols) {
        if (callback != null) {
            this.listener.getCallbackMap().put(APIConstants.API_ADVANCED_ORDER_TOPIC_PREFIX, callback);
            this.listener.getTypeReferenceMap().put(APIConstants.API_ADVANCED_ORDER_TOPIC_PREFIX,
                    new TypeReference<KucoinEvent<? extends AdvancedOrderEvent>>() {
                    });
        }
        return subscribe(APIConstants.API_ADVANCED_ORDER_TOPIC_PREFIX, true, true);
    }

    @Override
    public String ping(String requestId) {
        return super.ping(requestId);
    }

    @Override
    public String unsubscribe(PrivateChannelEnum channelEnum, String... symbols) {
        return super.unsubscribe(channelEnum.getTopicPrefix() + Arrays.stream(symbols).collect(Collectors.joining(",")),
                true, true);
    }
}
