package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.LoginResponse;

public class LoginResponseBuilder
{
    private String responseType;
    private String loginResponseDetails;

    public LoginResponseBuilder setResponseType(String responseType)
    {
        this.responseType = responseType;
        return this;
    }

    public LoginResponseBuilder setLoginResponseDetails(String loginResponseDetails)
    {
        this.loginResponseDetails = loginResponseDetails;
        return this;
    }

    public LoginResponse create()
    {
        return new LoginResponse(responseType, loginResponseDetails);
    }
}
