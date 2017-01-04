package com.activity_sync.screens;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.events.LocationPermissionGranted;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.utils.StringUtils;
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
import org.joda.time.DateTime;

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

    @Bind(R.id.filter_events_layout)
    LinearLayout filterEventsView;

    @Bind(R.id.events_enable_btn)
    Button enablePermissionBtn;

    @Bind(R.id.disciplines_toolbar)
    Spinner disciplineSpinner;

    @Bind(R.id.day_filter_tv)
    TextView dayFilter;

    @Bind(R.id.filter_date_layout)
    LinearLayout filterDateLayout;

    @Bind(R.id.refresh_filter)
    ImageView refreshFilter;

    private PublishSubject refreshEvents = PublishSubject.create();
    private PublishSubject<Boolean> locationsEnabled = PublishSubject.create();
    protected PublishSubject<Location> locationFound = PublishSubject.create();
    protected PublishSubject<DateTime> newDateOccurred = PublishSubject.create();
    private RVRendererAdapter<Event> adapter;
    private List<Event> events = new ArrayList<>();

    private List<Discipline> disciplines = new ArrayList<>();

    private String selectedDate;


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
    public void addEventsListAndClear(Collection<Event> events)
    {
        adapter.clear();
        this.events.addAll(events);
        adapter.addAll(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addEventsListAtTheEnd(Collection<Event> events)
    {
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
                } else
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
    public void filterLayoutVisible(boolean isVisible)
    {
        if (isVisible)
        {
            filterEventsView.setVisibility(View.VISIBLE);
        } else
        {
            filterEventsView.setVisibility(View.GONE);
        }
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
    public void postLocationPermissionsMessage()
    {
        EventBus.getDefault().post(new LocationPermissionGranted());
    }

    @Override
    public Observable<Location> locationFound()
    {
        return locationFound;
    }

    @Override
    public void prepareDisciplineSpinner(List<Discipline> disciplines)
    {
        this.disciplines.clear();
        this.disciplines = disciplines;

        this.disciplines.add(0, new Discipline(AllEventsPresenter.ALL_EVENTS_ID, getString(R.string.txt_all)));

        ArrayAdapter<Discipline> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_filter_toolbar_main_item, this.disciplines);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        disciplineSpinner.setAdapter(adapter);
    }

    @Override
    public void openDatePicker(DateTime dateTime)
    {
        if (dateTime == null)
        {
            dateTime = new DateTime();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerStyle, (view, selectedYear, selectedMonth, selectedDay) ->
        {
            newDateOccurred.onNext(new DateTime(selectedYear, selectedMonth + 1, selectedDay, 0, 0));

        }, dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());

        datePickerDialog.show();
    }

    @Override
    public Observable newDateEvent()
    {
        return newDateOccurred;
    }

    @Override
    public void setDate(DateTime dateTime)
    {
        if (dateTime == null || isDateToday(dateTime))
        {
            dayFilter.setText(R.string.txt_from_today);
            selectedDate = getString(R.string.txt_from_today);
        } else
        {
            int day = dateTime.getDayOfMonth();
            int month = dateTime.getMonthOfYear();
            int year = dateTime.getYear();

            dayFilter.setText(String.format(getString(R.string.txt_filter_from), day, month, year));
            selectedDate = String.format(getString(R.string.txt_date_server_format), year, month, day);
        }
    }

    @Override
    public Observable dateLayoutClicked()
    {
        return ViewObservable.clicks(filterDateLayout);
    }

    @Override
    public Observable refreshWithFilterClick()
    {
        return ViewObservable.clicks(refreshFilter);
    }

    private boolean isDateToday(DateTime dateTime)
    {
        DateTime todayDate = new DateTime();
        return ((dateTime.getYear() == todayDate.getYear())
                && (dateTime.getMonthOfYear() == todayDate.getMonthOfYear())
                && (dateTime.getDayOfMonth() == todayDate.getDayOfMonth()));
    }

    @Override
    public Discipline disciplineFilter()
    {
        return (Discipline) disciplineSpinner.getSelectedItem();
    }

    @Override
    public String getSelectedDate()
    {
        if (selectedDate.equals(getString(R.string.txt_from_today)))
        {
            return StringUtils.EMPTY;
        }
        else
        {
            return selectedDate;
        }
    }
}
