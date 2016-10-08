package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.User;

public class EventBuilder
{
    private int id;
    private User organizer;
    private String description;
    private String date;

    public EventBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public EventBuilder setUser(User organizer)
    {
        this.organizer = organizer;
        return this;
    }

    public EventBuilder setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public EventBuilder setDate(String date)
    {
        this.date = date;
        return this;
    }

    public Event createEvent()
    {
        return new Event(id, organizer, description, date);
    }
}
