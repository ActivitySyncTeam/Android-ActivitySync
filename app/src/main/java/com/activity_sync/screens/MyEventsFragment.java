package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.presenters.EventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IEventsView;

import rx.android.schedulers.AndroidSchedulers;

public class MyEventsFragment extends EventsFragmentBase implements IEventsView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState) {
        return new EventsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }
}
