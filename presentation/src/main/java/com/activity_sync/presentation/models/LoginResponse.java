package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
    @SerializedName("responseType")
    private String responseType;

    @SerializedName("responseMessage")
    private LoginResponseDetails loginResponseDetails;

    public LoginResponse(String responseType, LoginResponseDetails loginResponseDetails)
    {
        this.responseType = responseType;
        this.loginResponseDetails = loginResponseDetails;
    }

    public LoginResponse()
    {

    }

    public String getResponseType()
    {
        return responseType;
    }

    public LoginResponseDetails getLoginResponseDetails()
    {
        return loginResponseDetails;
    }
}
