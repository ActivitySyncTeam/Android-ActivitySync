package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.RegisterResponse;

import rx.Observable;

public interface IApiService
{
    String RESPONSE_SUCCESS = "SUCCESS";
    String RESPONSE_ERROR = "ERROR";

    Observable<RegisterResponse> register(String username, String password, String firstName, String lastName, String email);

    Observable<LoginResponse> login(String username, String password);

    Observable<Void> logout();

    Observable<ClientDetails> getClientDetails();

    Observable<Void> createEvent(String description, int disciplineID, int levelID, int playersNumber, Location location, String date, boolean addMe);

    Observable<AvailableDisciplines> getAvailableDisciplines();

    Observable<AvailableLevels> getAvailableLevels();
}
