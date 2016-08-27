package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.DummyPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IDummyView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class DummyPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IDummyView view;

    PublishSubject openDummyScreenEvent = PublishSubject.create();
    PublishSubject displayMessageEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.openDummyScreenClick()).thenReturn(openDummyScreenEvent);
        Mockito.when(view.displayMessageClick()).thenReturn(displayMessageEvent);
    }

    @Test
    public void dummyPresenter_clickDummyBtn_openDummyScreen()
    {
        DummyPresenter dummyPresenter = createPresenter();
        dummyPresenter.start();

        openDummyScreenEvent.onNext(this);
        Mockito.verify(navigator).openDummyScreen();
    }

    @Test
    public void dummyPresenter_clickDisplayMessageBtn_displayMessage()
    {
        DummyPresenter dummyPresenter = createPresenter();
        dummyPresenter.start();

        displayMessageEvent.onNext(this);
        Mockito.verify(view).displayMessage();
    }

    private DummyPresenter createPresenter()
    {
        return new DummyPresenter(Schedulers.immediate(), view, navigator);
    }
}
