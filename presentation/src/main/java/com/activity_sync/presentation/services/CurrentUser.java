package com.activity_sync.presentation.services;

import com.activity_sync.presentation.utils.StringUtils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class CurrentUser
{
    private final IPermanentStorage permanentStorage;
    private final INavigator navigator;
    private final IApiService apiService;

    @Inject
    public CurrentUser(IPermanentStorage permanentStorage, INavigator navigator, IApiService apiService)
    {
        this.permanentStorage = permanentStorage;
        this.navigator = navigator;
        this.apiService = apiService;
    }

    public String accessToken()
    {
        return permanentStorage.retrieveString(IPermanentStorage.ACCESS_TOKEN, StringUtils.EMPTY);
    }

    public void accessToken(String authToken)
    {
        permanentStorage.saveString(IPermanentStorage.ACCESS_TOKEN, authToken);
    }

    public String clientId()
    {
        return permanentStorage.retrieveString(IPermanentStorage.CLIENT_ID, StringUtils.EMPTY);
    }

    public void clientId(String clientId)
    {
        permanentStorage.saveString(IPermanentStorage.CLIENT_ID, clientId);
    }

    public String clientSecret()
    {
        return permanentStorage.retrieveString(IPermanentStorage.CLIENT_SECRET, StringUtils.EMPTY);
    }

    public void clientSecret(String clientSecret)
    {
        permanentStorage.saveString(IPermanentStorage.CLIENT_SECRET, clientSecret);
    }

    public void logout()
    {
        apiService.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    accessToken(StringUtils.EMPTY);
                    clientId(StringUtils.EMPTY);
                    clientSecret(StringUtils.EMPTY);

                    navigator.stopBackgroundService();
                    navigator.openLoginScreen();
        });
    }
}
