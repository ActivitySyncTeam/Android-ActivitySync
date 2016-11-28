package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Level;

public class LevelBuilder
{
    private int id;
    private String name;

    public LevelBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public LevelBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public Level createLevel()
    {
        return new Level(id, name);
    }
}
