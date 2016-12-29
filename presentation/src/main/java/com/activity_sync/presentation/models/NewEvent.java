package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewEvent implements Serializable
{
    @SerializedName("disciplineID")
    private int disciplineID;

    @SerializedName("date")
    private String date;

    @SerializedName("address")
    private Location address;

    @SerializedName("numberOfPlayers")
    private int numberOfPlayers;

    @SerializedName("levelID")
    private int levelID;

    @SerializedName("description")
    private String description;

    @SerializedName("addMe")
    private boolean addMe;

    public NewEvent(int disciplineID, String date, Location address, int numberOfPlayers, int levelID, String description, boolean addMe)
    {
        this.disciplineID = disciplineID;
        this.date = date;
        this.address = address;
        this.numberOfPlayers = numberOfPlayers;
        this.levelID = levelID;
        this.description = description;
        this.addMe = addMe;
    }

    public NewEvent()
    {

    }

    public int getDisciplineID()
    {
        return disciplineID;
    }

    public String getDate()
    {
        return date;
    }

    public Location getAddress()
    {
        return address;
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public int getLevelID()
    {
        return levelID;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isAddMe()
    {
        return addMe;
    }
}
