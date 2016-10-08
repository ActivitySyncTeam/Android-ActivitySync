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

    @Bind(R.id.event_desc)
    TextView eventDescription;

    @Bind(R.id.event_date)
    TextView eventDate;

    public EventsRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    @Override
    public void render()
    {
        eventOrganizer.setText(getContent().getOrganizer().getName());
        eventDescription.setText(getContent().getDescription());
        eventDate.setText(getContent().getDate());
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
