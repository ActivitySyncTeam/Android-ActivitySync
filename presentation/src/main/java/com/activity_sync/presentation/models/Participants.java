package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Participants
{
    @SerializedName("participants")
    private List<User> participants;

    @SerializedName("candidates")
    private List<User> candidates;

    public Participants(List<User> participants, List<User> candidates)
    {
        this.participants = participants;
        this.candidates = candidates;
    }

    public Participants()
    {

    }

    public List<User> getParticipants()
    {
        return participants;
    }

    public List<User> getCandidates()
    {
        return candidates;
    }
}
