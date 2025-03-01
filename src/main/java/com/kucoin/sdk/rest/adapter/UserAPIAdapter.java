package com.kucoin.sdk.rest.adapter;

import com.kucoin.sdk.ProxySettings;
import com.kucoin.sdk.rest.impl.retrofit.AuthRetrofitAPIImpl;
import com.kucoin.sdk.rest.interfaces.UserAPI;
import com.kucoin.sdk.rest.interfaces.retrofit.UserAPIRetrofit;
import com.kucoin.sdk.rest.response.Pagination;
import com.kucoin.sdk.rest.response.SubUserInfoResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Reeta on 2019-05-20
 */
public class UserAPIAdapter extends AuthRetrofitAPIImpl<UserAPIRetrofit> implements UserAPI {


    public UserAPIAdapter(
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
    public List<SubUserInfoResponse> listSubUsers() throws IOException {
        return super.executeSync(getAPIImpl().getSubUserList());
    }

    @Override
    public Pagination<SubUserInfoResponse> pageListSubUsers(int currentPage, int pageSize) throws IOException {
        return super.executeSync(getAPIImpl().getSubUserPageList(currentPage, pageSize));
    }
}
