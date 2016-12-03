package com.activity_sync.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Level;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    PublishSubject<String> newDateOccurred = PublishSubject.create();
    PublishSubject confirmClicked = PublishSubject.create();

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

        setTitle(getString(R.string.title_event_creator));
    }

    @Override
    public void prepareDisciplineSpinner(List<Discipline> disciplines)
    {
        ArrayAdapter<Discipline> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, disciplines);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        disciplineSpinner.setAdapter(adapter);
    }

    @Override
    public void prepareLevelSpinner(List<Level> levels)
    {
        ArrayAdapter<Level> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, levels);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        levelSpinner.setAdapter(adapter);
    }

    @Override
    public void preparePlayersSpinner()
    {
        List<String> players = new ArrayList<>();

        for (int i = 0; i < 40; i++)
        {
            players.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, players);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        playersSpinner.setAdapter(adapter);
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
    public Discipline discipline()
    {
        return (Discipline) disciplineSpinner.getSelectedItem();
    }

    @Override
    public Level level()
    {
        return (Level) levelSpinner.getSelectedItem();
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
        return newLocationOccurred;
    }

    @Override
    public Observable locationErrorEvent()
    {
        return locationErrorOccurred;
    }

    @Override
    public Observable newDateEvent()
    {
        return newDateOccurred;
    }

    @Override
    public Observable openLocationPickerScreenClick()
    {
        return ViewObservable.clicks(eventLocationLayout).throttleFirst(2, TimeUnit.SECONDS);
    }

    @Override
    public Observable openDatePickerClick()
    {
        return ViewObservable.clicks(eventDateLayout).throttleFirst(2, TimeUnit.SECONDS);
    }

    @Override
    public void openLocationPickerScreen()
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
    public void openDatePicker()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) ->
        {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, selectedHour, selectedMinute) ->
            {
                newDateOccurred.onNext(String.format(getString(R.string.date_format), selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute));

            }, hour, minute, false);

            timePickerDialog.show();

        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void showPickerLocationErrorMessage()
    {
        Toast.makeText(this, R.string.txt_error_choosing_location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isOrganizerEnrolled()
    {
        return eventCheckbox.isChecked();
    }

    @Override
    public void showConfirmationDialog()
    {
        showDialog(R.string.txt_create_event_confirmation_title, R.string.txt_create_event_confirmation_text, confirmClicked);
    }

    @Override
    public Observable confirmCreationClickEvent()
    {
        return confirmClicked;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SELECT_PLACE)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Location location = new LocationBuilder()
                        .setName(place.getAddress().toString())
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
