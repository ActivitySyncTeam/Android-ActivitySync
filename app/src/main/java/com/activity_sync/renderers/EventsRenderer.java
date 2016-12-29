package com.activity_sync.renderers;

import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

import butterknife.Bind;

public class EventsRenderer extends Renderer<Event>
{
    @Bind(R.id.event_organizer)
    TextView eventOrganizer;

    @Bind(R.id.event_location)
    TextView eventLocation;

    @Bind(R.id.event_date)
    TextView eventDate;

    @Bind(R.id.event_places)
    TextView eventPlaces;

    @Bind(R.id.event_discipline)
    TextView eventDiscipline;

    public EventsRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    @Override
    public void render()
    {
        if (getContent().getOrganizer() == null)
        {
            eventOrganizer.setText("not returned");
        }
        else
        {
            eventOrganizer.setText(String.format("%s %s", getContent().getOrganizer().getName(),
                    getContent().getOrganizer().getSurname()));
        }

        if (getContent().getLocation().getName() == null)
        {
            eventLocation.setText("not returned");
        }
        else
        {
            eventLocation.setText(getContent().getLocation().getName());
        }

        eventDate.setText(getContent().getReadableDate());
        eventDiscipline.setText(getContent().getDiscipline().getName());
        eventPlaces.setText(String.format("%d/%d", getContent().getNumberOfPlayers() - getContent().getFreePlaces(), getContent().getNumberOfPlayers()));
    }

    public static class Builder extends RendererBuilder<Event>
    {
        public Builder()
        {
            super(Arrays.asList(new EventsRenderer(R.layout.event_item_view)));
        }

        @Override
        protected Class getPrototypeClass(Event content)
        {
            return EventsRenderer.class;
        }
    }
}
