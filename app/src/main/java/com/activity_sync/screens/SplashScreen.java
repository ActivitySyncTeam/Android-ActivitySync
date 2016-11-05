package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.activity_sync.App;
import com.activity_sync.presentation.presenters.SplashPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ISplashView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class SplashScreen extends AppCompatActivity implements ISplashView
{
    private SplashPresenter presenter;

    @Inject
    INavigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this, AndroidSchedulers.mainThread(), navigator);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        presenter.start();
    }

    @Override
    public void finishSplashScreen()
    {
        finish();
    }
}
