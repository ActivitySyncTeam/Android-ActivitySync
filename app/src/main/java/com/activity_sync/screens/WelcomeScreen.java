package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.Button;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.WelcomePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IWelcomeView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class WelcomeScreen extends Screen implements IWelcomeView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.register_btn)
    Button registerBtn;

    public WelcomeScreen()
    {
        super(R.layout.welcome_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new WelcomePresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    public Observable loginBtnClick()
    {
        return ViewObservable.clicks(loginBtn);
    }

    @Override
    public Observable registerBtnClick()
    {
        return ViewObservable.clicks(registerBtn);
    }
}
