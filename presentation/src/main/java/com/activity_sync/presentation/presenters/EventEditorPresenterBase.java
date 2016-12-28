package com.activity_sync.presentation.presenters;


import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import rx.Scheduler;
import timber.log.Timber;

public class EventEditorPresenterBase extends Presenter<IEventCreatorView>
{
    protected final Scheduler uiThread;
    protected final INavigator navigator;
    protected final IApiService apiService;

    public EventEditorPresenterBase(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService)
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

        apiService.getAvailableDisciplines()
                .observeOn(uiThread)
                .subscribe((disciplines) -> {
                    view.prepareDisciplineSpinner(disciplines);
                }, this::handleError);

        apiService.getAvailableLevels()
                .observeOn(uiThread)
                .subscribe((levels) -> {
                    view.prepareLevelSpinner(levels);
                }, this::handleError);

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
    }

    private void handleError(Throwable error)
    {
        Timber.d(error.getMessage());
        view.displayDefaultError();
    }
}
