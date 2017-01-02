package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.presenters.EventDetailsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.IApiService;
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
    IApiService apiService;

    @Inject
    INavigator navigator;

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.event_details_base_info_layout)
    LinearLayout baseInfoLayout;

    @Bind(R.id.you_organizer_layout)
    LinearLayout youOrganizerLayout;

    @Bind(R.id.you_participant_layout)
    LinearLayout youParticipantLayout;

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

    @Bind(R.id.event_det_level)
    TextView eventLevel;

    @Bind(R.id.event_det_discipline)
    TextView eventDiscipline;

    @Bind(R.id.join_leave_event_btn)
    Button joinLeaveEventButton;

    @Bind(R.id.edit_event_btn)
    Button editEventButton;

    @Bind(R.id.cancel_event_btn)
    Button cancelEventButton;

    @Bind(R.id.organizer_layout)
    LinearLayout organizerLayout;

    @Bind(R.id.participants_layout)
    LinearLayout participantsLayout;

    @Bind(R.id.event_details_buttons_layout)
    LinearLayout buttonsLayout;

    @Bind(R.id.participant_tv)
    TextView participantTv;

    @Bind(R.id.comments_on_map_btn)
    LinearLayout commentsLayout;

    private GoogleMap map;

    private PublishSubject joinEventConfirmed = PublishSubject.create();
    private PublishSubject leaveEventConfirmed = PublishSubject.create();
    private PublishSubject cancelEventConfirmed = PublishSubject.create();
    private PublishSubject googleMapReadyEvent = PublishSubject.create();

    public EventDetailsScreen()
    {
        super(R.layout.event_details_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        return new EventDetailsPresenter(AndroidSchedulers.mainThread(), this, navigator, eventID, apiService);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    public Observable joinLeaveEventClick()
    {
        return ViewObservable.clicks(joinLeaveEventButton);
    }

    @Override
    public Observable editEventClick()
    {
        return ViewObservable.clicks(editEventButton);
    }

    @Override
    public Observable cancelEventClick()
    {
        return ViewObservable.clicks(cancelEventButton);
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
        eventLocation.setText(event.getLocation().getDescription());
        eventOrganizer.setText(String.format("%s. %s", event.getOrganizer().getName().substring(0, 1), event.getOrganizer().getSurname()));
        eventParticipants.setText(String.format("%d/%d", event.getNumberOfPlayers() - event.getFreePlaces(), event.getNumberOfPlayers()));
        eventLevel.setText(event.getLevel().getName());
        eventDiscipline.setText(event.getDiscipline().getName());

        loadMap(event);
        setOrganizerParticipantView(event);
    }

    @Override
    public void showEnrollConfirmationDialog()
    {
        showDialog(R.string.txt_enroll_confirmation_title, R.string.txt_enroll_confirmation_text, joinEventConfirmed);
    }

    @Override
    public void showLeaveConfirmationDialog()
    {
        showDialog(R.string.txt_leave_confirmation_title, R.string.txt_leave_confirmation_text, leaveEventConfirmed);
    }

    @Override
    public void showCancelConfirmationDialog()
    {
        showDialog(R.string.txt_cancel_confirmation_title, R.string.txt_cancel_confirmation_text, cancelEventConfirmed);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        googleMapReadyEvent.onNext(this);
    }

    private void loadMap(Event event)
    {
        LatLng location = new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        map.addMarker(new MarkerOptions().position(location));
    }

    @Override
    public Observable googleMapAsyncCompleted()
    {
        return googleMapReadyEvent;
    }

    @Override
    public Observable commentsClick()
    {
        return ViewObservable.clicks(commentsLayout);
    }

    @Override
    public Observable joinEventConfirmClick()
    {
        return joinEventConfirmed;
    }

    @Override
    public Observable leaveEventConfirmClick()
    {
        return leaveEventConfirmed;
    }

    @Override
    public Observable cancelEventConfirmClick()
    {
        return cancelEventConfirmed;
    }

    @Override
    public void showEnrollMessage()
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.txt_enroll_message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showLeaveEventMessage()
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.txt_leave_message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void setOrganizerParticipantView(Event event)
    {
        if (isConnectedWithEvent(event))
        {
            baseInfoLayout.setVisibility(View.VISIBLE);

            if (event.isOrganizer())
            {
                youOrganizerLayout.setVisibility(View.VISIBLE);
                editEventButton.setVisibility(View.VISIBLE);
                cancelEventButton.setVisibility(View.VISIBLE);
            }
            else
            {
                youOrganizerLayout.setVisibility(View.GONE);
                editEventButton.setVisibility(View.GONE);
                cancelEventButton.setVisibility(View.GONE);
            }

            if (isEnrolledForEvent(event))
            {
                prepareLeaveLayout(event);
            }
            else
            {
                prepareJoinLayout();
            }
        }
        else
        {
            baseInfoLayout.setVisibility(View.GONE);
            prepareJoinLayout();
        }
    }

    private void prepareJoinLayout()
    {
        youParticipantLayout.setVisibility(View.GONE);
        joinLeaveEventButton.setText(getResources().getString(R.string.btn_join_event));
        joinLeaveEventButton.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_positive));
    }

    private void prepareLeaveLayout(Event event)
    {
        youParticipantLayout.setVisibility(View.VISIBLE);

        if (event.isParticipant())
        {
            participantTv.setText(getResources().getString(R.string.txt_participant));
        }
        else
        {
            participantTv.setText(getResources().getString(R.string.txt_enrolled));
        }

        joinLeaveEventButton.setText(getResources().getString(R.string.btn_leave_event));
        joinLeaveEventButton.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_default_negative));
    }

    private boolean isConnectedWithEvent(Event event)
    {
        return event.isOrganizer() || event.isParticipant() || event.isCandidate();
    }

    private boolean isEnrolledForEvent(Event event)
    {
        return event.isParticipant() || event.isCandidate();
    }
}
