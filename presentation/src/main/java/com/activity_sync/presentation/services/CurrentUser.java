package com.activity_sync.presentation.services;

import com.activity_sync.presentation.utils.StringUtils;

import javax.inject.Inject;

public class CurrentUser
{
    private final IPermanentStorage permanentStorage;
    private final INavigator navigator;

    @Inject
    public CurrentUser(IPermanentStorage permanentStorage, INavigator navigator)
    {
        this.permanentStorage = permanentStorage;
        this.navigator = navigator;
    }

    public String authToken()
    {
        return permanentStorage.retrieveString(IPermanentStorage.AUTH_TOKEN, IPermanentStorage.AUTH_TOKEN_DEFAULT);
    }

    public void authToken(String authToken)
    {
        permanentStorage.saveString(IPermanentStorage.AUTH_TOKEN, authToken);
    }

    public int userID()
    {
        return permanentStorage.retrieveInteger(IPermanentStorage.CURRENT_USER_ID, IPermanentStorage.CURRENT_USER_ID_DEFAULT);
    }

    public void userID(int userID)
    {
        permanentStorage.saveInteger(IPermanentStorage.CURRENT_USER_ID, userID);
    }

    public void logout()
    {
        authToken(StringUtils.EMPTY);
        userID(0);
    }
}
