package com.mel.seekraces.fragments.reviews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public ReviewsFragmentPresenterImpl(IFragmentReviewsView view) {
        this.view = view;
        interactor=new ReviewsFragmentInteractorImpl(this);
    }

    @Override
    public void getReviews(int idEvent) {
        if (view!=null){
            view.hideList();
            view.showProgressBar();
            interactor.getReviws(idEvent);
        }
    }

    @Override
    public void onOptionsItemSelected(int itemSelected) {

    }

    @Override
    public void onDestroy() {
        view=null;
        interactor.getReviws(0);
    }

    @Override
    public void addReview(Review review) {

    }

    @Override
    public void deleteReview(String user, int idEvent) {

    }

    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            if (object instanceof Response){
                Gson gson=new Gson();
                Type founderListType = new TypeToken<ArrayList<Review>>(){}.getType();
                List<Review> reviews=gson.fromJson(((Response)object).getContent(),founderListType);
                view.fillAdapterList(reviews);
                int totalReviews=reviews.size();
                double rating;
                int totalScores=0;
                for (Review review:reviews) {
                    totalScores+=review.getScore();
                }
                rating=totalScores/totalReviews;
                view.fillViewRating(rating,totalReviews);
                view.hideProgressBar();
                view.showList();
            }
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgressBar();
            //view.hideList();
            view.showMessage(response.getMessage());
        }
    }
}
