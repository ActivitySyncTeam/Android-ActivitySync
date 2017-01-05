package com.activity_sync.presentation.models.body_models;


public class OrganizerApprovalBody
{
    private int eventID;
    private int personID;

    public OrganizerApprovalBody(int eventID, int personID)
    {
        this.eventID = eventID;
        this.personID = personID;
    }

    public OrganizerApprovalBody()
    {

    }

    public int getEventID()
    {
        return eventID;
    }

    public int getPersonID()
    {
        return personID;
    }
}
