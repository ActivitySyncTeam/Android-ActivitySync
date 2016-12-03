package com.activity_sync.tests;

import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.presenters.EventCreatorPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EventCreatorPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventCreatorView view;

    PublishSubject createEventClickEvent = PublishSubject.create();
    PublishSubject<Location> newLocationOccurredEvent = PublishSubject.create();
    PublishSubject locationErrorEvent = PublishSubject.create();
    PublishSubject<String> newDateOccurredEvent = PublishSubject.create();
    PublishSubject openLocationPickerClickEvent = PublishSubject.create();
    PublishSubject openDatePickerClickEvent = PublishSubject.create();
    PublishSubject confirmEventCreationEvent = PublishSubject.create();

    private Location testLocation;
    private String testDate = "29/02/2020";
    private String locationName = "Hala VLO";

    @Before
    public void setup()
    {
        Mockito.when(view.createEventClick()).thenReturn(createEventClickEvent);
        Mockito.when(view.newLocationEvent()).thenReturn(newLocationOccurredEvent);
        Mockito.when(view.locationErrorEvent()).thenReturn(locationErrorEvent);
        Mockito.when(view.newDateEvent()).thenReturn(newDateOccurredEvent);
        Mockito.when(view.openLocationPickerScreenClick()).thenReturn(openLocationPickerClickEvent);
        Mockito.when(view.openDatePickerClick()).thenReturn(openDatePickerClickEvent);
        Mockito.when(view.confirmCreationClickEvent()).thenReturn(confirmEventCreationEvent);

        testLocation = new LocationBuilder()
                .setId(1)
                .setLatitude(23.32)
                .setLongitude(23.23)
                .setName(locationName)
                .createLocation();
    }

    @Test
    public void eventCreatorPresenter_init_loadSpinners()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).preparePlayersSpinner();
        Mockito.verify(view).prepareDisciplineSpinner(any());
        Mockito.verify(view).prepareDisciplineSpinner(any());
    }

    @Test
    public void eventCreatorPresenter_openDatePickerClickEvent_openDatePicker()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        openDatePickerClickEvent.onNext(this);

        Mockito.verify(view).openDatePicker();
    }

    @Test
    public void eventCreatorPresenter_openLocationPickerClickEvent_openLocationPicker()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        openLocationPickerClickEvent.onNext(this);

        Mockito.verify(view).openLocationPickerScreen();
    }

    @Test
    public void eventCreatorPresenter_locationError_displayError()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        locationErrorEvent.onNext(this);

        Mockito.verify(view).showPickerLocationErrorMessage();
    }

    @Test
    public void eventCreatorPresenter_createEventClick_openConfirmationDialog()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        createEventClickEvent.onNext(this);

        Mockito.verify(view).showConfirmationDialog();
    }

    @Test
    public void eventCreatorPresenter_confirmCreation_openEventsScreen()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        confirmEventCreationEvent.onNext(this);

        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void eventCreatorPresenter_locationOccurred_updateView()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        newLocationOccurredEvent.onNext(testLocation);

        Mockito.verify(view).location(testLocation.getName());
    }

    @Test
    public void eventCreatorPresenter_dateOccurred_updateView()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        newDateOccurredEvent.onNext(testDate);

        Mockito.verify(view).date(testDate);
    }

    private EventCreatorPresenter createPresenter()
    {
        return new EventCreatorPresenter(Schedulers.immediate(), view, navigator, apiService);
    }
}
