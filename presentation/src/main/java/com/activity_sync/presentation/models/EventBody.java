package com.activity_sync.presentation.models;

public class EventBody
{
    private String description;
    private String date;
    private int numberOfPlayers;
    private AddressBody address;
    private int disciplineID;
    private int levelID;
    private boolean addMe;

    public EventBody(String description, String date, int numberOfPlayers, AddressBody address, int disciplineID, int levelID, boolean addMe)
    {
        this.description = description;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;
        this.address = address;
        this.disciplineID = disciplineID;
        this.levelID = levelID;
        this.addMe = addMe;
    }
}
