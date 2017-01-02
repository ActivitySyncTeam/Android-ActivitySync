package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventBody;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.User;

import java.util.List;

import rx.Observable;

public interface IApiService
{
    Observable<RegisterResponse> register(String username, String password, String firstName, String lastName, String email);

    Observable<LoginResponse> login(String username, String password);

    Observable<Void> logout();

    Observable<ClientDetails> getClientDetails();

    Observable<User> getUserID();

    Observable<EventID> createEvent(EventBody eventBody);

    Observable<EventID> updateEvent(int eventId, EventBody eventBody);

    Observable<EventsCollection> getAllEvents();

    Observable<Event> getEventDetails(int eventId);

    Observable<CommentsCollection> getEventComments(int eventId);

    Observable<Void> addComment(int eventId, String comment);

    Observable<Participants> getEventParticipants(int eventId);

    Observable<Participants> joinEvent(int eventID);

    Observable<Participants> joinEventAsAdmin(int eventID);

    Observable<Participants> cancelEventJoinRequest(int eventID);

    Observable<Participants> leaveEvent(int eventID);

    Observable<List<Discipline>> getAvailableDisciplines();

    Observable<List<Level>> getAvailableLevels();
}
