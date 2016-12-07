package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.DeclinedParticipantsPresenter;
import com.activity_sync.presentation.presenters.ParticipantsFragmentBasePresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsFragmentView;

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
    IParticipantsFragmentView view;

    @Mock
    IApiService apiService;

    protected PublishSubject participantSelectedEvent = PublishSubject.create();
    protected PublishSubject refreshParticipantsEvent = PublishSubject.create();

    protected User testedParticipant;

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

        Mockito.when(view.selectedParticipant()).thenReturn(participantSelectedEvent);
        Mockito.when(view.refreshParticipants()).thenReturn(refreshParticipantsEvent);
    }

    @Test
    public void participantsBasePresenter_selectParticipant_openUserDetailsScreen()
    {
        ParticipantsFragmentBasePresenter presenter = createPresenter(true);
        presenter.start();

        participantSelectedEvent.onNext(testedParticipant);
        Mockito.verify(navigator).openUserDetailsScreen(0);
    }

    @Test
    public void participantsBasePresenter_refreshList_reloadParticipants()
    {
        ParticipantsFragmentBasePresenter presenter = createPresenter(true);
        presenter.start();

        refreshParticipantsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private ParticipantsFragmentBasePresenter createPresenter(boolean isOrganizer)
    {
        return new DeclinedParticipantsPresenter(view, navigator, Schedulers.immediate(), isOrganizer, apiService);
    }
}
