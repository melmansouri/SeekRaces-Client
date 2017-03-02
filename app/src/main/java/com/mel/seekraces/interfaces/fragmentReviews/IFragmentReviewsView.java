package com.mel.seekraces.interfaces.fragmentReviews;

import android.view.Menu;

import com.mel.seekraces.adapters.RVReviewsAdapter;
import com.mel.seekraces.entities.Review;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentReviewsView {
    void showItemMenu(int idgroup, boolean showMenu);

    Menu getMenu();

    void fillAdapterList(List<Review> reviews);

    void fillViewRating(float rating, int totalScores);

    void showDialogAddReview();

    void showDialogEditReview();

    void deleteOwnReview();

    void showProgressBar();
    void hideProgressBar();
    void showList();
    void hideList();

    RVReviewsAdapter getAdapter();

    void showMessage(String message);


}
