package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.LoginResponseDetails;

public class LoginResponseDetailsBuilder
{
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String scope;

    public LoginResponseDetailsBuilder setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
        return this;
    }

    public LoginResponseDetailsBuilder setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
        return this;
    }

    public LoginResponseDetailsBuilder setExpiresIn(String expiresIn)
    {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponseDetailsBuilder setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
        return this;
    }

    public LoginResponseDetailsBuilder setScope(String scope)
    {
        this.scope = scope;
        return this;
    }

    public LoginResponseDetails create()
    {
        return new LoginResponseDetails(accessToken, tokenType, expiresIn, refreshToken, scope);
    }
}
