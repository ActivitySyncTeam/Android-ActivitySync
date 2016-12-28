package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyProfilePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IMyProfileView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class MyProfileScreen extends UserBaseScreen implements IMyProfileView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.edit_account_btn)
    Button editAccountButton;

    @Bind(R.id.change_password_btn)
    Button changePasswordButton;

    public MyProfileScreen()
    {
        super(R.layout.my_profile_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_my_profile);
        hideThumbs();
    }

    private void hideThumbs()
    {
        thumbDown.setVisibility(View.GONE);
        thumbUp.setVisibility(View.GONE);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new MyProfilePresenter(this, navigator, AndroidSchedulers.mainThread(), currentUser);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public Observable editUserAccount()
    {
        return ViewObservable.clicks(editAccountButton);
    }

    @Override
    public Observable changeUserPassword()
    {
        return ViewObservable.clicks(changePasswordButton);
    }
}
