package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyEventsPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.android.schedulers.AndroidSchedulers;

public class MyEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new MyEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    public void onStart()
    {
        super.onStart();

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
}
