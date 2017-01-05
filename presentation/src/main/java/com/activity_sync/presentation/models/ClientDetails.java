package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientDetails implements Serializable
{
    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    public ClientDetails(String clientId, String clientSecret)
    {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public ClientDetails()
    {

    }

    public String getClientId()
    {
        return clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }
}