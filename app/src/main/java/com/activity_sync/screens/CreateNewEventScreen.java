package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.EventCreatorPresenter;
import com.activity_sync.presentation.presenters.IPresenter;

import rx.android.schedulers.AndroidSchedulers;

public class CreateNewEventScreen extends EventEditorScreenBase
{
    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventCreatorPresenter(AndroidSchedulers.mainThread(), this, navigator, apiService, errorHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_event_creator));
    }
}
