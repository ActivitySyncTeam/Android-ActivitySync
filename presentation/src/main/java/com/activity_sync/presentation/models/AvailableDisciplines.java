package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AvailableDisciplines implements Serializable
{
    @SerializedName("disciplines")
    private Discipline[] disciplines;

    public AvailableDisciplines(Discipline[] disciplines)
    {
        this.disciplines = new Discipline[disciplines.length];
        this.disciplines = disciplines;
    }

    public AvailableDisciplines()
    {

    }

    public List<Discipline> getDisciplinesList()
    {
        return Arrays.asList(disciplines);
    }
}
