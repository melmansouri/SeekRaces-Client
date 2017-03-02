package com.mel.seekraces.interfaces.fragmentReviews;

import com.mel.seekraces.entities.Review;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentReviewsPresenter {
    void getReviews(int idEvent);
    void onOptionsItemSelected(int itemSelected);
    void onDestroy();

    void addReview(Review review);

    void editReview(Review review);

    void deleteReview(String user, int idEvent);
}
