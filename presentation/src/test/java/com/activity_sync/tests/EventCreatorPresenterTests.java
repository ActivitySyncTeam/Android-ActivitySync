package com.activity_sync.tests;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.LevelBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.presenters.EventCreatorPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EventCreatorPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IApiService apiService;

    @Mock
    IEventCreatorView view;

    @Mock
    IErrorHandler errorHandler;

    PublishSubject createEventClickEvent = PublishSubject.create();
    PublishSubject<Location> newLocationOccurredEvent = PublishSubject.create();
    PublishSubject locationErrorEvent = PublishSubject.create();
    PublishSubject<String> newDateOccurredEvent = PublishSubject.create();
    PublishSubject openLocationPickerClickEvent = PublishSubject.create();
    PublishSubject openDatePickerClickEvent = PublishSubject.create();
    PublishSubject confirmActionEvent = PublishSubject.create();

    private Location testLocation;
    private String testDate = "29/02/2020";
    private String locationName = "Hala VLO";

    private List<Discipline> disciplines = new ArrayList<>();
    private List<Level> levels = new ArrayList<>();

    private EventID eventID;
    private String description = "description";
    private Discipline discipline;
    private Level level;
    private int players = 12;
    private String date = "2016-12-29 23:23:00";
    private boolean isOrganizerEnrolled = true;

    @Before
    public void setup()
    {
        disciplines.add(new Discipline(1, "test"));
        levels.add(new Level(1, "test"));

        Mockito.when(view.createEventClick()).thenReturn(createEventClickEvent);
        Mockito.when(view.newLocationEvent()).thenReturn(newLocationOccurredEvent);
        Mockito.when(view.locationErrorEvent()).thenReturn(locationErrorEvent);
        Mockito.when(view.newDateEvent()).thenReturn(newDateOccurredEvent);
        Mockito.when(view.openLocationPickerScreenClick()).thenReturn(openLocationPickerClickEvent);
        Mockito.when(view.openDatePickerClick()).thenReturn(openDatePickerClickEvent);
        Mockito.when(view.confirmActionClickEvent()).thenReturn(confirmActionEvent);

        Mockito.when(apiService.getAvailableDisciplines()).thenReturn(Observable.just(disciplines));
        Mockito.when(apiService.getAvailableLevels()).thenReturn(Observable.just(levels));

        discipline = new DisciplineBuilder().setId(1).createDiscipline();
        level = new LevelBuilder().setId(1).createLevel();
        testLocation = new LocationBuilder()
                .setId(1)
                .setLatitude(23.32)
                .setLongitude(23.23)
                .setDescription(locationName)
                .createLocation();

        Mockito.when(view.description()).thenReturn(description);
        Mockito.when(view.discipline()).thenReturn(discipline);
        Mockito.when(view.level()).thenReturn(level);
        Mockito.when(view.date()).thenReturn(date);
        Mockito.when(view.location()).thenReturn(testLocation);
        Mockito.when(view.players()).thenReturn(players);
        Mockito.when(view.isOrganizerEnrolled()).thenReturn(isOrganizerEnrolled);

        eventID = new EventID(1);

        Mockito.when(apiService.createEvent(any())).thenReturn(Observable.just(eventID));
    }

    @Test
    public void eventCreatorPresenter_init_loadSpinners()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).preparePlayersSpinner();
        Mockito.verify(view).prepareDisciplineSpinner(disciplines);
        Mockito.verify(view).prepareLevelSpinner(levels);
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

        Mockito.verify(view).showCreateConfirmationDialog();
    }

    @Test
    public void eventCreatorPresenter_confirmCreation_openEventsDetailsScreen()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        confirmActionEvent.onNext(this);

        Mockito.verify(apiService).createEvent(any());
        Mockito.verify(navigator).openEventDetailsScreen(eventID.getEventID());
    }

    @Test
    public void eventCreatorPresenter_locationOccurred_updateView()
    {
        EventCreatorPresenter presenter = createPresenter();
        presenter.start();

        newLocationOccurredEvent.onNext(testLocation);

        Mockito.verify(view).location(testLocation);
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
        return new EventCreatorPresenter(Schedulers.immediate(), view, navigator, apiService, errorHandler);
    }
}
