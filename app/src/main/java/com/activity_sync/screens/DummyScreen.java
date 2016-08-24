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

public class DummyScreen extends Screen implements IDummyView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.dummy_message_btn)
    Button displayMessageBtn;

    @Bind(R.id.dummy_open_screen_btn)
    Button openDummyBtn;

    public DummyScreen()
    {
        super(R.layout.dummy_screen);
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
    public Observable openDummyScreenClick()
    {
        return ViewObservable.clicks(openDummyBtn);
    }

    @Override
    public void displayMessage()
    {
        Toast.makeText(this, R.string.txt_hello_message, Toast.LENGTH_LONG).show();
    }
}
