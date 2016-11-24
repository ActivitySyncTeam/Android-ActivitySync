package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IntroBasePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IIntroBaseView;
import com.github.paolorotolo.appintro.AppIntro2;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

public class IntroScreen extends AppIntro2 implements IIntroBaseView
{
    @Inject
    INavigator navigator;

    @Inject
    IPermanentStorage permanentStorage;

    private IntroBasePresenter presenter;
    private PublishSubject skipButtonClicked = PublishSubject.create();
    private PublishSubject doneButtonClicked = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        super.onCreate(savedInstanceState);

        addSlide(new IntroHappyScreen());
        addSlide(new IntroEasyOrganizingScreen());
        addSlide(new IntroFriendsScreen());
        addSlide(new IntroLocationScreen());
        addSlide(new IntroLastScreen());

        setColorTransitionsEnabled(true);
        setIndicatorColor(ContextCompat.getColor(this, R.color.intro_indicator_selected),
                ContextCompat.getColor(this, R.color.intro_indicator_unselected));

        presenter = new IntroBasePresenter(AndroidSchedulers.mainThread(), this, navigator, permanentStorage);
    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        doneButtonClicked.onNext(this);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        skipButtonClicked.onNext(this);
    }

    @Override
    public Observable skipButtonClicked()
    {
        return skipButtonClicked;
    }

    @Override
    public Observable doneButtonClicked()
    {
        return doneButtonClicked;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        presenter.stop();
    }
}
