package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;

import rx.Observable;

public interface IEventDetailsView
{
    Observable joinEventClick();

    Observable organizerDetailsClick();

    void setEventData(Event event);
}
