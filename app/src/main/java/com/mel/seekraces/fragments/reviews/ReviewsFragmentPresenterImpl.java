package com.mel.seekraces.fragments.reviews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsInteractor;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsPresenter;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 28/02/2017.
 */

public class ReviewsFragmentPresenterImpl implements IFragmentReviewsPresenter, IListennerCallBack {
    private IFragmentReviewsView view;
    private IFragmentReviewsInteractor interactor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public ReviewsFragmentPresenterImpl(IFragmentReviewsView view, SharedPreferencesSingleton sharedPreferencesSingleton) {
        this.view = view;
        interactor = new ReviewsFragmentInteractorImpl(this);
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
    }

    @Override
    public void getReviews(int idEvent) {
        if (view != null) {
            view.hideList();
            view.showProgressBar();
            interactor.getReviws(idEvent);
        }
    }

    @Override
    public void onOptionsItemSelected(int itemSelected) {
        if (view != null) {
            if (itemSelected == RMapped.ITEM_OPINAR.getValue()) {
                view.showDialogAddReview();
            }else if  (itemSelected == RMapped.ITEM_EDTT_REVIEW.getValue()) {
                view.showDialogEditReview();
            } else if  (itemSelected == RMapped.ITEM_DELETE_REVIEW.getValue()) {
                view.deleteOwnReview();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.getReviws(0);
        interactor.addReview(null);
        interactor.editReview(null);
        interactor.deleteReview(null, 0);
    }

    @Override
    public void addReview(Review review) {
        if (view != null) {
            view.hideList();
            view.showProgressBar();
            interactor.addReview(review);
        }
    }

    @Override
    public void editReview(Review review) {
        if (view != null) {
            view.hideList();
            view.showProgressBar();
            interactor.editReview(review);
        }
    }

    @Override
    public void deleteReview(String user, int idEvent) {
        if (view != null) {
            view.hideList();
            view.showProgressBar();
            interactor.deleteReview(user, idEvent);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (view != null) {
            if (object instanceof Response) {
                Gson gson = new Gson();
                Type founderListType = new TypeToken<ArrayList<Review>>() {
                }.getType();
                List<Review> reviews = gson.fromJson(((Response) object).getContent(), founderListType);
                int totalReviews = reviews.size();
                float rating;
                float totalScores = 0;
                for (Review review : reviews) {
                    totalScores += review.getScore();
                }
                rating = totalScores / totalReviews;
                fillViewOwnReview(reviews);
                view.fillAdapterList(reviews);
                view.fillViewRating(rating, totalReviews);
                view.hideProgressBar();
                view.showList();
            }
        }
    }

    private void fillViewOwnReview(List<Review> reviews) {
        if (reviews!=null){
            String email = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
            Review ownReview = null;
            for (Review review :
                    reviews) {
                if (review.getUser().equals(email)) {
                    ownReview = review;
                    reviews.remove(review);
                    break;
                }
            }
            if (ownReview != null) {
                reviews.add(0, ownReview);
                if (view.getMenu() != null) {
                    view.showItemMenu(RMapped.ITEM_EDTT_REVIEW.getValue(), true);
                    view.showItemMenu(RMapped.ITEM_DELETE_REVIEW.getValue(), true);
                    view.showItemMenu(RMapped.ITEM_OPINAR.getValue(), false);
                }
            } else {

                if (view.getMenu() != null) {
                    view.showItemMenu(RMapped.ITEM_EDTT_REVIEW.getValue(), false);
                    view.showItemMenu(RMapped.ITEM_DELETE_REVIEW.getValue(), false);
                    view.showItemMenu(RMapped.ITEM_OPINAR.getValue(), true);
                }
            }
        }else{
            if (view.getMenu() != null) {
                view.showItemMenu(RMapped.ITEM_EDTT_REVIEW.getValue(), false);
                view.showItemMenu(RMapped.ITEM_DELETE_REVIEW.getValue(), false);
                view.showItemMenu(RMapped.ITEM_OPINAR.getValue(), true);
            }
        }

    }

    @Override
    public void onError(Response response) {
        if (view != null) {
            view.hideProgressBar();
            fillViewOwnReview(null);
            /*if (view.getAdapter()!=null){
                view.showList();
            }*/
            view.showMessage(response.getMessage());
        }
    }
}
