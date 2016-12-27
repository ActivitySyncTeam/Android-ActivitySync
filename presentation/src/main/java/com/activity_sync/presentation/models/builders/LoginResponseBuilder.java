package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.LoginResponseDetails;

public class LoginResponseBuilder
{
    private String responseType;
    private LoginResponseDetails loginResponseDetails;

    public LoginResponseBuilder setResponseType(String responseType)
    {
        this.responseType = responseType;
        return this;
    }

    public LoginResponseBuilder setLoginResponseDetails(LoginResponseDetails loginResponseDetails)
    {
        this.loginResponseDetails = loginResponseDetails;
        return this;
    }

    public LoginResponse create()
    {
        return new LoginResponse(responseType, loginResponseDetails);
    }
}
