package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Location;

public class LocationBuilder
{
    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public LocationBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public LocationBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public LocationBuilder setLatitude(double latitude)
    {
        this.latitude = latitude;
        return this;
    }

    public LocationBuilder setLongitude(double longitude)
    {
        this.longitude = longitude;
        return this;
    }

    public Location createLocation()
    {
        return new Location(id, name, latitude, longitude);
    }
}
