package com.activity_sync.screens;


import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.presenters.EventUpdatePresenter;
import com.activity_sync.presentation.presenters.IPresenter;

import rx.android.schedulers.AndroidSchedulers;

public class EventUpdateScreen extends EventEditorScreenBase
{
    public static final String EVENT = "event";

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        Event event = (Event) getIntent().getSerializableExtra(EventUpdateScreen.EVENT);
        return new EventUpdatePresenter(AndroidSchedulers.mainThread(), this, navigator, apiService, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_event_update));
    }
}
