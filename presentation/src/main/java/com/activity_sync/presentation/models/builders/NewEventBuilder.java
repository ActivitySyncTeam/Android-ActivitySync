package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.NewEvent;

public class NewEventBuilder
{
    private int disciplineID;
    private String date;
    private Location address;
    private int numberOfPlayers;
    private int levelID;
    private String description;
    private boolean addMe;

    public NewEventBuilder setDisciplineID(int disciplineID)
    {
        this.disciplineID = disciplineID;
        return this;
    }

    public NewEventBuilder setDate(String date)
    {
        this.date = date;
        return this;
    }

    public NewEventBuilder setAddress(Location address)
    {
        this.address = address;
        return this;
    }

    public NewEventBuilder setNumberOfPlayers(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        return this;
    }

    public NewEventBuilder setLevelID(int levelID)
    {
        this.levelID = levelID;
        return this;
    }

    public NewEventBuilder setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public NewEventBuilder setAddMe(boolean addMe)
    {
        this.addMe = addMe;
        return this;
    }

    public NewEvent create()
    {
        return new NewEvent(disciplineID, date, address, numberOfPlayers, levelID, description, addMe);
    }
}
