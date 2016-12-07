package com.activity_sync.presentation.services;

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

    public String userID()
    {
        return permanentStorage.retrieveString(IPermanentStorage.CURRENT_USER_ID, IPermanentStorage.CURRENT_USER_ID_DEFAULT);
    }

    public void userID(String userID)
    {
        permanentStorage.saveString(IPermanentStorage.CURRENT_USER_ID, userID);
    }
}
