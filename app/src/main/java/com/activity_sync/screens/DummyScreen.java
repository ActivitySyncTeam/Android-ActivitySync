package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.DummyPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IDummyView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class DummyScreen extends ScreenWithMenu implements IDummyView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.dummy_message_btn)
    Button displayMessageBtn;

    @Bind(R.id.dummy_open_intro_screen_btn)
    Button openIntroBtn;

    @Bind(R.id.dummy_show_progress_btn)
    Button showProgressButton;

    @Bind(R.id.dummy_hide_progress_btn)
    Button hideProgressButton;

    public DummyScreen()
    {
        super(R.layout.dummy_screen);
    }

    @Override
    protected int getMenuId()
    {
        return R.id.menu_dummy;
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new DummyPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_dummy_screen));
    }

    @Override
    public Observable displayMessageClick()
    {
        return ViewObservable.clicks(displayMessageBtn);
    }

    @Override
    public Observable openIntroScreenClick()
    {
        return ViewObservable.clicks(openIntroBtn);
    }

    @Override
    public void displayMessage()
    {
        Toast.makeText(this, R.string.txt_hello_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Observable showProgressClick()
    {
        return ViewObservable.clicks(showProgressButton);
    }

    @Override
    public Observable hideProgressClick()
    {
        return ViewObservable.clicks(hideProgressButton);
    }
}
