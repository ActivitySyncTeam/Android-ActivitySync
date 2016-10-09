package com.activity_sync.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.activity_sync.R;

public class ActivitySyncRecyclerView extends RecyclerView
{
    public ActivitySyncRecyclerView(Context context)
    {
        super(context);
    }

    public ActivitySyncRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ActivitySyncRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Nullable
    private View emptyView;

    @NonNull
    private final AdapterDataObserver observer = new AdapterDataObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter)
    {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null)
        {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null)
        {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void bindSwipeToRefreshLayout(SwipeRefreshLayout swipeRefreshLayout)
    {
        addOnScrollListener(new SwipeRefreshLayoutScrollListener(swipeRefreshLayout));
    }

    public void setEmptyView(@Nullable View emptyView)
    {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    private void checkIfEmpty()
    {
        if (emptyView != null && getAdapter() != null)
        {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(showEmptyView ? VISIBLE : GONE);

        }
        else if (emptyView == null && getAdapter() != null)
        {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            if (showEmptyView == false)
            {
                this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backgroundColor));
            }
            else
            {
                this.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            }
        }
    }
}