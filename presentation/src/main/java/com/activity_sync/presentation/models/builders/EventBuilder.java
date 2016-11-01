package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.Price;
import com.activity_sync.presentation.models.User;

import java.util.Date;

public class EventBuilder
{
    private int id;
    private User organizer;
    private String description;
    private Date date;
    private int places;
    private Location location;
    private Discipline discipline;
    private Price price;
    private boolean isOrganizer;
    private boolean isParticipant;

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

    public EventBuilder setDate(Date date)
    {
        this.date = date;
        return this;
    }

    public EventBuilder setPlaces(int places)
    {
        this.places = places;
        return this;
    }

    public EventBuilder setLocation(Location location)
    {
        this.location = location;
        return this;
    }

    public EventBuilder setDiscipline(Discipline discipline)
    {
        this.discipline = discipline;
        return this;
    }

    public EventBuilder setPrice(Price price)
    {
        this.price = price;
        return this;
    }

    public EventBuilder setIsOrganizer(boolean isOrganizer)
    {
        this.isOrganizer = isOrganizer;
        return this;
    }

    public EventBuilder setIsParticipant(boolean isParticipant)
    {
        this.isParticipant = isParticipant;
        return this;
    }

    public Event createEvent()
    {
        return new Event(id, organizer, description, date, places, location, discipline, price, isOrganizer, isParticipant);
    }
}
