package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.Toast;

import com.activity_sync.events.LocationChangeEvent;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyEventsPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.android.schedulers.AndroidSchedulers;

public class MyEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState) {
        return new MyEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationChangeEvent event)
    {
        Toast.makeText(getContext(), "test location change", Toast.LENGTH_LONG).show();
    }
}
