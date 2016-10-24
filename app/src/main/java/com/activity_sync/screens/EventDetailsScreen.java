package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.Toast;

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

public class EventDetailsScreen extends Screen implements IEventDetailsView, OnMapReadyCallback
{
    @Inject
    INavigator navigator;

//    @Bind(R.id.event_organizer)
//    TextView eventOrganizer;
//
//    @Bind(R.id.event_location)
//    TextView eventLocation;
//
//    @Bind(R.id.event_date)
//    TextView eventDate;
//
//    @Bind(R.id.event_places)
//    TextView eventPlaces;

    @Bind(R.id.join_event_btn)
    Button joinEventButton;

    private GoogleMap map;

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
        return new EventDetailsPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    public Observable joinEventClick()
    {
        return ViewObservable.clicks(joinEventButton);
    }

    @Override
    public Observable organizerDetailsClick()
    {
        return ViewObservable.clicks(joinEventButton);
    }

    @Override
    public void organizerSelected()
    {
        Toast.makeText(this, "Organizer field has been clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setEventData(Event event)
    {

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        LatLng jordan = new LatLng(50.061124, 19.914123);
        map.addMarker(new MarkerOptions().position(jordan).title("Marker in Jordan"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(jordan, 15));
    }
}
