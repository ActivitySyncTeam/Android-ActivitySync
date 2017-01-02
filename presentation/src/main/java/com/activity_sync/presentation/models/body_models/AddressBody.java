package com.activity_sync.presentation.models.body_models;


import com.activity_sync.presentation.models.Location;

public class AddressBody
{
    private double lat;
    private String city;
    private double lng;
    private String desc;

    public AddressBody(double lat, String city, double lng, String desc)
    {
        this.lat = lat;
        this.city = city;
        this.lng = lng;
        this.desc = desc;
    }

    public AddressBody(Location location)
    {
        this.lat = location.getLatitude();
        this.city = location.getCity();
        this.lng = location.getLongitude();
        this.desc = location.getDescription();
    }
}
