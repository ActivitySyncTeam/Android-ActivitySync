package com.activity_sync.renderers;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

public class ParticipantsRenderer extends Renderer<User>
{


    public ParticipantsRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    @Override
    public void render()
    {

    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder()
        {
            super(Arrays.asList(new ParticipantsRenderer(R.layout.event_item_view)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return ParticipantsRenderer.class;
        }
    }
}
