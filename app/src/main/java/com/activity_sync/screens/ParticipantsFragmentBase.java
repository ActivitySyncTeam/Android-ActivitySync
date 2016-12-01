package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsFragmentView;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.subjects.PublishSubject;

abstract public class ParticipantsFragmentBase extends FragmentScreen implements IParticipantsFragmentView, IParticipantActionListener
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

    protected final boolean isOrganizer;
    PublishSubject<User> onDeclinedEvent = PublishSubject.create();
    PublishSubject<User> onApprovedEvent = PublishSubject.create();

    public ParticipantsFragmentBase(boolean isOrganizer)
    {
        super(R.layout.participants_fragment);
        this.isOrganizer = isOrganizer;
    }

    public ParticipantsFragmentBase()
    {
        super(R.layout.participants_fragment);
        this.isOrganizer = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        App.component(getContext()).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        participantsRefreshLayout.setOnRefreshListener(() -> refreshParticipants.onNext(this));
        adapter = new RVRendererAdapter<>(getContext(), getRendererBuilder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        participantsList.setLayoutManager(linearLayoutManager);
        participantsList.addItemDecoration(new DividerItemDecoration(getContext()));
        participantsList.setAdapter(adapter);
    }

    @Override
    public Observable<User> selectedParticipant()
    {
        return adapter.itemSelected();
    }

    @Override
    public Observable refreshParticipants()
    {
        return refreshParticipants;
    }

    @Override
    public void addParticipantsList(Collection<User> participants)
    {
        adapter.clear();
        this.participants.addAll(participants);
        adapter.addAll(participants);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removeParticpant(User userToDelete)
    {
        Iterator<User> usersIterator = participants.iterator();

        while (usersIterator.hasNext())
        {
            if (usersIterator.next().getId() == userToDelete.getId())
            {
                adapter.remove(userToDelete);
                usersIterator.remove();
                break;
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        participantsRefreshLayout.post(() -> participantsRefreshLayout.setRefreshing(isRefreshing));
    }

    abstract RendererBuilder<User> getRendererBuilder();

    @Override
    public void onDeclineButtonClick(User user)
    {
        onDeclinedEvent.onNext(user);
    }

    @Override
    public void onApproveButtonClick(User user)
    {
        onApprovedEvent.onNext(user);
    }

    @Override
    public Observable<User> acceptEvent()
    {
        return onApprovedEvent;
    }

    @Override
    public Observable<User> declinedEvent()
    {
        return onDeclinedEvent;
    }

    @Override
    public void removedMessage()
    {
        Toast.makeText(getContext(), "User has been removed from participants list", Toast.LENGTH_LONG).show();
    }

    @Override
    public void acceptMessage()
    {
        Toast.makeText(getContext(), "User request has been accepted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void declinedMessage()
    {
        Toast.makeText(getContext(), "User request has been declined", Toast.LENGTH_LONG).show();
    }
}
