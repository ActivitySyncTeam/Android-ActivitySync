package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AvailableLevels implements Serializable
{
    @SerializedName("levels")
    private Level[] levels;

    public AvailableLevels(Level[] levels)
    {
        this.levels = new Level[levels.length];
        this.levels = levels;
    }

    public AvailableLevels()
    {

    }

    public List<Level> getLevelsList()
    {
        return Arrays.asList(levels);
    }
}

