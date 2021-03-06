package com.mel.seekraces.fragments.reviews;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVReviewsAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.customsViews.SwipeRefreshLayoutWithEmpty;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsPresenter;
import com.mel.seekraces.interfaces.fragmentReviews.IFragmentReviewsView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by void on 26/02/2017.
 */

public class ReviewsFragment extends Fragment implements IFragmentReviewsView {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.txtTotalScores)
    TextView txtTotalScores;
    @BindView(R.id.lnValoracion)
    LinearLayout lnValoracion;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    AppCompatEditText edtOpinion;
    RatingBar ratingBarDialog;
    @BindView(R.id.rlRoot)
    RelativeLayout rlRoot;
    @BindView(R.id.recyclerViewReviews)
    RecyclerView recyclerViewReviews;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayoutWithEmpty swipeRefresh;
    /*@BindView(R.id.fabOpinar)
    FloatingActionButton fabOpinar;
    @BindView(R.id.fabEdit)
    FloatingActionButton fabEdit;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;
    @BindView(R.id.menu_fab)
    FloatingActionMenu menuFab;*/
    @BindView(R.id.fabOpinar)
    FloatingActionButton fabOpinar;
    @BindView(R.id.fabEdit)
    FloatingActionButton fabEdit;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;
    @BindView(R.id.menu_fab)
    FloatingActionsMenu menuFab;

    private int idEvent;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private IFragmentReviewsPresenter presenter;
    private RVReviewsAdapter adapter;
    private Menu menu;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

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
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(getContext());
        presenter = new ReviewsFragmentPresenterImpl(this, sharedPreferencesSingleton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        swipeRefresh.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getReviews(Utils.isOnline(getContext()), idEvent);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getReviews(Utils.isOnline(getContext()), idEvent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        menu.clear();
        //inflater.inflate(R.menu.menu_fragment_reviews, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemSelected(item.getGroupId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showItemMenu(int idgroup, boolean showMenu) {
        //menu.setGroupVisible(idgroup, showMenu);
    }

    @Override
    public void showFabOpinar(boolean show) {
        fabOpinar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFabDelete(boolean show) {
        fabDelete.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFabEdit(boolean show) {
        fabEdit.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void closeFabMenu(boolean close){
        //menuFab.close(close);
    }

    @Override
    public Menu getMenu() {
        return menu;
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
        adapter = new RVReviewsAdapter(getContext(), reviews);
        recyclerViewReviews.setAdapter(adapter);
    }

    @Override
    public void fillViewRating(float rating, int totalScores) {
        DecimalFormat df = new DecimalFormat("####0.00");
        txtRating.setText(df.format(rating));
        ratingBar.setRating(rating);
        txtTotalScores.setText(String.valueOf(totalScores).concat(" Votos en total"));
    }

    @Override
    @OnClick(R.id.fabOpinar)
    public void showDialogAddReview() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), null);
        /*View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_edit_review, coordinatorLayout);*/
        builder.setView(R.layout.dialog_add_edit_review);

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Review review = new Review();
                review.setEvent(idEvent);
                review.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                review.setScore(ratingBarDialog.getRating());
                review.setComment(edtOpinion.getText().toString());
                review.setDateOpinion(Utils.convertDateSpanishToEnglish(Utils.getCurrentDateSpanishString()));
                presenter.addReview(Utils.isOnline(getContext()), review);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alterDialog = builder.show();
        ratingBarDialog = (RatingBar) alterDialog.findViewById(R.id.ratingBarDialog);
        edtOpinion = (AppCompatEditText) alterDialog.findViewById(R.id.edtOpinion);
    }

    @Override
    @OnClick(R.id.fabEdit)
    public void showDialogEditReview() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), null);
        /*View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_edit_review, rlRoot);
        builder.setView(view);*/
        builder.setView(R.layout.dialog_add_edit_review);

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Review review = new Review();
                review.setEvent(idEvent);
                review.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                review.setScore(ratingBarDialog.getRating());
                review.setComment(edtOpinion.getText().toString());
                review.setDateOpinion(Utils.convertDateSpanishToEnglish(Utils.getCurrentDateSpanishString()));
                presenter.editReview(Utils.isOnline(getContext()), review);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alterDialog = builder.show();
        ratingBarDialog = (RatingBar) alterDialog.findViewById(R.id.ratingBarDialog);
        edtOpinion = (AppCompatEditText) alterDialog.findViewById(R.id.edtOpinion);
        Review review = adapter.getOwnReview();
        ratingBarDialog.setRating((float) review.getScore());
        edtOpinion.setText(review.getComment());
    }

    @Override
    @OnClick(R.id.fabDelete)
    public void deleteOwnReview() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), "Importante");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteReview(Utils.isOnline(getContext()), sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER), idEvent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage("¿Está seguro de eliminar tu comentario?");
        builder.show();
    }

    @Override
    public void showProgressBar() {
        //progressBar.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        //progressBar.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showList() {
        recyclerViewReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void showValoracion() {
        lnValoracion.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideValoracion() {
        lnValoracion.setVisibility(View.GONE);
    }

    @Override
    public void hideList() {
        recyclerViewReviews.setVisibility(View.GONE);
    }

    @Override
    public RVReviewsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout, message);
    }
}
