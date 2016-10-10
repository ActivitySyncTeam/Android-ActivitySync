package com.activity_sync.presentation.views;

import rx.Observable;

public interface IEventDetailsView
{
    Observable joinEventClick();

    Observable organizerDetailsClick();
}
