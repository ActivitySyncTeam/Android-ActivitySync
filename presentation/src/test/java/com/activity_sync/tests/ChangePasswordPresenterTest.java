package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.ChangePasswordPresenter;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IChangePasswordView;

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

public class ChangePasswordPresenterTest
{
    @Mock
    IChangePasswordView view;

    private ChangePasswordPresenter changePasswordPresenter;
    private PublishSubject saveBtnClickEvent = PublishSubject.create();

    private String oldPassword = "Old Password";
    private String newPassword = "New Password";
    private String confirmedNewPassword = "New Password";
    private String emptyFieldError = "Error";
    private String passwordsNotMatchingError = "Error 1";
    private String samePasswordsError = "Error 2";

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        changePasswordPresenter = new ChangePasswordPresenter(view, Schedulers.immediate());

        when(view.onSaveClick()).thenReturn(saveBtnClickEvent);
        when(view.getOldPassword()).thenReturn(oldPassword);
        when(view.getNewPassword()).thenReturn(newPassword);
        when(view.getConfirmNewPassword()).thenReturn(confirmedNewPassword);
        when(view.emptyFieldErrorText()).thenReturn(emptyFieldError);
        when(view.confirmedNotMatchingErrorText()).thenReturn(passwordsNotMatchingError);
        when(view.samePasswordsErrorText()).thenReturn(samePasswordsError);

        changePasswordPresenter.start();
    }

    @Test
    public void editAccountPresenter_clickSaveBtn_closeChangePasswordScreen()
    {
        saveBtnClickEvent.onNext(this);
        verify(view).close();
    }

    @Test
    public void changePasswordPresenter_clickSaveBtn_oldPasswordEmptyError()
    {
        when(view.getOldPassword()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).oldPasswordErrorEnabled(true);
        verify(view).oldPasswordErrorText(anyString());
    }

    @Test
    public void changePasswordPresenter_clickSaveBtn_newPasswordEmptyError()
    {
        when(view.getNewPassword()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).newPasswordErrorEnabled(true);
        verify(view).newPasswordErrorText(anyString());
    }

    @Test
    public void changePasswordPresenter_clickSaveBtn_confirmedNewPasswordEmptyError()
    {
        when(view.getConfirmNewPassword()).thenReturn(StringUtils.EMPTY);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).confirmNewPasswordErrorEnabled(true);
        verify(view).confirmNewPasswordErrorText(anyString());
    }

    @Test
    public void changePasswordPresenter_clickSaveBtn_newPasswordMatchOldPasswordError()
    {
        when(view.getNewPassword()).thenReturn(oldPassword);

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).confirmNewPasswordErrorEnabled(true);
        verify(view).confirmNewPasswordErrorText(anyString());
    }

    @Test
    public void changePasswordPresenter_clickSaveBtn_confirmedNewPasswordNotMatchingError()
    {
        when(view.getNewPassword()).thenReturn("pwd");
        when(view.getConfirmNewPassword()).thenReturn("pwd1");

        saveBtnClickEvent.onNext(this);
        verify(view, never()).close();
        verify(view).confirmNewPasswordErrorEnabled(true);
        verify(view).confirmNewPasswordErrorText(anyString());
    }

    @After
    public void tearDown() throws Exception
    {
        changePasswordPresenter = null;
    }

}