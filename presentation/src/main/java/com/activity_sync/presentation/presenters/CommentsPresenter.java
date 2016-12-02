package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.views.ICommentsView;

import rx.Scheduler;

public class CommentsPresenter extends Presenter<ICommentsView>
{
    private final Scheduler uiThread;

    public CommentsPresenter(ICommentsView view, Scheduler uiThread)
    {
        super(view);
        this.uiThread = uiThread;
    }
}
