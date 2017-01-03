package com.activity_sync.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.DoubleUtils;
import com.activity_sync.presentation.views.IEventCreatorView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

abstract public class EventEditorScreenBase extends Screen implements IEventCreatorView
{
    private static final int REQUEST_SELECT_PLACE = 1111;

    @Inject
    IApiService apiService;

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

    @Bind(R.id.event_description)
    EditText eventDescritption;

    @Bind(R.id.event_checkbox)
    CheckBox eventCheckbox;

    @Bind(R.id.btn_editor_action)
    Button editorActionButton;

    PublishSubject<Location> newLocationOccurred = PublishSubject.create();
    PublishSubject locationErrorOccurred = PublishSubject.create();
    PublishSubject<String> newDateOccurred = PublishSubject.create();
    PublishSubject confirmClicked = PublishSubject.create();

    private List<Discipline> disciplines = new ArrayList<>();
    private List<Level> levels = new ArrayList<>();
    private List<String> playersNumbers = new ArrayList<>();
    private Location selectedLocation;

    List<Address> suggestedAddresses = new ArrayList<>();

    public EventEditorScreenBase()
    {
        super(R.layout.event_editor_screen);
    }

    @Override
    protected void inject(Context screen)
    {
        App.component(this).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void prepareDisciplineSpinner(List<Discipline> disciplines)
    {
        this.disciplines.clear();
        this.disciplines = disciplines;

        ArrayAdapter<Discipline> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, disciplines);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        disciplineSpinner.setAdapter(adapter);
    }

    @Override
    public void prepareLevelSpinner(List<Level> levels)
    {
        this.levels.clear();
        this.levels = levels;

        ArrayAdapter<Level> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, levels);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        levelSpinner.setAdapter(adapter);
    }

    @Override
    public void preparePlayersSpinner()
    {
        playersNumbers.clear();

        for (int i = 0; i < 40; i++)
        {
            playersNumbers.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_deafult_main_item, playersNumbers);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        playersSpinner.setAdapter(adapter);
    }

    @Override
    public String date()
    {
        return eventDate.getText().toString();
    }

    @Override
    public Location location()
    {
        return selectedLocation;
    }

    @Override
    public String description()
    {
        return eventDescritption.getText().toString();
    }

    @Override
    public void date(String date)
    {
        eventDate.setText(date);
    }

    @Override
    public void location(Location location)
    {
        selectedLocation = location;
        eventLocation.setText(location.getDescription());
    }

    @Override
    public void level(Level level)
    {
        for (int i = 0; i < levels.size() ; i++)
        {
            if (level.getId() == levels.get(i).getId())
            {
                playersSpinner.setSelection(i);
            }
        }
    }

    @Override
    public void discipline(Discipline discipline)
    {
        for (int i = 0; i < disciplines.size() ; i++)
        {
            if (discipline.getId() == disciplines.get(i).getId())
            {
                disciplineSpinner.setSelection(i);
            }
        }
    }

    @Override
    public void playersNumber(String players)
    {
        for (int i = 0; i < playersNumbers.size() ; i++)
        {
            if (playersNumbers.get(i).equals(players))
            {
                playersSpinner.setSelection(i);
            }
        }
    }

    @Override
    public void description(String description)
    {
        eventDescritption.setText(description);
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
    public int players()
    {
        return Integer.parseInt(playersSpinner.getSelectedItem().toString());
    }

    @Override
    public Observable createEventClick()
    {
        return ViewObservable.clicks(editorActionButton);
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerStyle, (view, selectedYear, selectedMonth, selectedDay) ->
        {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePickerStyle, (view1, selectedHour, selectedMinute) ->
            {
                newDateOccurred.onNext(String.format(getString(R.string.date_format), selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute));

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
    public void showCreateConfirmationDialog()
    {
        showDialog(R.string.txt_create_event_confirmation_title, R.string.txt_create_event_confirmation_text, confirmClicked);
    }

    @Override
    public void showUpdateConfirmationDialog()
    {
        showDialog(R.string.txt_update_event_confirmation_title, R.string.txt_update_event_confirmation_text, confirmClicked);
    }

    @Override
    public Observable confirmActionClickEvent()
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

                try
                {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    suggestedAddresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                Location location = new LocationBuilder()
                        .setLatitude(DoubleUtils.round(place.getLatLng().latitude, 6))
                        .setLongitude(DoubleUtils.round(place.getLatLng().longitude, 6))
                        .setCity(suggestedAddresses.get(0).getAddressLine(1))
                        .setDescription(place.getName().toString())
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

    @Override
    public void prepareUpdateLayout()
    {
        editorActionButton.setText(R.string.btn_update_event);
        eventCheckbox.setVisibility(View.GONE);
    }

    @Override
    public void showNoLocationChosenError()
    {
        Toast.makeText(this, R.string.err_choose_address, Toast.LENGTH_LONG).show();
    }
}
