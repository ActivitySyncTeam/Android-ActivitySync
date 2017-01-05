package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("lng")
    private double longitude;

    @SerializedName("city")
    private String city;

    @SerializedName("desc")
    private String description;

    public Location(int id, double latitude, double longitude, String city, String description)
    {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.description = description;
    }

    public Location()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
