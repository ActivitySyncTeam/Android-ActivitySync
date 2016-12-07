package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.AdditionalInfo;

public class AdditionalInfoBuilder
{
    private boolean isOrganizer;
    private boolean isParticipant;
    private boolean isCandidate;
    private int rate;

    public AdditionalInfoBuilder setOrganizer(boolean organizer)
    {
        isOrganizer = organizer;
        return this;
    }

    public AdditionalInfoBuilder setParticipant(boolean participant)
    {
        isParticipant = participant;
        return this;
    }

    public AdditionalInfoBuilder setCandidate(boolean candidate)
    {
        isCandidate = candidate;
        return this;
    }

    public AdditionalInfoBuilder setRate(int rate)
    {
        this.rate = rate;
        return this;
    }

    public AdditionalInfo createAdditionalInfo()
    {
        return new AdditionalInfo(isOrganizer, isParticipant, isCandidate, rate);
    }
}
