package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import timber.log.Timber;

public class Event implements Serializable
{
    @SerializedName("eventID")
    private int eventID;

    @SerializedName("admin")
    private User organizer;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    @SerializedName("numberOfPlayers")
    private int numberOfPlayers;

    @SerializedName("freePlaces")
    private int freePlaces;

    @SerializedName("like")
    private int like;

    @SerializedName("address")
    private Location location;

    @SerializedName("discipline")
    private Discipline discipline;

    @SerializedName("level")
    private Level level;

    @SerializedName("isAdmin")
    private boolean isOrganizer;

    @SerializedName("isParticipant")
    private boolean isParticipant;

    @SerializedName("isCandidate")
    private boolean isCandidate;

    @SerializedName("rate")
    private int rate;

    public Event(int eventID, User organizer, String description, String date, int numberOfPlayers, int freePlaces, int like, Location location, Discipline discipline, Level level, boolean isOrganizer, boolean isParticipant, boolean isCandidate,  int rate)
    {
        this.eventID = eventID;
        this.organizer = organizer;
        this.description = description;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;
        this.freePlaces = freePlaces;
        this.like = like;
        this.location = location;
        this.discipline = discipline;
        this.level = level;
        this.isOrganizer = isOrganizer;
        this.isParticipant = isParticipant;
        this.isCandidate = isCandidate;
        this.rate = rate;
    }

    public Event()
    {

    }

    public int getEventId()
    {
        return eventID;
    }

    public User getOrganizer()
    {
        return organizer;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public Location getLocation()
    {
        return location;
    }

    public Discipline getDiscipline()
    {
        return discipline;
    }

    public Level getLevel()
    {
        return level;
    }

    public int getFreePlaces()
    {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces)
    {
        this.freePlaces = freePlaces;
    }

    public int getLike()
    {
        return like;
    }

    public boolean isOrganizer()
    {
        return isOrganizer;
    }

    public boolean isParticipant()
    {
        return isParticipant;
    }

    public boolean isCandidate()
    {
        return isCandidate;
    }

    public int getRate()
    {
        return rate;
    }

    public void setParticipant(boolean participant)
    {
        isParticipant = participant;
    }

    public void setCandidate(boolean candidate)
    {
        isCandidate = candidate;
    }

    public String getReadableDate()
    {
        try
        {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime jodatime = dtf.parseDateTime(date);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd HH:mm");

            return dtfOut.print(jodatime);
        }
        catch (Exception e)
        {
            Timber.i("Error during joda date formatting");
            return date;
        }
    }
}