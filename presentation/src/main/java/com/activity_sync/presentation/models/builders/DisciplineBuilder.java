package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Discipline;

public class DisciplineBuilder
{
    private int id;
    private String name;

    public DisciplineBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public DisciplineBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public Discipline createDiscipline()
    {
        return new Discipline(id, name);
    }
}
