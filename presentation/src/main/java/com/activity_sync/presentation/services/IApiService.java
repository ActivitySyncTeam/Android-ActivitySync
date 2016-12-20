package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;

import rx.Observable;

public interface IApiService
{
    Observable<AvailableDisciplines> getAvailableDisciplines();

    Observable<AvailableLevels> getAvailableLevels();
}
