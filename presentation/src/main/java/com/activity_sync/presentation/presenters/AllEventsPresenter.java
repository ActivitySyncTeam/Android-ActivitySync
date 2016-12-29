package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.joda.time.DateTime;

import java.util.ArrayList;

import rx.Scheduler;

public class AllEventsPresenter extends EventsFragmentBasePresenter
{
    public static int ALL_EVENTS_ID = 0;

    private final IPermanentStorage permanentStorage;

    public AllEventsPresenter(IEventsFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, IPermanentStorage permanentStorage)
    {
        super(view, navigator, uiThread, apiService);
        this.permanentStorage = permanentStorage;
    }

    private boolean alreadyLoaded = false;
    private DateTime dateTimeSelected;

    @Override
    public void start()
    {
        if (areLastCordsSaved())
        {
            super.start();
            prepareFilterLayout();
            dateTimeSelected = new DateTime();
            view.setDate(dateTimeSelected);
        }
        else
        {
            if (view.checkLocationPermissions() == false)
            {
                view.noPermissionLayoutVisible();
                view.refreshingVisible(false);
                view.askForPermission();
            }
            else
            {
                view.searchingForCordsVisible();
            }
        }

        subscriptions.add(view.locationEnabled()
                .observeOn(uiThread)
                .subscribe(isEnabled -> {

                    if (isEnabled)
                    {
                        view.postLocationPermissionsMessage();
                        view.searchingForCordsVisible();
                    }
                    else
                    {
                        view.noPermissionLayoutVisible();
                    }
                })
        );

        subscriptions.add(view.locationFound()
                .observeOn(uiThread)
                .subscribe(isEnabled -> {

                    if (!alreadyLoaded)
                    {
                        super.start();
                        prepareFilterLayout();
                    }
                })
        );

        subscriptions.add(view.enableLocationButtonClick()
                .observeOn(uiThread)
                .subscribe(isEnabled -> {

                    view.askForPermission();
                })
        );

        subscriptions.add(view.dateLayoutClicked()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.openDatePicker(dateTimeSelected);
                })
        );

        subscriptions.add(view.newDateEvent()
                .observeOn(uiThread)
                .subscribe(date -> {
                    view.setDate(date);
                })
        );

        subscriptions.add(view.refreshWithFilterClick()
                .observeOn(uiThread)
                .subscribe(date -> {
                    resolveFilterRefresh();
                })
        );
    }

    @Override
    void loadEvents()
    {
        apiService.getAllEvents()
                .observeOn(uiThread)
                .subscribe(eventsCollection -> {

                    view.refreshingVisible(false);
                    view.addEventsList(eventsCollection.getEvents());

                    alreadyLoaded = true;

                }, this::handleError);
    }

    public DateTime getDateTimeSelected()
    {
        return dateTimeSelected;
    }

    private boolean areLastCordsSaved()
    {
        return permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT) != IPermanentStorage.LAST_COORDINATION_DEFAULT;
    }

    private void prepareFilterLayout()
    {
        view.filterLayoutVisible(true);

        ArrayList<Discipline> list = new ArrayList<>();
        list.add(new Discipline(0, "Piłka nożna"));
        list.add(new Discipline(1, "Koszykówka"));

        view.prepareDisciplineSpinner(list);
    }

    private void resolveFilterRefresh()
    {
        view.refreshingVisible(true);

        if (view.disciplineFilter().getId() == ALL_EVENTS_ID)
        {
            loadEvents();
        }
        else
        {
            loadEvents();   //api call with filtering
        }
    }
}
