package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.EditAccountPresenter;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEditAccountView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private EditAccountPresenter editAccountPresenter;
    private PublishSubject saveBtnClickEvent = PublishSubject.create();

    private String firstName = "First name";
    private String lastName = "Last name";
    private String email = "Email";
    private String password = "Password";
    private String emptyFieldError = "Error";


    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        editAccountPresenter = new EditAccountPresenter(view, Schedulers.immediate());

        when(view.onSaveClick()).thenReturn(saveBtnClickEvent);
        when(view.getFirstName()).thenReturn(firstName);
        when(view.getLastName()).thenReturn(lastName);
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(view.emptyFieldErrorText()).thenReturn(emptyFieldError);

        editAccountPresenter.start();
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_closeEditAccountScreen()
    {
        saveBtnClickEvent.onNext(this);
        verify(view).close();
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

    @Test
    public void editAccountPresenter_clickSaveBtn_passwordEmptyError()
    {
        when(view.getPassword()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).passwordErrorEnabled(true);
        verify(view).passwordErrorText(anyString());
    }


    @After
    public void tearDown() throws Exception
    {
        editAccountPresenter = null;
    }

}