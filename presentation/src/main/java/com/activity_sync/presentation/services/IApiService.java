package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.RegisterResponse;

import rx.Observable;

public interface IApiService
{
    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_ERROR = "ERROR";

    Observable<RegisterResponse> registerUser(String username, String password,  String firstName, String lastName, String email);

    Observable<AvailableDisciplines> getAvailableDisciplines();

    Observable<AvailableLevels> getAvailableLevels();
}
