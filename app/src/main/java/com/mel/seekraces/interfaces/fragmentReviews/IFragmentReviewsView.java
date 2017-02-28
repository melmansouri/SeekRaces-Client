package com.mel.seekraces.interfaces.fragmentReviews;

import com.mel.seekraces.entities.Review;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentReviewsView {
    void fillAdapterList(List<Review> reviews);

    void fillViewRating(double rating, int totalScores);

    void showProgressBar();
    void hideProgressBar();
    void showList();
    void hideList();
    void showMessage(String message);


}
