package com.activity_sync.renderers;

import com.activity_sync.R;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

public class EventsRenderer extends Renderer<Event>
{
    public EventsRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    @Override
    public void render()
    {

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
