package com.activity_sync.screens;


import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.EventUpdatePresenter;
import com.activity_sync.presentation.presenters.IPresenter;

import rx.android.schedulers.AndroidSchedulers;

public class EventUpdateScreen extends EventEditorScreenBase
{
    public static final String EVENT_ID = "event_id";

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        int eventID = getIntent().getIntExtra(EventUpdateScreen.EVENT_ID, 0);
        return new EventUpdatePresenter(AndroidSchedulers.mainThread(), this, navigator, apiService, eventID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_event_update));
    }
}
