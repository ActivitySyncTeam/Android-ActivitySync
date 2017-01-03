package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.FindUsersResponse;
import com.activity_sync.presentation.presenters.AllUsersPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IAllUsersScreen;
import com.activity_sync.renderers.UsersRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;
import com.activity_sync.services.Navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

public class AllUsersScreen extends Screen implements IAllUsersScreen
{
    @Inject
    IApiService apiService;

    @Bind(R.id.users_list)
    RecyclerView usersList;

    @Bind(R.id.users_refresh)
    SwipeRefreshLayout usersRefresh;

    private PublishSubject refreshUsers = PublishSubject.create();
    private RVRendererAdapter<FindUsersResponse> adapter;
    private List<FindUsersResponse> users = new ArrayList<>();

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new AllUsersPresenter(this, new Navigator(this), AndroidSchedulers.mainThread(), apiService);
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
        usersRefresh.setOnRefreshListener(() -> refreshUsers.onNext(this));
        adapter = new RVRendererAdapter<>(this, new UsersRenderer.Builder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        usersList.setLayoutManager(linearLayoutManager);
        usersList.addItemDecoration(new DividerItemDecoration(this));
        usersList.setAdapter(adapter);
    }

    @Override
    public Observable refreshUsers()
    {
        return refreshUsers;
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        usersRefresh.post(() -> usersRefresh.setRefreshing(isRefreshing));
    }

    @Override
    public void addUsersList(Collection<FindUsersResponse> users)
    {
        adapter.clear();
        this.users.addAll(users);
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
