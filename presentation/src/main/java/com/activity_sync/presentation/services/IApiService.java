package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ChangePasswordResponse;
import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.UserID;
import com.activity_sync.presentation.models.body_models.EventBody;
import com.activity_sync.presentation.models.body_models.EventIDBody;
import com.activity_sync.presentation.models.body_models.OrganizerApprovalBody;
import com.activity_sync.presentation.models.body_models.UserIDBody;

import java.util.List;

import rx.Observable;

public interface IApiService
{
    Observable<RegisterResponse> register(String username, String password, String firstName, String lastName, String email);

    Observable<LoginResponse> login(String username, String password);

    Observable<Void> logout();

    Observable<ClientDetails> getClientDetails();

    Observable<User> getMyProfile();

    Observable<User> getUserData(int userId);

    Observable<User> rateUser(int userId, int rate);

    Observable<EventID> createEvent(EventBody eventBody);

    Observable<EventID> updateEvent(int eventId, EventBody eventBody);

    Observable<EventsCollection> getAllEvents();

    Observable<EventsCollection> getMyEvents();

    Observable<Event> getEventDetails(int eventId);

    Observable<CommentsCollection> getEventComments(int eventId);

    Observable<Comment> addComment(int eventId, String comment);

    Observable<Participants> getEventParticipants(int eventId);

    Observable<Friends> getFriends(int userId);

    Observable<Participants> joinEvent(int eventID);

    Observable<Participants> joinEventAsAdmin(int eventID);

    Observable<Participants> cancelEventJoinRequest(EventIDBody eventID);

    Observable<Participants> leaveEvent(EventIDBody eventID);

    Observable<Void> deleteEvent(int eventId);

    Observable<Participants> acceptCandidate(int eventID, int personID);

    Observable<Participants> removeParticipant(OrganizerApprovalBody organizerApprovalBody);

    Observable<Participants> rejectCandidate(OrganizerApprovalBody organizerApprovalBody);

    Observable<Void> deleteFriend(UserIDBody userIDBody);

    Observable<Void> rejectFriendRequest(UserIDBody userIDBody);

    Observable<Friends> acceptFriendInvitation(int userID);

    Observable<Friends> sendFriendRequest(int userID);

    Observable<Friends> cancelFriendInvitation(int userID);

    Observable<List<Discipline>> getAvailableDisciplines();

    Observable<List<Level>> getAvailableLevels();

    Observable<ChangePasswordResponse> changePassword(String currentPassword, String newPassword);

    Observable<UserID> updateUser(String name, String surname, String signature, String email);
}
