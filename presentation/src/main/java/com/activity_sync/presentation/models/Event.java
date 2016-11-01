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

    @SerializedName("places")
    private int places;

    @SerializedName("location")
    private Location location;

    @SerializedName("discipline")
    private Discipline discipline;

    @SerializedName("price")
    private Price price;

    public Event(int id, User organizer, String description, Date date, int places, Location location, Discipline discipline, Price price)
    {
        this.id = id;
        this.organizer = organizer;
        this.description = description;
        this.date = date;
        this.places = places;
        this.location = location;
        this.discipline = discipline;
        this.price = price;
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

    public int getPlaces()
    {
        return places;
    }

    public void setPlaces(int places)
    {
        this.places = places;
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

    public Price getPrice()
    {
        return price;
    }

    public void setPrice(Price price)
    {
        this.price = price;
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