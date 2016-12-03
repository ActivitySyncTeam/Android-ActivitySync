package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.EnrollmentStatus;
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
    private int maxPlaces;
    private int occupiedPlaces;
    private Location location;
    private Discipline discipline;
    private Level level;
    private EnrollmentStatus enrollmentStatus;
    private boolean isActive;

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

    public EventBuilder setMaxPlaces(int maxPlaces)
    {
        this.maxPlaces = maxPlaces;
        return this;
    }

    public EventBuilder setOccupiedPlaces(int occupiedPlaces)
    {
        this.occupiedPlaces = occupiedPlaces;
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

    public EventBuilder setEnrollmentStatus(EnrollmentStatus enrollmentStatus)
    {
        this.enrollmentStatus = enrollmentStatus;
        return this;
    }

    public EventBuilder setIsActive(boolean isActive)
    {
        this.isActive = isActive;
        return this;
    }

    public Event createEvent()
    {
        return new Event(id, organizer, description, date, maxPlaces, occupiedPlaces, location, discipline, level, enrollmentStatus, isActive);
    }
}
