package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.IntroBasePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IIntroBaseView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class IntroBasePresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IIntroBaseView view;

    @Mock
    IPermanentStorage permanentStorage;

    PublishSubject skipButtonClickEvent = PublishSubject.create();
    PublishSubject doneButtonClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.skipButtonClicked()).thenReturn(skipButtonClickEvent);
        Mockito.when(view.doneButtonClicked()).thenReturn(doneButtonClickEvent);
    }

    @Test
    public void introBasePresenter_init_updatePermanentStorage()
    {
        IntroBasePresenter introBasePresenter = createPresenter();
        introBasePresenter.start();

        Mockito.verify(permanentStorage).saveBoolean(IPermanentStorage.IS_APP_OPENED_BEFORE, true);
    }

    @Test
    public void introBasePresenter_clickSkipBtn_openLoginScreen()
    {
        IntroBasePresenter introBasePresenter = createPresenter();
        introBasePresenter.start();

        skipButtonClickEvent.onNext(this);
        Mockito.verify(navigator).openLoginScreen();
    }

    @Test
    public void introBasePresenter_clickDoneBtn_openDummyScreen()
    {
        IntroBasePresenter introBasePresenter = createPresenter();
        introBasePresenter.start();

        doneButtonClickEvent.onNext(this);
        Mockito.verify(navigator).openLoginScreen();
    }

    private IntroBasePresenter createPresenter()
    {
        return new IntroBasePresenter(Schedulers.immediate(), view, navigator, permanentStorage);
    }
}