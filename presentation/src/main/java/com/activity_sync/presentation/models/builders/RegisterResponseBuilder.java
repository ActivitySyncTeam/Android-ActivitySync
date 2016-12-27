package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.RegisterResponseDetails;

public class RegisterResponseBuilder
{
    private String responseType;
    private RegisterResponseDetails registerResponseDetails;

    public RegisterResponseBuilder setResponseType(String responseType)
    {
        this.responseType = responseType;
        return this;
    }

    public RegisterResponseBuilder setRegisterResponseDetails(RegisterResponseDetails registerResponseDetails)
    {
        this.registerResponseDetails = registerResponseDetails;
        return this;
    }

    public RegisterResponse create()
    {
        return new RegisterResponse(responseType, registerResponseDetails);
    }
}
