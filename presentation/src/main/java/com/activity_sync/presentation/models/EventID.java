package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

public class EventID
{
    @SerializedName("eventID")
    private int eventID;

    public EventID(int eventID)
    {
        this.eventID = eventID;
    }

    public EventID()
    {

    }

    public int getEventID()
    {
        return eventID;
    }
}
