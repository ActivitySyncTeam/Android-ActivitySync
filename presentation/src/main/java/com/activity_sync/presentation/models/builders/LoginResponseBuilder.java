package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.LoginResponse;

public class LoginResponseBuilder
{
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String scope;

    public LoginResponseBuilder setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
        return this;
    }

    public LoginResponseBuilder setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
        return this;
    }

    public LoginResponseBuilder setExpiresIn(String expiresIn)
    {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponseBuilder setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
        return this;
    }

    public LoginResponseBuilder setScope(String scope)
    {
        this.scope = scope;
        return this;
    }

    public LoginResponse create()
    {
        return new LoginResponse(accessToken, tokenType, expiresIn, refreshToken, scope);
    }
}
