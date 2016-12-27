package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterResponse implements Serializable
{
    @SerializedName("responseType")
    private String responseType;

    @SerializedName("responseMessage")
    private RegisterResponseDetails registerResponseDetails;

    public RegisterResponse(String responseType, RegisterResponseDetails registerResponseDetails)
    {
        this.responseType = responseType;
        this.registerResponseDetails = registerResponseDetails;
    }

    public RegisterResponse()
    {

    }

    public String getResponseType()
    {
        return responseType;
    }

    public RegisterResponseDetails getRegisterResponseDetails()
    {
        return registerResponseDetails;
    }
}
