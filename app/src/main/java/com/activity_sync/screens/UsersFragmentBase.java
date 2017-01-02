package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;
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

abstract public class UsersFragmentBase extends FragmentScreen implements IUsersFragmentView, IUserActionListener
{
    @Inject
    INavigator navigator;

    @Inject
    IApiService apiService;

    @Inject
    CurrentUser currentUser;

    @Bind(R.id.participants_refresh)
    SwipeRefreshLayout participantsRefreshLayout;

    @Bind(R.id.participants_list)
    RecyclerView participantsList;

    private PublishSubject<User> removeAction = PublishSubject.create();
    private PublishSubject<User> acceptAction = PublishSubject.create();

    private PublishSubject<User> removeConfirmed = PublishSubject.create();
    private PublishSubject<User> acceptConfirmed = PublishSubject.create();

    private PublishSubject refreshParticipants = PublishSubject.create();
    private RVRendererAdapter<User> adapter;
    private List<User> participants = new ArrayList<>();

    protected final boolean shouldDisplayAllOptions;
    protected final int eventId;

    public UsersFragmentBase(boolean shouldDisplayAllOptions, int eventId)
    {
        super(R.layout.participants_fragment);
        this.shouldDisplayAllOptions = shouldDisplayAllOptions;
        this.eventId = eventId;
    }

    public UsersFragmentBase(boolean shouldDisplayAllOptions)
    {
        super(R.layout.participants_fragment);
        this.shouldDisplayAllOptions = shouldDisplayAllOptions;
        this.eventId = 0;
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
    public Observable<User> selectedUser()
    {
        return adapter.itemSelected();
    }

    @Override
    public Observable refreshUsers()
    {
        return refreshParticipants;
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
    public void removeUser(User userToDelete)
    {
        Iterator<User> usersIterator = participants.iterator();

        while (usersIterator.hasNext())
        {
            if (usersIterator.next().getUserId() == userToDelete.getUserId())
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
    public void onDeclineButtonAction(User user)
    {
        removeAction.onNext(user);
    }

    @Override
    public void onAcceptButtonAction(User user)
    {
        acceptAction.onNext(user);
    }

    @Override
    public Observable<User> acceptEventClick()
    {
        return acceptAction;
    }

    @Override
    public Observable<User> removeEventClick()
    {
        return removeAction;
    }

    @Override
    public void removeSuccessMessage(User user)
    {
        Toast.makeText(getContext(), String.format(getContext().getString(R.string.txt_remove_success),
                user.getName()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void acceptSuccessMessage(User user)
    {
        Toast.makeText(getContext(), String.format(getContext().getString(R.string.txt_accept_success),
                user.getName()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void openRemoveConfirmationDialog(User user)
    {
        showDialog(R.string.txt_remove_user_confirmation_title, R.string.txt_remove_user_confirmation_text, user, removeConfirmed);
    }

    @Override
    public void openAcceptConfirmationDialog(User user)
    {
        showDialog(R.string.txt_accept_user_confirmation_title, R.string.txt_accept_user_confirmation_text, user, acceptConfirmed);
    }

    @Override
    public Observable acceptConfirmClick()
    {
        return acceptConfirmed;
    }

    @Override
    public Observable removeConfirmClick()
    {
        return removeConfirmed;
    }

    public void showDialog(int title, int message, User user, PublishSubject<User> confirmClicked)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);

        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, (dialog, which) ->
                confirmClicked.onNext(user));

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, (dialog, which) ->
                dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}