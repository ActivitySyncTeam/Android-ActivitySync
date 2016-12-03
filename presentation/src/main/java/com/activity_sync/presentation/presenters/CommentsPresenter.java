package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.builders.CommentBuilder;
import com.activity_sync.presentation.views.ICommentsView;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class CommentsPresenter extends Presenter<ICommentsView>
{
    private final Scheduler uiThread;
    private final int eventId;

    public CommentsPresenter(ICommentsView view, Scheduler uiThread, int eventId)
    {
        super(view);
        this.uiThread = uiThread;
        this.eventId = eventId;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.refreshComments()
                .subscribe(event -> {
                    loadComments();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.sendCommentClick()
                .subscribe(event -> {
                    view.sendCommentMessage();

                    Comment comment = new CommentBuilder()
                            .setComment(view.comment())
                            .setName("Marcin Zielinski")
                            .createComment();

                    view.addSingleComment(comment);
                })
        );
    }

    private void loadComments()
    {
        //API CALL WILL BE HERE
        List<Comment> comments = new ArrayList<>();

        comments.add(new CommentBuilder()
                .setName("Marcin Zielinski")
                .setComment("Polecam organizatora")
                .createComment());

        comments.add(new CommentBuilder()
                .setName("Michal Wolny")
                .setComment("Ja tez")
                .createComment());

        comments.add(new CommentBuilder()
                .setName("Łukasz Petka")
                .setComment("Juz nie moge sei doczekac")
                .createComment());

        view.addCommentsList(comments);
    }
}
