package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.LoginPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ILoginView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class LoginScreen extends Screen implements ILoginView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.create_account)
    TextView createAccountTv;

    public LoginScreen()
    {
        super(R.layout.login_screen);
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
        return new LoginPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    public Observable loginBtnClick()
    {
        return ViewObservable.clicks(loginBtn);
    }

    @Override
    public Observable createAccountClick()
    {
        return ViewObservable.clicks(createAccountTv);
    }
}
