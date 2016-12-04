package com.activity_sync.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;
import com.activity_sync.renderers.EventsRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

abstract public class EventsFragmentBase extends FragmentScreen implements IEventsFragmentView
{
    @Inject
    INavigator navigator;

    @Inject
    IApiService apiService;

    @Bind(R.id.events_refresh)
    SwipeRefreshLayout eventsRefreshLayout;

    @Bind(R.id.events_list)
    RecyclerView eventsList;

    @Bind(R.id.fragment_toolbar)
    LinearLayout fragmentToolbar;

    @Bind(R.id.empty_view)
    LinearLayout emptyView;

    @Bind(R.id.events_enable_btn)
    Button enablePermissionBtn;

    private PublishSubject refreshEvents = PublishSubject.create();
    private PublishSubject<Boolean> locationsEnabled = PublishSubject.create();
    private RVRendererAdapter<Event> adapter;
    private List<Event> events = new ArrayList<>();
    private List<String> disciplines = new ArrayList<String>()
    {{
        add("Koszykówka");
        add("Siatkówka");
        add("Piłka nożna");
    }};

    public EventsFragmentBase()
    {
        super(R.layout.events_fragment);
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

        eventsRefreshLayout.setOnRefreshListener(() -> refreshEvents.onNext(this));
        adapter = new RVRendererAdapter<>(getContext(), new EventsRenderer.Builder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        eventsList.setLayoutManager(linearLayoutManager);
        eventsList.addItemDecoration(new DividerItemDecoration(getContext()));
        eventsList.setAdapter(adapter);
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
    public Observable refreshEvents()
    {
        return refreshEvents;
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        eventsRefreshLayout.post(() -> {
            eventsRefreshLayout.setRefreshing(isRefreshing);
        });
    }

    @Override
    public boolean checkLocationPermissions()
    {
        int locationCoarse = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int LocationFine = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        return locationCoarse == PackageManager.PERMISSION_GRANTED && LocationFine == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void askForPermission()
    {
        if (Dexter.isRequestOngoing())
        {
            return;
        }

        Dexter.checkPermissions(new MultiplePermissionsListener()
        {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report)
            {
                if (report.getDeniedPermissionResponses().size() > 0)
                {
                    locationsEnabled.onNext(false);
                }
                else
                {
                    locationsEnabled.onNext(true);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
            {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    public Observable<Boolean> locationEnabled()
    {
        return locationsEnabled;
    }

    @Override
    public void eventsListVisible(boolean isVisible)
    {
        if (isVisible)
        {
            eventsList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            fragmentToolbar.setVisibility(View.GONE);

            eventsRefreshLayout.setEnabled(true);
        }
        else
        {
            eventsList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            fragmentToolbar.setVisibility(View.VISIBLE);

            eventsRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public Observable enableLocationButtonClick()
    {
        return ViewObservable.clicks(enablePermissionBtn);
    }

    @Override
    public void setFragmentToolbarVisibility(int visibility)
    {
        fragmentToolbar.setVisibility(visibility);
    }
}
