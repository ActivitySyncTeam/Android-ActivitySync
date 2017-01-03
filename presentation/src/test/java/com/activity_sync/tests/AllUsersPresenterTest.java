package com.activity_sync.tests;

import com.activity_sync.presentation.models.FindUsersResponse;
import com.activity_sync.presentation.models.UsersCollection;
import com.activity_sync.presentation.models.builders.FoundUserBuilder;
import com.activity_sync.presentation.models.builders.UsersCollectionBuilder;
import com.activity_sync.presentation.presenters.AllUsersPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IAllUsersScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AllUsersPresenterTest
{
    @Mock
    IApiService apiService;

    @Mock
    INavigator navigator;

    @Mock
    IAllUsersScreen view;

    @Mock
    CurrentUser currentUser;

    private AllUsersPresenter presenter;
    private UsersCollection usersCollection;
    private FindUsersResponse findUsersResponse;
    private List<FindUsersResponse> usersResponses = new ArrayList<>();
    private final int userID = 1;

    PublishSubject refreshUsersEvent = PublishSubject.create();
    PublishSubject userSelectedEvent = PublishSubject.create();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        presenter = new AllUsersPresenter(view, navigator, Schedulers.immediate(), apiService, currentUser);

        findUsersResponse = new FoundUserBuilder().setEvents(1).setFriends(1).setLikes(1)
                .setName("Name").setSurname("Surname").setUserId(userID).setRegisterDate("date").build();
        usersResponses.add(findUsersResponse);
        usersCollection = new UsersCollectionBuilder().setUsers(usersResponses).build();

        when(view.refreshUsers()).thenReturn(refreshUsersEvent);
        when(view.selectedUser()).thenReturn(userSelectedEvent);
        when(apiService.getAllUsers()).thenReturn(Observable.just(usersCollection));
        when(currentUser.userId()).thenReturn(userID + 1);

        presenter.start();
    }

    @Test
    public void allUsersPresenter_loadUsers_loadSucceded() throws Exception
    {
        verify(view).addUsersList(usersResponses);
    }

    @Test
    public void allUsersPresenter_loadUsers_loadFailed() throws Exception
    {
        Mockito.reset(view);
        when(apiService.getAllUsers()).thenReturn(Observable.error(new Throwable()));
        verify(view, never()).addUsersList(any());
    }

    @Test
    public void allUsersPresenter_refreshUsers_loadUsers()
    {
        Mockito.reset(view);

        refreshUsersEvent.onNext(this);
        Mockito.verify(view, times(2)).refreshingVisible(false);
    }

    @Test
    public void allUsersPresenter_selectUser_openUserDetails()
    {
        userSelectedEvent.onNext(findUsersResponse);
        Mockito.verify(navigator).openUserDetailsScreen(findUsersResponse.getUserId());
    }

    @Test
    public void allUsersPresenter_selectUser_openMyProfileScreen()
    {
        when(currentUser.userId()).thenReturn(userID);
        userSelectedEvent.onNext(findUsersResponse);
        Mockito.verify(navigator).openMyProfileScreen();
    }

    @After
    public void tearDown() throws Exception
    {
        presenter = null;
    }

}