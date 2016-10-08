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

    public Event(int id, User organizer, String description, Date date)
    {
        this.id = id;
        this.organizer = organizer;
        this.description = description;
        this.date = date;
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