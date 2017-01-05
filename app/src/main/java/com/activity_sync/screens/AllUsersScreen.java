package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.AllUsersPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.CurrentUser;
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

    @Inject
    CurrentUser currentUser;

    @Bind(R.id.users_list)
    RecyclerView usersList;

    @Bind(R.id.users_refresh)
    SwipeRefreshLayout usersRefresh;

    @Bind(R.id.user_filter)
    AppCompatEditText userFilter;

    private PublishSubject endListReached = PublishSubject.create();

    private PublishSubject refreshUsers = PublishSubject.create();
    private PublishSubject filterUsers = PublishSubject.create();
    private RVRendererAdapter<User> adapter;
    private List<User> users = new ArrayList<>();
    private static final int TRIGGER_SEARCH = 1;
    private static final int SEARCH_TRIGGER_DELAY_IN_MS = 1000;
    private static Handler handler;

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new AllUsersPresenter(this, new Navigator(this), AndroidSchedulers.mainThread(), apiService, currentUser);
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
        initListeners();
        adapter = new RVRendererAdapter<>(this, new UsersRenderer.Builder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        usersList.setLayoutManager(linearLayoutManager);
        usersList.addItemDecoration(new DividerItemDecoration(this));
        usersList.setAdapter(adapter);

        usersList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (!recyclerView.canScrollVertically(1))
                {
                    endListReached.onNext(this);
                }
            }
        });
    }

    private void initListeners()
    {
        usersRefresh.setOnRefreshListener(() -> refreshUsers.onNext(this));
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if (msg.what == TRIGGER_SEARCH)
                {
                    filterUsers.onNext(this);
                }
            }
        };
        userFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                handler.removeMessages(TRIGGER_SEARCH);
                handler.sendEmptyMessageDelayed(TRIGGER_SEARCH, SEARCH_TRIGGER_DELAY_IN_MS);
            }
        });
    }

    @Override
    public Observable refreshUsers()
    {
        return refreshUsers;
    }

    @Override
    public Observable filterUsers()
    {
        return filterUsers;
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        usersRefresh.post(() -> usersRefresh.setRefreshing(isRefreshing));
    }

    @Override
    public void addUsersListAndClear(Collection<User> users)
    {
        adapter.clear();
        this.users.addAll(users);
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addUsersListAtTheEnd(Collection<User> users)
    {
        this.users.addAll(users);
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Observable<User> selectedUser()
    {
        return adapter.itemSelected();
    }

    @Override
    public String getUserFilterText()
    {
        return userFilter.getText().toString();
    }

    @Override
    public void showProgress()
    {
        showProgressBar();
    }

    @Override
    public void hideProgress()
    {
        hideProgressBar();
    }

    @Override
    public Observable endListReached()
    {
        return endListReached;
    }
}
