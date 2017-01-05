package com.activity_sync.presentation.presenters;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class Presenter<TView> implements IPresenter
{
    public final TView view;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();
    private final List<Action1> startupActions = new ArrayList<>();
    protected IPresenter childPresenter;

    private boolean started = false;

    public Presenter(TView view)
    {
        this.view = view;
    }

    @Override
    public void start()
    {
        this.started = true;

        if (this.childPresenter != null)
        {
            this.childPresenter.start();
        }

        for (Action1 action : this.startupActions)
        {
            action.call(null);
        }
    }

    @Override
    public void stop()
    {
        if (this.childPresenter != null)
        {
            this.childPresenter.stop();
        }

        this.started = false;
        this.subscriptions.clear();
    }

    @Override
    public void resume()
    {
        if (this.childPresenter != null)
        {
            this.childPresenter.resume();
        }
    }

    protected void runOnStartup(Action1 action)
    {
        if (this.started)
        {
            action.call(null);
        }
        else
        {
            this.startupActions.add(action);
        }
    }
}
