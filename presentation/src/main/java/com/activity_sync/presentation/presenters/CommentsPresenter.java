package com.activity_sync.presentation.presenters;

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

    private int currentPage = 1;
    private boolean endAlreadyReached = false;

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

                    currentPage = 1;
                    endAlreadyReached = false;

                    loadComments();
                })
        );

        subscriptions.add(view.endListReached()
                .subscribe(event -> {

                    if (!endAlreadyReached)
                    {
                        currentPage++;
                        loadComments();
                    }
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
                        apiService.addComment(eventId, view.comment())
                                .observeOn(uiThread)
                                .subscribe(comment -> {

                                    view.addSingleComment(comment);
                                    view.clearComment();
                                    view.hideKeyboard();
                                    view.scrollToBottom();

                                }, this::handleError);
                    }
                })
        );
    }

    private void loadComments()
    {
        view.refreshingVisible(true);

        apiService.getEventComments(eventId, currentPage)
                .observeOn(uiThread)
                .subscribe(commentsCollection -> {

                    if (commentsCollection.getNext() == null)
                    {
                        endAlreadyReached = true;
                    }
                    else
                    {
                        endAlreadyReached = false;
                    }

                    if (currentPage == 1)
                    {
                        view.addCommentsListAndClear(commentsCollection.getComments());
                    }
                    else
                    {
                        view.addCommentsListAndAddAtTheEnd(commentsCollection.getComments());
                    }

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
