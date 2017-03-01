package com.mel.seekraces.fragments.reviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVReviewsAdapter;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsPresenter;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by void on 26/02/2017.
 */

public class ReviewsFragment extends Fragment implements IFragmentReviewsView {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtTotalScores)
    TextView txtTotalScores;
    @BindView(R.id.lnValoracion)
    LinearLayout lnValoracion;
    @BindView(R.id.recyclerViewReviews)
    RecyclerView recyclerViewReviews;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private int idEvent;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private IFragmentReviewsPresenter presenter;
    private RVReviewsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idEvent = getArguments().getInt("idEvent");
        mListener.setDrawerEnabled(false);
        mListener.hideFloatingButton();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.changeTitleActionBar(R.string.title_opiniones);
        mListener.showHamburgerIconDrawer(false);
        mListener.setNavigationIcon();
        mListener.setOnClickNavigationToolbar(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        presenter = new ReviewsFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getReviews(idEvent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //inflater.inflate(R.menu.menu_fragment_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IGenericInterface.OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        mListener.showHamburgerIconDrawer(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void fillAdapterList(List<Review> reviews) {
        adapter = new RVReviewsAdapter(getContext(),reviews);
        recyclerViewReviews.setAdapter(adapter);
    }

    @Override
    public void fillViewRating(double rating,int totalScores) {
        txtRating.setText(Double.toString(rating));
        ratingBar.setRating((float) rating);
        txtTotalScores.setText(String.valueOf(totalScores).concat(" Votos en total"));
    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showList() {
        lnValoracion.setVisibility(View.VISIBLE);
        recyclerViewReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        lnValoracion.setVisibility(View.GONE);
        recyclerViewReviews.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout,message);
    }
}
