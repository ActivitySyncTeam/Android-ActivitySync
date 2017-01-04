package com.activity_sync.renderers;


import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

import butterknife.Bind;

public class UsersRenderer extends Renderer<User>
{
    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.user_nickname)
    TextView userNickname;

    @Bind(R.id.user_friends_nr)
    TextView userFirendsNr;

    @Bind(R.id.user_events_nr)
    TextView userEventsNr;

    @Bind(R.id.user_likes_nr)
    TextView userLikesNr;

    @Override
    public void render()
    {
        userName.setText(String.format("%s %s", getContent().getName(), getContent().getSurname()));
        userNickname.setText(getContent().getUsername());
        userFirendsNr.setText(String.valueOf(getContent().getFriends()));
        userEventsNr.setText(String.valueOf(getContent().getEvents()));
        userLikesNr.setText(String.valueOf(getContent().getLikes()));
    }

    public UsersRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder()
        {
            super(Arrays.asList(new UsersRenderer(R.layout.all_users_item_view)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return UsersRenderer.class;
        }
    }
}
