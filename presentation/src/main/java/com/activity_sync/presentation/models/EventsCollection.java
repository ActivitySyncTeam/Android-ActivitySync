package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventsCollection implements Serializable
{
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<Event> events;

    public EventsCollection(int count, String next, String previous, List<Event> events)
    {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.events = events;
    }

    public EventsCollection()
    {

    }

    public int getCount()
    {
        return count;
    }

    public String getNext()
    {
        return next;
    }

    public String getPrevious()
    {
        return previous;
    }

    public List<Event> getEvents()
    {
        return events;
    }
}

