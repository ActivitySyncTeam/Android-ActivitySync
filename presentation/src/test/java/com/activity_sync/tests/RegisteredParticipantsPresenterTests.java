package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.RegisteredUsersPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredParticipantsPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    @Override
    public void setup()
    {
        super.setup();

        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);

        Mockito.when(apiService.removeParticipant(any())).thenReturn(Observable.just(participants));
    }

    @Test
    public void registeredParticipantsPresenter_removeClick_openRemoveConfDialog()
    {
        RegisteredUsersPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedUser);
        Mockito.verify(view).openRemoveConfirmationDialog(testedUser);
    }

    @Test
    public void registeredParticipantsPresenter_removeAccept_refresh()
    {
        RegisteredUsersPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).removeParticipant(any());
        Mockito.verify(view).removeSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    private RegisteredUsersPresenter createPresenter()
    {
        return new RegisteredUsersPresenter(view, navigator, Schedulers.immediate(), apiService, eventId);
    }
}
