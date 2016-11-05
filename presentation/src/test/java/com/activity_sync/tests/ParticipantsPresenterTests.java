package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.presenters.ParticipantsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IParticipantsView view;

    PublishSubject participantSelectedEvent = PublishSubject.create();
    PublishSubject refreshParticipantsEvent = PublishSubject.create();

    User testedParticipant;

    @Before
    public void setup()
    {
        testedParticipant = new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Marcin")
                        .setlastName("Zielinski")
                        .createUserDetails())
                .setCredibility(85)
                .createUser();

        Mockito.when(view.selectedUser()).thenReturn(participantSelectedEvent);
        Mockito.when(view.refreshParticipants()).thenReturn(refreshParticipantsEvent);
    }

    @Test
    public void participantsPresenter_selectParticipant_openUserDetailsScreen()
    {
        ParticipantsPresenter presenter = createPresenter();
        presenter.start();

        participantSelectedEvent.onNext(testedParticipant);
        Mockito.verify(navigator).openUserDetailsScreen(1);
    }

    @Test
    public void participantsPresenter_refreshList_reloadParticipants()
    {
        ParticipantsPresenter presenter = createPresenter();
        presenter.start();

        refreshParticipantsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private ParticipantsPresenter createPresenter()
    {
        return new ParticipantsPresenter(view, navigator, Schedulers.immediate());
    }
}
