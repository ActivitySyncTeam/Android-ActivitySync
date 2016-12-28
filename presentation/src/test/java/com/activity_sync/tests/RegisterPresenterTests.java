package com.activity_sync.tests;

import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.builders.ClientDetailsBuilder;
import com.activity_sync.presentation.models.builders.LoginResponseBuilder;
import com.activity_sync.presentation.models.builders.RegisterResponseBuilder;
import com.activity_sync.presentation.presenters.RegisterPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IRegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
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

    @Mock
    IApiService apiService;

    @Mock
    CurrentUser currentUser;

    PublishSubject registerBtnClickEvent = PublishSubject.create();
    PublishSubject alreadyRegisteredClickEvent = PublishSubject.create();

    String firstName = "First name";
    String lastName = "Last name";
    String email = "Email";
    String nickName = "Nick name";
    String password = "Password";
    String emptyFieldError = "Error";

    RegisterResponse registerResponse;
    ClientDetails clientDetails;
    LoginResponse loginResponse;

    @Before
    public void setup()
    {
        registerResponse = new RegisterResponseBuilder().create();
        clientDetails = new ClientDetailsBuilder().create();
        loginResponse = new LoginResponseBuilder().create();

        Mockito.when(view.registerBtnClick()).thenReturn(registerBtnClickEvent);
        Mockito.when(view.alreadyRegisteredClick()).thenReturn(alreadyRegisteredClickEvent);
        Mockito.when(view.emptyFieldErrorText()).thenReturn(emptyFieldError);
        Mockito.when(view.firstName()).thenReturn(firstName);
        Mockito.when(view.lastName()).thenReturn(lastName);
        Mockito.when(view.email()).thenReturn(email);
        Mockito.when(view.userName()).thenReturn(nickName);
        Mockito.when(view.password()).thenReturn(password);
        Mockito.when(apiService.register(view.userName(), view.password(), view.firstName(), view.lastName(), view.email())).thenReturn(Observable.just(registerResponse));
        Mockito.when(apiService.getClientDetails()).thenReturn(Observable.just(clientDetails));
        Mockito.when(apiService.login(view.userName(), view.password())).thenReturn(Observable.just(loginResponse));
    }

    @Test
    public void registerPresenter_clickRegisterBtn_openEventsScreen()
    {
        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(currentUser).clientId(anyString());
        Mockito.verify(currentUser).clientSecret(anyString());
        Mockito.verify(currentUser).accessToken(anyString());
        Mockito.verify(navigator).openEventsScreen();
        Mockito.verify(navigator).startBackgroundService();
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
    public void registerPresenter_clickRegisterBtn_firstNameEmptyError()
    {
        Mockito.when(view.firstName()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).firstNameErrorEnabled(true);
        Mockito.verify(view).firstNameErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_lastNameEmptyError()
    {
        Mockito.when(view.lastName()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).lastNameErrorEnabled(true);
        Mockito.verify(view).lastNameErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_emailEmptyError()
    {
        Mockito.when(view.email()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).emailErrorEnabled(true);
        Mockito.verify(view).emailErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_nickNameEmptyError()
    {
        Mockito.when(view.userName()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).userNameErrorEnabled(true);
        Mockito.verify(view).userNameErrorText(anyString());
    }

    @Test
    public void registerPresenter_clickRegisterBtn_passwordEmptyError()
    {
        Mockito.when(view.password()).thenReturn(StringUtils.EMPTY);

        RegisterPresenter registerPresenter = createPresenter();
        registerPresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openEventsScreen();
        Mockito.verify(view).passwordErrorEnabled(true);
        Mockito.verify(view).passwordErrorText(anyString());
    }

    private RegisterPresenter createPresenter()
    {
        return new RegisterPresenter(Schedulers.immediate(), view, navigator, apiService, currentUser);
    }
}
