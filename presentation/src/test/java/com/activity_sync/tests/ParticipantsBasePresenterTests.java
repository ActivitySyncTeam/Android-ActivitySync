package com.activity_sync.tests;

import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.FriendsBuilder;
import com.activity_sync.presentation.models.builders.ParticipantsBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.DeclinedUsersPresenter;
import com.activity_sync.presentation.presenters.UsersFragmentBasePresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantsBasePresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IUsersFragmentView view;

    @Mock
    IApiService apiService;

    @Mock
    CurrentUser currentUser;

    protected PublishSubject participantSelectedEvent = PublishSubject.create();
    protected PublishSubject refreshParticipantsEvent = PublishSubject.create();

    protected User testedUser;
    protected int eventId = 1;

    protected Friends friends;
    protected Participants participants;

    @Before
    public void setup()
    {
        testedUser = new UserBuilder()
                .setUserId(12)
                .setName("Marcin")
                .setSurname("Zielinski")
                .setCredibility(85)
                .createUser();

        friends = new FriendsBuilder().setFriends(Arrays.asList(testedUser)).setCandidates(Arrays.asList(testedUser)).create();
        participants = new ParticipantsBuilder().setParticipants(Arrays.asList(testedUser)).setCandidates(Arrays.asList(testedUser)).create();

        Mockito.when(view.selectedUser()).thenReturn(participantSelectedEvent);
        Mockito.when(view.refreshUsers()).thenReturn(refreshParticipantsEvent);

        Mockito.when(apiService.getFriends(currentUser.userId())).thenReturn(Observable.just(friends));
        Mockito.when(apiService.getEventParticipants(eventId)).thenReturn(Observable.just(participants));
    }

    @Test
    public void participantsBasePresenter_selectParticipant_openUserDetailsScreen()
    {
        UsersFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        participantSelectedEvent.onNext(testedUser);
        Mockito.verify(navigator).openUserDetailsScreen(12);
    }

    @Test
    public void participantsBasePresenter_refreshList_reloadParticipants()
    {
        UsersFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        refreshParticipantsEvent.onNext(this);
        Mockito.verify(apiService, times(2)).getEventParticipants(eventId);
        Mockito.verify(view, times(2)).refreshingVisible(false);
    }

    private UsersFragmentBasePresenter createPresenter()
    {
        return new DeclinedUsersPresenter(view, navigator, Schedulers.immediate(), apiService, eventId);
    }
}
