package com.activity_sync.widgets;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

public class SwipeRefreshLayoutScrollListener extends RecyclerView.OnScrollListener
{
    private SwipeRefreshLayout swipeLayout;

    public SwipeRefreshLayoutScrollListener(SwipeRefreshLayout swipeLayout)
    {
        this.swipeLayout = swipeLayout;
    }
}
