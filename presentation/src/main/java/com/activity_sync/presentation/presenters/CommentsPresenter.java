package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.builders.CommentBuilder;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ICommentsView;

import rx.Scheduler;
import timber.log.Timber;

public class CommentsPresenter extends Presenter<ICommentsView>
{
    private final Scheduler uiThread;
    private final int eventId;
    private final IApiService apiService;
    private final CurrentUser currentUser;

    public CommentsPresenter(ICommentsView view, Scheduler uiThread, int eventId, IApiService apiService, CurrentUser currentUser)
    {
        super(view);
        this.uiThread = uiThread;
        this.eventId = eventId;
        this.apiService = apiService;
        this.currentUser = currentUser;
    }

    @Override
    public void start()
    {
        super.start();

        loadComments();

        subscriptions.add(view.refreshComments()
                .subscribe(event -> {
                    loadComments();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.sendCommentClick()
                .subscribe(event -> {

                    if (view.comment().equals(StringUtils.EMPTY))
                    {
                        view.showEmptyCommentError();
                    }
                    else
                    {
                        Comment comment = new CommentBuilder()
                                .setComment(view.comment())
                                .setName("Marcin Zielinski")
                                .createComment();

                        view.addSingleComment(comment);
                        view.clearComment();
                        view.hideKeyboard();
                        view.scrollToBottom();
                    }
                })
        );
    }

    private void loadComments()
    {
        view.refreshingVisible(true);

        apiService.getEventComments(eventId)
                .observeOn(uiThread)
                .subscribe(commentsCollection -> {

                    view.addCommentsList(commentsCollection.getComments());
                    view.refreshingVisible(false);

                }, this::handleError);
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
        view.displayDefaultError();
    }
}
