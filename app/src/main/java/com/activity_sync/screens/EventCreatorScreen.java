package com.activity_sync.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.presenters.EventCreatorPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

public class EventCreatorScreen extends Screen implements IEventCreatorView
{
    private static final int REQUEST_SELECT_PLACE = 1111;

    @Inject
    INavigator navigator;

    @Bind(R.id.event_date_layout)
    CardView eventDateLayout;

    @Bind(R.id.event_location_layout)
    CardView eventLocationLayout;

    @Bind(R.id.event_date)
    TextView eventDate;

    @Bind(R.id.event_location)
    TextView eventLocation;

    @Bind(R.id.spinner_discipline)
    Spinner disciplineSpinner;

    @Bind(R.id.spinner_level)
    Spinner levelSpinner;

    @Bind(R.id.spinner_players)
    Spinner playersSpinner;

    @Bind(R.id.event_checkbox)
    CheckBox eventCheckbox;

    @Bind(R.id.btn_create_event)
    Button createEventButton;

    PublishSubject<Location> newLocationOccurred = PublishSubject.create();
    PublishSubject locationErrorOccurred = PublishSubject.create();

    public EventCreatorScreen()
    {
        super(R.layout.event_creator_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventCreatorPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void openDisciplineSpinner(List<String> disciplines)
    {

    }

    @Override
    public void openLevelSpinner(List<String> disciplines)
    {

    }

    @Override
    public void openPlayersSpinner()
    {

    }

    @Override
    public String date()
    {
        return eventDate.toString();
    }

    @Override
    public String location()
    {
        return eventLocation.toString();
    }

    @Override
    public void date(String date)
    {
        eventDate.setText(date);
    }

    @Override
    public void location(String location)
    {
        eventLocation.setText(location);
    }

    @Override
    public String discipline()
    {
        return disciplineSpinner.getSelectedItem().toString();
    }

    @Override
    public String level()
    {
        return levelSpinner.getSelectedItem().toString();
    }

    @Override
    public String players()
    {
        return playersSpinner.getSelectedItem().toString();
    }

    @Override
    public Observable createEventClick()
    {
        return ViewObservable.clicks(createEventButton);
    }

    @Override
    public Observable<Location> newLocationEvent()
    {
        return locationErrorOccurred;
    }

    @Override
    public Observable locationErrorEvent()
    {
        return locationErrorOccurred;
    }

    @Override
    public void openPickerScreen()
    {
        try
        {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, REQUEST_SELECT_PLACE);
        }
        catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void showPickerLocationErrorMessage()
    {
        Toast.makeText(this, "Location has not been chosen properly", Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SELECT_PLACE)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Location location = new LocationBuilder()
                        .setName(place.getName().toString())
                        .setLatitude(place.getLatLng().latitude)
                        .setLongitude(place.getLatLng().longitude)
                        .createLocation();

                newLocationOccurred.onNext(location);
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR)
            {
                locationErrorOccurred.onNext(null);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
