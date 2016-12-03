package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import java.util.Arrays;

import rx.Scheduler;

public class EventCreatorPresenter extends Presenter<IEventCreatorView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IApiService apiService;

    public EventCreatorPresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        view.preparePlayersSpinner();
        view.prepareDisciplineSpinner(Arrays.asList(new Discipline(1, "Koszykówka"), new Discipline(2, "Piłka nożna")));
        view.prepareLevelSpinner(Arrays.asList(new Level(1, "Niski"), new Level(2, "Średni"), new Level(3, "Wysoki")));

        subscriptions.add(view.openDatePickerClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.openDatePicker();
                })
        );

        subscriptions.add(view.openLocationPickerScreenClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.openLocationPickerScreen();
                })
        );

        subscriptions.add(view.newLocationEvent()
                .observeOn(uiThread)
                .subscribe(location -> {
                    view.location(location.getName());
                })
        );

        subscriptions.add(view.locationErrorEvent()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showPickerLocationErrorMessage();
                })
        );

        subscriptions.add(view.newDateEvent()
                .observeOn(uiThread)
                .subscribe(date -> {
                    view.date(date);
                })
        );

        subscriptions.add(view.createEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showConfirmationDialog();
                })
        );

        subscriptions.add(view.confirmCreationClickEvent()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventsScreen();
                })
        );
    }
}
