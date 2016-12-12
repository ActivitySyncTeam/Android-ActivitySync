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
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.events.LocationChangeEvent;
import com.activity_sync.presentation.events.LocationPermissionGranted;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IEventsFragmentView;
import com.activity_sync.renderers.EventsRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Inject
    IPermanentStorage permanentStorage;

    @Bind(R.id.events_refresh)
    SwipeRefreshLayout eventsRefreshLayout;

    @Bind(R.id.events_list)
    RecyclerView eventsList;

    @Bind(R.id.no_permission_view)
    LinearLayout noPermissionView;

    @Bind(R.id.looking_for_cords)
    LinearLayout lookingForCordsView;

    @Bind(R.id.events_enable_btn)
    Button enablePermissionBtn;

    private PublishSubject refreshEvents = PublishSubject.create();
    private PublishSubject<Boolean> locationsEnabled = PublishSubject.create();
    private PublishSubject<Location> locationFound = PublishSubject.create();
    private RVRendererAdapter<Event> adapter;
    private List<Event> events = new ArrayList<>();

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
        eventsRefreshLayout.post(() -> eventsRefreshLayout.setRefreshing(isRefreshing));
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
    public void eventsListVisible()
    {

        eventsList.setVisibility(View.VISIBLE);
        noPermissionView.setVisibility(View.GONE);
        lookingForCordsView.setVisibility(View.GONE);

        eventsRefreshLayout.setEnabled(true);
    }

    @Override
    public void noPermissionLayoutVisible()
    {
        eventsList.setVisibility(View.GONE);
        lookingForCordsView.setVisibility(View.GONE);
        noPermissionView.setVisibility(View.VISIBLE);

        eventsRefreshLayout.setEnabled(false);
    }

    @Override
    public void searchingForCordsVisible()
    {
        eventsList.setVisibility(View.GONE);
        lookingForCordsView.setVisibility(View.VISIBLE);
        noPermissionView.setVisibility(View.GONE);

        eventsRefreshLayout.setEnabled(false);
    }

    @Override
    public Observable enableLocationButtonClick()
    {
        return ViewObservable.clicks(enablePermissionBtn);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationChangeEvent event)
    {
        Toast.makeText(getContext(),
                String.format("Location changed: %f %f", event.getLocation().getLatitude(), event.getLocation().getLongitude()),
                Toast.LENGTH_LONG)
                .show();

        locationFound.onNext(event.getLocation());
    }

    @Override
    public void postLocationPermissionsMessage()
    {
        EventBus.getDefault().post(new LocationPermissionGranted());
    }

    @Override
    public Observable<Location> locationFound()
    {
        return locationFound;
    }
}
