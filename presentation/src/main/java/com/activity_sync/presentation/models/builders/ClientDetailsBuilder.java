package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.ClientDetails;

public class ClientDetailsBuilder
{
    private String clientId;
    private String clientSecret;

    public ClientDetailsBuilder setClientId(String clientId)
    {
        this.clientId = clientId;
        return this;
    }

    public ClientDetailsBuilder setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
        return this;
    }

    public ClientDetails create()
    {
        return new ClientDetails(clientId, clientSecret);
    }
}
