package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.NewEvent;
import com.activity_sync.presentation.models.RegisterResponse;

import java.util.List;

import rx.Observable;

public interface IApiService
{
    Observable<RegisterResponse> register(String username, String password, String firstName, String lastName, String email);

    Observable<LoginResponse> login(String username, String password);

    Observable<Void> logout();

    Observable<ClientDetails> getClientDetails();

    Observable<NewEvent> createEvent(String description, int disciplineID, int levelID, int playersNumber, Location location, String date, boolean addMe);

    Observable<EventsCollection> getAllEvents();

    Observable<Event> getEventDetails(int eventId);

    Observable<CommentsCollection> getEventComments(int eventId);

    Observable<List<Discipline>> getAvailableDisciplines();

    Observable<List<Level>> getAvailableLevels();
}
