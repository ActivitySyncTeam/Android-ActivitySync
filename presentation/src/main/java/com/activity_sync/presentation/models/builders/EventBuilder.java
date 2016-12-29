package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.User;

import java.util.Date;

public class EventBuilder
{
    private int id;
    private User organizer;
    private String description;
    private Date date;
    private int numberOfPlayers;
    private int freePlaces;
    private int like;
    private Location location;
    private Discipline discipline;
    private Level level;
    private boolean isOrganizer;
    private boolean isParticipant;
    private boolean isCandidate;
    private int rate;

    public EventBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public EventBuilder setOrganizer(User organizer)
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

    public EventBuilder setNumberOfPlayers(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        return this;
    }

    public EventBuilder setFreePlaces(int freePlaces)
    {
        this.freePlaces = freePlaces;
        return this;
    }

    public EventBuilder setLike(int like)
    {
        this.like = like;
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

    public EventBuilder setLevel(Level level)
    {
        this.level = level;
        return this;
    }

    public EventBuilder setOrganizer(boolean organizer)
    {
        isOrganizer = organizer;
        return this;
    }

    public EventBuilder setParticipant(boolean participant)
    {
        isParticipant = participant;
        return this;
    }

    public EventBuilder setCandidate(boolean candidate)
    {
        isCandidate = candidate;
        return this;
    }

    public EventBuilder setRate(int rate)
    {
        this.rate = rate;
        return this;
    }

    public Event createEvent()
    {
        return new Event(id, organizer, description, date, numberOfPlayers, freePlaces, like, location, discipline, level, isOrganizer, isParticipant, isCandidate, rate);
    }
}
