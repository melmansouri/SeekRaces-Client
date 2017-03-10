package com.mel.seekraces.interfaces.fragmentReviews;

import com.mel.seekraces.entities.Review;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentReviewsPresenter {
    void getReviews(boolean isOnline, int idEvent);

    void onOptionsItemSelected(int itemSelected);
    void onDestroy();

    void addReview(boolean isOnline, Review review);

    void editReview(boolean isOnline, Review review);

    void deleteReview(boolean isOnline, String user, int idEvent);
}
