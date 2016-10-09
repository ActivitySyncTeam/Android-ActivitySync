package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.presenters.EventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;
import com.activity_sync.renderers.EventsRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;
import com.activity_sync.widgets.ActivitySyncRecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

public class EventsScreen extends ScreenWithMenu implements IEventsView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.events_list)
    ActivitySyncRecyclerView eventsList;

    @Bind(R.id.events_refresh)
    SwipeRefreshLayout eventsRefreshLayout;

    public EventsScreen()
    {
        super(R.layout.events_screen);
    }

    private PublishSubject refreshEvents = PublishSubject.create();
    private RVRendererAdapter<Event> adapter;
    private List<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);

        super.onCreate(savedInstanceState);
        adapter = new RVRendererAdapter<>(this, new EventsRenderer.Builder());

        eventsRefreshLayout.setColorSchemeColors(R.color.colorPrimaryDark);
        eventsRefreshLayout.setOnRefreshListener(() -> refreshEvents.onNext(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        eventsList.setLayoutManager(linearLayoutManager);
        eventsList.addItemDecoration(new DividerItemDecoration(this));
        eventsList.bindSwipeToRefreshLayout(eventsRefreshLayout);
        eventsList.setAdapter(adapter);

        setTitle(R.string.title_events);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Event> selectedEvent()
    {
        return adapter.itemSelected();
    }

    @Override
    public void addEventsList(Collection<Event> events)
    {
        adapter.clear();
        this.events.addAll(events);
        adapter.addAll(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void eventSelected(Event event)
    {
        Toast.makeText(this, String.format(getString(R.string.txt_event_clicked), event.getId()), Toast.LENGTH_LONG).show();
    }

    @Override
    public Observable refreshEvents()
    {
        return refreshEvents;
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        eventsRefreshLayout.post(() -> eventsRefreshLayout.setRefreshing(isRefreshing));
    }
}