package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.DeclinedUsersPresenter;
import com.activity_sync.presentation.presenters.UsersFragmentBasePresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantsBasePresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IUsersFragmentView view;

    @Mock
    IApiService apiService;

    protected PublishSubject participantSelectedEvent = PublishSubject.create();
    protected PublishSubject refreshParticipantsEvent = PublishSubject.create();

    protected User testedParticipant;
    protected int eventId = 1;

    @Before
    public void setup()
    {
        testedParticipant = new UserBuilder()
                .setUserId(12)
                .setName("Marcin")
                .setSurname("Zielinski")
                .setCredibility(85)
                .createUser();

        Mockito.when(view.selectedUser()).thenReturn(participantSelectedEvent);
        Mockito.when(view.refreshUsers()).thenReturn(refreshParticipantsEvent);
    }

    @Test
    public void participantsBasePresenter_selectParticipant_openUserDetailsScreen()
    {
        UsersFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        participantSelectedEvent.onNext(testedParticipant);
        Mockito.verify(navigator).openUserDetailsScreen(12);
    }

    @Test
    public void participantsBasePresenter_refreshList_reloadParticipants()
    {
        UsersFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        refreshParticipantsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private UsersFragmentBasePresenter createPresenter()
    {
        return new DeclinedUsersPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
