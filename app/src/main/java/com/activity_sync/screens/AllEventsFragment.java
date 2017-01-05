package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.activity_sync.presentation.events.LocationChangeEvent;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.android.schedulers.AndroidSchedulers;

public class AllEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new AllEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService, permanentStorage);
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);

        eventsList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (!recyclerView.canScrollVertically(1))
                {
                    pageDownReached.onNext(this);
                }
            }
        });
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
        Toast.makeText(getContext(),
                String.format("Location changed: %f %f", event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                Toast.LENGTH_LONG)
                .show();

        locationFound.onNext(event.getLocation());
    }
}
