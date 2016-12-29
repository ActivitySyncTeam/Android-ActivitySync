package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.LoginPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ILoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    ILoginView view;

    @Mock
    IApiService apiService;

    @Mock
    CurrentUser currentUser;

    PublishSubject loginBtnClickEvent = PublishSubject.create();
    PublishSubject createAccountClickEvent = PublishSubject.create();

    String login = "email";
    String password = "password";

    @Before
    public void setup()
    {
        Mockito.when(view.loginBtnClick()).thenReturn(loginBtnClickEvent);
        Mockito.when(view.createAccountClick()).thenReturn(createAccountClickEvent);
        Mockito.when(view.login()).thenReturn(login);
        Mockito.when(view.password()).thenReturn(password);
    }

    @Test
    public void loginPresenter_clickLoginBtn_openEventsScreen()
    {
        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        loginBtnClickEvent.onNext(this);
        Mockito.verify(navigator).startBackgroundService();
        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void loginPresenter_clickRegisterBtn_openRegisterScreen()
    {
        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        createAccountClickEvent.onNext(this);
        Mockito.verify(navigator).openRegisterScreen();
    }

    @Test
    public void loginPresenter_clickRegisterBtn_loginEmptyError()
    {
        Mockito.when(view.login()).thenReturn(StringUtils.EMPTY);

        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        loginBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).loginErrorEnabled(true);
        Mockito.verify(view).loginErrorText(anyString());
    }

    @Test
    public void loginPresenter_clickRegisterBtn_passwordEmptyError()
    {
        Mockito.when(view.password()).thenReturn(StringUtils.EMPTY);

        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        loginBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).passwordErrorEnabled(true);
        Mockito.verify(view).passwordErrorText(anyString());
    }

    private LoginPresenter createPresenter()
    {
        return new LoginPresenter(Schedulers.immediate(), view, navigator, apiService, currentUser);
    }
}
