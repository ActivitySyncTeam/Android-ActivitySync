package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.RegisterPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IRegisterView;

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
public class RegisterPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IRegisterView view;

    PublishSubject regsiterBtnClickEvent = PublishSubject.create();
    PublishSubject alreadyRegisteredClickEvent = PublishSubject.create();

    String fullName = "Full name";
    String login = "login";
    String password = "password";

    @Before
    public void setup()
    {
        Mockito.when(view.registerBtnClick()).thenReturn(regsiterBtnClickEvent);
        Mockito.when(view.alreadyRegisteredClick()).thenReturn(alreadyRegisteredClickEvent);
        Mockito.when(view.fullName()).thenReturn(fullName);
        Mockito.when(view.login()).thenReturn(login);
        Mockito.when(view.password()).thenReturn(password);
    }

    @Test
    public void registerPresenter_clickLoginBtn_openEventsScreen()
    {
        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        regsiterBtnClickEvent.onNext(this);
        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void registerPresenter_clickAlreadyRegisteredView_openLoginScreen()
    {
        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        alreadyRegisteredClickEvent.onNext(this);
        Mockito.verify(navigator).openLoginScreen();
    }

    @Test
    public void registerPresenter_clickRegisterBtn_fullNameEmptyError()
    {
        Mockito.when(view.fullName()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        regsiterBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).fullNameErrorEnabled(true);
        Mockito.verify(view).fullNameErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_loginEmptyError()
    {
        Mockito.when(view.login()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        regsiterBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).loginErrorEnabled(true);
        Mockito.verify(view).loginErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_passwordEmptyError()
    {
        Mockito.when(view.password()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        regsiterBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).passwordErrorEnabled(true);
        Mockito.verify(view).passwordErrorText(anyString());
    }

    private RegisterPresenter createPresenter()
    {
        return new RegisterPresenter(Schedulers.immediate(), view, navigator);
    }
}
