package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.ParticipantsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsView;
import com.activity_sync.renderers.ParticipantsRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ParticipantsScreen extends Screen implements IParticipantsView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.participants_refresh)
    SwipeRefreshLayout participantsRefreshLayout;

    @Bind(R.id.participants_list)
    RecyclerView participantsList;

    private PublishSubject refreshParticipants = PublishSubject.create();
    private RVRendererAdapter<User> adapter;
    private List<User> participants = new ArrayList<>();

    public ParticipantsScreen()
    {
        super(R.layout.particpants_screen);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        participantsRefreshLayout.setOnRefreshListener(() -> refreshParticipants.onNext(this));

        adapter = new RVRendererAdapter<>(this, new ParticipantsRenderer.Builder());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        participantsList.setLayoutManager(linearLayoutManager);
        participantsList.addItemDecoration(new DividerItemDecoration(this));
        participantsList.setAdapter(adapter);
    }

    @Override
    public Observable<User> selectedUser()
    {
        return adapter.itemSelected();
    }

    @Override
    public void addUsersList(Collection<User> users)
    {
        adapter.clear();
        this.participants.addAll(users);
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Observable refreshEvents()
    {
        return refreshParticipants;
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        participantsRefreshLayout.post(() -> participantsRefreshLayout.setRefreshing(isRefreshing));
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new ParticipantsPresenter(this);
    }
}
