package com.activity_sync.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.EventDetailsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class EventDetailsScreen extends ScreenWithMenu implements IEventDetailsView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.event_organizer)
    TextView eventOrganizer;

    @Bind(R.id.event_location)
    TextView eventLocation;

    @Bind(R.id.event_date)
    TextView eventDate;

    @Bind(R.id.event_places)
    TextView eventPlaces;

    @Bind(R.id.join_event_btn)
    Button joinEventButton;

    public EventDetailsScreen()
    {
        super(R.layout.event_details_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventDetailsPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    public Observable joinEventClick()
    {
        return ViewObservable.clicks(eventOrganizer);
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
}
