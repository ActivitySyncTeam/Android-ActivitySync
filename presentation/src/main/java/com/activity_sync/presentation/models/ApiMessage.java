package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApiMessage implements Serializable
{
    public final static String FRIEND_REQUEST = "friend_request";
    public final static String EVENT_CANCEL= "event_cancel";
    public final static String EVENT_APPROVED = "event_approved";
    public final static String EVENT_EDITED = "event_edited";
    public final static String EVENT_INVITE = "event_invite";
    public final static String EVENT_DELETE = "event_delete";

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    public ApiMessage(int id, String message)
    {
        this.id = id;
        this.message = message;
    }

    public ApiMessage()
    {

    }

    public int getId()
    {
        return id;
    }

    public String getMessage()
    {
        return message;
    }
}
