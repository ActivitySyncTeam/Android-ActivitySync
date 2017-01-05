package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.joda.time.DateTime;

import java.util.Collection;

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
        dateTimeSelected = new DateTime();
        view.setDate(dateTimeSelected);

        if (areLastCordsSaved())
        {
            prepareFilterLayout();
            super.start();
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
                        prepareFilterLayout();
                        super.start();
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

                    currentPage = 1;
                    resolveRefresh();
                })
        );
    }

    @Override
    void loadEvents()
    {
        view.refreshingVisible(true);

        int range = permanentStorage.retrieveInteger(IPermanentStorage.SEARCH_RANGE, IPermanentStorage.SEARCH_RANGE_DEFAULT);
        float lat = permanentStorage.retrieveFloat(IPermanentStorage.LAST_LATITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT);
        float lng = permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT);

                apiService.getFilteredEvents(1, range, lat, lng)
                        .observeOn(uiThread)
                        .subscribe(eventsCollection -> {

                            if (eventsCollection.getNext() == null)
                            {
                                endAlreadyReached = true;
                            }
                            else
                            {
                                endAlreadyReached = false;
                            }

                            view.refreshingVisible(false);
                            view.addEventsListAndClear(eventsCollection.getEvents());
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

        apiService.getAvailableDisciplines()
                .observeOn(uiThread)
                .subscribe(disciplines -> {

                    view.prepareDisciplineSpinner(disciplines);

                }, this::handleError);
    }

    private void filterFinishedAndReloadAllData(Collection<Event> events)
    {
        view.refreshingVisible(false);
        view.addEventsListAndClear(events);
        alreadyLoaded = true;
    }

    private void filterFinishedAndAddAtTheEnd(Collection<Event> events)
    {
        view.refreshingVisible(false);
        view.addEventsListAtTheEnd(events);
        alreadyLoaded = true;
    }

    @Override
    void resolveRefresh()
    {
        view.refreshingVisible(true);

        int range = permanentStorage.retrieveInteger(IPermanentStorage.SEARCH_RANGE, IPermanentStorage.SEARCH_RANGE_DEFAULT);
        float lat = permanentStorage.retrieveFloat(IPermanentStorage.LAST_LATITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT);
        float lng = permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT);

        if (view.getSelectedDate().equals(StringUtils.EMPTY))
        {
            if (view.disciplineFilter().getId() == ALL_EVENTS_ID)
            {
                apiService.getFilteredEvents(currentPage, range, lat, lng)
                        .observeOn(uiThread)
                        .subscribe(eventsCollection -> {

                            if (eventsCollection.getNext() == null)
                            {
                                endAlreadyReached = true;
                            }
                            else
                            {
                                endAlreadyReached = false;
                            }

                            if (currentPage == 1)
                            {
                                filterFinishedAndReloadAllData(eventsCollection.getEvents());
                            }
                            else
                            {
                                filterFinishedAndAddAtTheEnd(eventsCollection.getEvents());
                            }

                        }, this::handleError);
            }
            else
            {
                apiService.getFilteredEvents(currentPage, range, lat, lng, view.disciplineFilter().getId())
                        .observeOn(uiThread)
                        .subscribe(eventsCollection -> {

                            if (eventsCollection.getNext() == null)
                            {
                                endAlreadyReached = true;
                            }
                            else
                            {
                                endAlreadyReached = false;
                            }

                            if (currentPage == 1)
                            {
                                filterFinishedAndReloadAllData(eventsCollection.getEvents());
                            }
                            else
                            {
                                filterFinishedAndAddAtTheEnd(eventsCollection.getEvents());
                            }

                        }, this::handleError);
            }
        }
        else
        {
            if (view.disciplineFilter().getId() == ALL_EVENTS_ID)
            {
                apiService.getFilteredEvents(currentPage, range, lat, lng, view.getSelectedDate())
                        .observeOn(uiThread)
                        .subscribe(eventsCollection -> {

                            if (eventsCollection.getNext() == null)
                            {
                                endAlreadyReached = true;
                            }
                            else
                            {
                                endAlreadyReached = false;
                            }


                            if (currentPage == 1)
                            {
                                filterFinishedAndReloadAllData(eventsCollection.getEvents());
                            }
                            else
                            {
                                filterFinishedAndAddAtTheEnd(eventsCollection.getEvents());
                            }

                        }, this::handleError);
            }
            else
            {
                apiService.getFilteredEvents(currentPage, range, lat, lng, view.disciplineFilter().getId(), view.getSelectedDate())
                        .observeOn(uiThread)
                        .subscribe(eventsCollection -> {

                            if (eventsCollection.getNext() == null)
                            {
                                endAlreadyReached = true;
                            }
                            else
                            {
                                endAlreadyReached = false;
                            }


                            if (currentPage == 1)
                            {
                                filterFinishedAndReloadAllData(eventsCollection.getEvents());
                            }
                            else
                            {
                                filterFinishedAndAddAtTheEnd(eventsCollection.getEvents());
                            }

                        }, this::handleError);
            }
        }
    }
}
