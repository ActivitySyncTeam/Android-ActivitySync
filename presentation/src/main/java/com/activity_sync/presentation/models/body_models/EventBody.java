package com.activity_sync.presentation.models.body_models;

public class EventBody
{
    private String description;
    private String date;
    private int numberOfPlayers;
    private AddressBody address;
    private int disciplineID;
    private int levelID;
    private boolean addMe;
    private String status;

    public EventBody(String description, String date, int numberOfPlayers, AddressBody address, int disciplineID, int levelID, boolean addMe, String status)
    {
        this.description = description;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;
        this.address = address;
        this.disciplineID = disciplineID;
        this.levelID = levelID;
        this.addMe = addMe;
        this.status = status;
    }
}
