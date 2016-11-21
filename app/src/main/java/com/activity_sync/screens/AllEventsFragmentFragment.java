package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.android.schedulers.AndroidSchedulers;

public class AllEventsFragmentFragment extends EventsFragmentFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new AllEventsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }

    @Override
    protected int getFragmentToolbarId()
    {
        return R.layout.all_events_search_toolbar;
    }
}
