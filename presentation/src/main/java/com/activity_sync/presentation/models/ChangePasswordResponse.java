package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse
{
    @SerializedName("msg")
    private String message;

    public ChangePasswordResponse(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
