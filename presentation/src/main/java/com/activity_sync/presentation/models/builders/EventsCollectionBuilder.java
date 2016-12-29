package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventsCollection;

import java.util.List;

public class EventsCollectionBuilder
{
    private int count;
    private String next;
    private String previous;
    private List<Event> events;

    public EventsCollectionBuilder setCount(int count)
    {
        this.count = count;
        return this;
    }

    public EventsCollectionBuilder setNext(String next)
    {
        this.next = next;
        return this;
    }

    public EventsCollectionBuilder setPrevious(String previous)
    {
        this.previous = previous;
        return this;
    }

    public EventsCollectionBuilder setEvents(List<Event> events)
    {
        this.events = events;
        return this;
    }

    public EventsCollection create()
    {
        return new EventsCollection(count, next, previous, events);
    }
}
