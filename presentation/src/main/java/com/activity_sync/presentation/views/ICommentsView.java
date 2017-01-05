package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Comment;

import java.util.Collection;

import rx.Observable;

public interface ICommentsView extends IScreenView
{
    Observable refreshComments();
    void addCommentsListAndClear(Collection<Comment> comments);
    void addCommentsListAndAddAtTheEnd(Collection<Comment> comments);
    void addSingleComment(Comment comment);
    void refreshingVisible(boolean isRefreshing);

    Observable sendCommentClick();
    String comment();

    void hideKeyboard();
    void scrollToBottom();
    void clearComment();

    void showEmptyCommentError();

    Observable endListReached();
}
