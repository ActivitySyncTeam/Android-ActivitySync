package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalInfo implements Serializable
{
    @SerializedName("isAdmin")
    private boolean isOrganizer;

    @SerializedName("isParticipant")
    private boolean isParticipant;

    @SerializedName("isCandidate")
    private boolean isCandidate;

    @SerializedName("isFriend")
    private boolean isFriend;

    @SerializedName("rate")
    private int rate;

    public AdditionalInfo(boolean isOrganizer, boolean isParticipant, boolean isFriend, boolean isCandidate, int rate)
    {
        this.isOrganizer = isOrganizer;
        this.isCandidate = isCandidate;
        this.isFriend = isFriend;
        this.isParticipant = isParticipant;
        this.rate = rate;
    }

    public AdditionalInfo()
    {

    }

    public boolean isOrganizer()
    {
        return isOrganizer;
    }

    public void setOrganizer(boolean organizer)
    {
        isOrganizer = organizer;
    }

    public boolean isParticipant()
    {
        return isParticipant;
    }

    public void setParticipant(boolean participant)
    {
        isParticipant = participant;
    }

    public boolean isCandidate()
    {
        return isCandidate;
    }

    public void setCandidate(boolean candidate)
    {
        isCandidate = candidate;
    }

    public boolean isFriend()
    {
        return isFriend;
    }

    public void setFriend(boolean friend)
    {
        isFriend = friend;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}