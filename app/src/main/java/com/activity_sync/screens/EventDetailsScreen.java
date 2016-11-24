package com.activity_sync.screens;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.presenters.EventDetailsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

public class EventDetailsScreen extends Screen implements IEventDetailsView, OnMapReadyCallback
{
    public static final String EVENT_ID = "event_id";

    @Inject
    INavigator navigator;

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.event_det_date)
    TextView eventDate;

    @Bind(R.id.event_det_description)
    TextView eventDescription;

    @Bind(R.id.event_det_location)
    TextView eventLocation;

    @Bind(R.id.event_det_organizer)
    TextView eventOrganizer;

    @Bind(R.id.event_det_participants)
    TextView eventParticipants;

    @Bind(R.id.event_det_price)
    TextView eventPrice;

    @Bind(R.id.event_det_discipline)
    TextView eventDiscipline;

    @Bind(R.id.join_event_btn)
    Button joinEventButton;

    @Bind(R.id.organizer_layout)
    LinearLayout organizerLayout;

    @Bind(R.id.participants_layout)
    LinearLayout participantsLayout;

    private GoogleMap map;

    private PublishSubject confirmationDialogOk = PublishSubject.create();
    private PublishSubject confirmationDialogCancel = PublishSubject.create();

    public EventDetailsScreen()
    {
        super(R.layout.event_details_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        SupportMapFragment supportMapFragment = new SupportMapFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, supportMapFragment);
        transaction.commit();

        supportMapFragment.getMapAsync(this);

        setTitle(R.string.title_event_details);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        int eventID = getIntent().getIntExtra(EventDetailsScreen.EVENT_ID, 1);
        return new EventDetailsPresenter(AndroidSchedulers.mainThread(), this, navigator, eventID);
    }

    @Override
    public Observable joinEventClick()
    {
        return ViewObservable.clicks(joinEventButton);
    }

    @Override
    public Observable organizerDetailsClick()
    {
        return ViewObservable.clicks(organizerLayout);
    }

    @Override
    public Observable participantsDetailsClick()
    {
        return ViewObservable.clicks(participantsLayout);
    }

    @Override
    public void setEventData(Event event)
    {
        eventDate.setText(event.getReadableDate());
        eventDescription.setText(event.getDescription());
        eventLocation.setText(event.getLocation().getName());
        eventOrganizer.setText(event.getOrganizer().getUserDetails().getUserName());
        eventParticipants.setText(String.format("%d/%d", event.getOccupiedPlaces(), event.getMaxPlaces()));
        eventPrice.setText(String.format("%.2f%s", event.getPrice().getAmount(), event.getPrice().getCurrency()));
        eventDiscipline.setText(event.getDiscipline().getName());
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        LatLng jordan = new LatLng(50.061124, 19.914123);
        map.addMarker(new MarkerOptions().position(jordan).title("Marker in Jordan"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(jordan, 15));
    }

    @Override
    public Observable confirmationDialogOk()
    {
        return confirmationDialogOk;
    }

    @Override
    public Observable confirmationDialogCancel()
    {
        return confirmationDialogCancel;
    }

    @Override
    public String joinEventConfirmationText()
    {
        return "Are you sure you want to join this event?";
    }

    @Override
    public String joinEventConfirmationTitle()
    {
        return "Join Event Confirmation";
    }

    @Override
    public String leaveEventConfirmationText()
    {
        return "Are you sure you want to leave this event?";
    }

    @Override
    public String leaveEventConfirmationTitle()
    {
        return "Leave Event Confirmation";
    }

    @Override
    public String cancelEventConfirmationText()
    {
        return "Are you sure you want to cancel this event? This operation cannot be undone.";
    }

    @Override
    public String cancelEventConfirmationTitle()
    {
        return "Cancel Event Confirmation";
    }

    @Override
    public void showJoinEventMessage()
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "You have joined this event", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showLeaveEventMessage()
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "You have left this event", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, (dialog, which) ->
        {
            confirmationDialogOk.onNext(this);
        });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, (dialog, which) ->
        {
            confirmationDialogCancel.onNext(this);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
