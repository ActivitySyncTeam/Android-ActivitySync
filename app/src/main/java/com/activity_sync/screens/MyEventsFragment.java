package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyEventsPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.android.schedulers.AndroidSchedulers;

public class MyEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState) {
        return new MyEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    public int getIndex()
    {
        return 1;
    }
}
