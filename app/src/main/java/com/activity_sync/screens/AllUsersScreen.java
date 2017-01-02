package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.AllUsersPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IAllUsersScreen;

import butterknife.Bind;

public class AllUsersScreen extends Screen implements IAllUsersScreen
{
    @Bind(R.id.users_list)
    RecyclerView usersList;

    @Bind(R.id.users_refresh)
    SwipeRefreshLayout usersRefresh;

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new AllUsersPresenter(this);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    public AllUsersScreen()
    {
        super(R.layout.all_users_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_all_users);
    }
}
