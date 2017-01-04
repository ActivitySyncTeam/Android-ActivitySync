package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.UserID;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.EditAccountPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEditAccountView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EditAccountPresenterTest
{
    @Mock
    IEditAccountView view;

    @Mock
    IApiService apiService;

    private EditAccountPresenter editAccountPresenter;
    private PublishSubject saveBtnClickEvent = PublishSubject.create();
    private UserID userID;
    private String firstName = "First name";
    private String lastName = "Last name";
    private String email = "Email";
    private String signature = "asd";
    private String emptyFieldError = "Error";

    User user;

    @Before
    public void setUp() throws Exception
    {
        user = new UserBuilder().createUser();

        MockitoAnnotations.initMocks(this);
        editAccountPresenter = new EditAccountPresenter(view, Schedulers.immediate(), apiService, user);
        userID = new UserID(1);

        when(view.onSaveClick()).thenReturn(saveBtnClickEvent);
        when(view.getFirstName()).thenReturn(firstName);
        when(view.getLastName()).thenReturn(lastName);
        when(view.getEmail()).thenReturn(email);
        when(view.getSignature()).thenReturn(signature);
        when(view.emptyFieldErrorText()).thenReturn(emptyFieldError);

        editAccountPresenter.start();
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_accountEdited()
    {
        when(apiService.updateUser(anyString(), anyString(), anyString(), anyString())).thenReturn(Observable.just(userID));

        saveBtnClickEvent.onNext(this);
        verify(view).close();
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_accountNotEdited()
    {
        when(apiService.updateUser(anyString(), anyString(), anyString(), anyString())).thenReturn(Observable.error(new Throwable()));

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_firstNameEmptyError()
    {
        when(view.getFirstName()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).firstNameErrorEnabled(true);
        verify(view).firstNameErrorText(anyString());
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_lastNameEmptyError()
    {
        when(view.getLastName()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).lastNameErrorEnabled(true);
        verify(view).lastNameErrorText(anyString());
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_emailEmptyError()
    {
        when(view.getEmail()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).emailErrorEnabled(true);
        verify(view).emailErrorText(anyString());
    }

    @After
    public void tearDown() throws Exception
    {
        editAccountPresenter = null;
    }

}