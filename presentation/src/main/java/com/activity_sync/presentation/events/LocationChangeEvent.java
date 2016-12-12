package com.activity_sync.presentation.events;


import com.activity_sync.presentation.models.Location;

public class LocationChangeEvent
{
    Location location;

    public LocationChangeEvent(Location location)
    {
        this.location = location;
    }

    public Location getLocation()
    {
        return location;
    }
}
