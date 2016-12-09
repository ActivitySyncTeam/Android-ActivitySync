package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("organizer")
    private User organizer;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private Date date;

    @SerializedName("max_places")
    private int maxPlaces;

    @SerializedName("occupied_places")
    private int occupiedPlaces;

    @SerializedName("location")
    private Location location;

    @SerializedName("discipline")
    private Discipline discipline;

    @SerializedName("level")
    private Level level;

    @SerializedName("user")
    private AdditionalInfo additionalInfo;

    @SerializedName("is_active")
    private boolean isActive;

    public Event(int id, User organizer, String description, Date date, int maxPlaces, int occupiedPlaces, Location location, Discipline discipline, Level level, AdditionalInfo additionalInfo, boolean isActive)
    {
        this.id = id;
        this.organizer = organizer;
        this.description = description;
        this.date = date;
        this.maxPlaces = maxPlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.location = location;
        this.discipline = discipline;
        this.level = level;
        this.additionalInfo = additionalInfo;
        this.isActive = isActive;
    }

    public Event()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getOrganizer()
    {
        return organizer;
    }

    public void setOrganizer(User organizer)
    {
        this.organizer = organizer;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getMaxPlaces()
    {
        return maxPlaces;
    }

    public void setMaxPlaces(int maxPlaces)
    {
        this.maxPlaces = maxPlaces;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public Discipline getDiscipline()
    {
        return discipline;
    }

    public void setDiscipline(Discipline discipline)
    {
        this.discipline = discipline;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public int getOccupiedPlaces()
    {
        return occupiedPlaces;
    }

    public void setOccupiedPlaces(int occupiedPlaces)
    {
        this.occupiedPlaces = occupiedPlaces;
    }

    public AdditionalInfo getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo)
    {
        this.additionalInfo = additionalInfo;
    }

    public String getReadableDate()
    {
        if (date != null)
        {
            DateFormat df = new SimpleDateFormat("MM/dd HH:mm");
            return df.format(date);
        }
        else
        {
            return "";
        }
    }
}