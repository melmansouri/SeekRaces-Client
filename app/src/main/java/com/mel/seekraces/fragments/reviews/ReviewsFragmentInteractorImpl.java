package com.mel.seekraces.fragments.reviews;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 28/02/2017.
 */

public class ReviewsFragmentInteractorImpl implements IFragmentReviewsInteractor {
    private IListennerCallBack listennerCallBack;
    private Call<Response> getReviewsCall;
    private Call<Response> addReviewsCall;
    private Call<Response> editReviewsCall;
    private Call<Response> deleteReviewsCall;
    public ReviewsFragmentInteractorImpl(IListennerCallBack callBack) {
        this.listennerCallBack=callBack;
    }

    @Override
    public void getReviws(int idEvent) {
        if (idEvent!=0){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
            String url= INetworkConnectionApi.BASE_URL+"user/event/"+idEvent+"/reviews";
            getReviewsCall=networkConnectionApi.getReviews(url);
            getReviewsCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new Response();
                        responsetmp.setMessage(response.message());
                        responsetmp.setOk(false);

                    }else{
                        responsetmp=response.body();
                    }

                    if (responsetmp.isOk()){
                        listennerCallBack.onSuccess(responsetmp);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (getReviewsCall!=null && !getReviewsCall.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (getReviewsCall!=null){
                getReviewsCall.cancel();
            }
        }
    }

    @Override
    public void addReview(final Review review) {
        if (review!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
            addReviewsCall=networkConnectionApi.addNewOpinionEvent(review);
            addReviewsCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new Response();
                        responsetmp.setMessage(response.message());
                        responsetmp.setOk(false);

                    }else{
                        responsetmp=response.body();
                    }

                    if (responsetmp.isOk()){
                        //listennerCallBack.onSuccess(responsetmp);
                        getReviws(review.getEvent());
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (addReviewsCall!=null && !addReviewsCall.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (addReviewsCall!=null){
                addReviewsCall.cancel();
            }
        }
    }

    @Override
    public void editReview(final Review review) {
        if (review!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
            editReviewsCall=networkConnectionApi.editEventOpinion(review);
            editReviewsCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new Response();
                        responsetmp.setMessage(response.message());
                        responsetmp.setOk(false);

                    }else{
                        responsetmp=response.body();
                    }

                    if (responsetmp.isOk()){
                        //listennerCallBack.onSuccess(responsetmp);
                        getReviws(review.getEvent());
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (editReviewsCall!=null && !editReviewsCall.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (editReviewsCall!=null){
                editReviewsCall.cancel();
            }
        }
    }

    @Override
    public void deleteReview(String user, final int idEvent) {
        if (user!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
            String url= INetworkConnectionApi.BASE_URL+"user/"+user+"/event/"+idEvent+"/reviews";
            deleteReviewsCall=networkConnectionApi.deleteEventOpinion(url);
            deleteReviewsCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new Response();
                        responsetmp.setMessage(response.message());
                        responsetmp.setOk(false);

                    }else{
                        responsetmp=response.body();
                    }

                    if (responsetmp.isOk()){
                        //listennerCallBack.onSuccess(responsetmp);
                        getReviws(idEvent);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (deleteReviewsCall!=null && !deleteReviewsCall.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (deleteReviewsCall!=null){
                deleteReviewsCall.cancel();
            }
        }
    }
}
