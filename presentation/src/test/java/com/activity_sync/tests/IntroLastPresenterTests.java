package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.IntroLastPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IIntroLastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;


@RunWith(MockitoJUnitRunner.class)
public class IntroLastPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IIntroLastView view;

    PublishSubject checkImageClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.checkImageClick()).thenReturn(checkImageClickEvent);
    }

    @Test
    public void introBasePresenter_clickSkipBtn_openDummyScreen()
    {
        IntroLastPresenter introLastPresenter = createPresenter();
        introLastPresenter.start();

        checkImageClickEvent.onNext(this);
        Mockito.verify(navigator).openDummyScreen();
    }

    private IntroLastPresenter createPresenter()
    {
        return new IntroLastPresenter(Schedulers.immediate(), view, navigator);
    }
}
