package com.mel.seekraces.interfaces.fragmentReviews;

import com.mel.seekraces.entities.Review;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentReviewsInteractor {
    void getReviws(int idEvent);

    void addReview(Review review);

    void deleteReview(String user, int idEvent);
}
