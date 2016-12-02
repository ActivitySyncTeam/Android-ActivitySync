package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.CommentsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.ICommentsView;

import rx.android.schedulers.AndroidSchedulers;

public class CommentsScreen extends Screen implements ICommentsView
{
    public static final String EVENT_ID = "event_id";

    public CommentsScreen()
    {
        super(R.layout.comments_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new CommentsPresenter(this, AndroidSchedulers.mainThread());
    }
}
