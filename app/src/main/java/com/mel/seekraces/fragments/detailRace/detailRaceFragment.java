package com.mel.seekraces.fragments.detailRace;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.interfaces.IGenericInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by void on 26/02/2017.
 */

public class DetailRaceFragment extends Fragment {
    @BindView(R.id.imgRace)
    ImageView imgRace;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtLugar)
    TextView txtLugar;
    @BindView(R.id.txtFecha)
    TextView txtFecha;
    @BindView(R.id.txtWeb)
    TextView txtWeb;
    @BindView(R.id.txtDescripcion)
    TextView txtDescripcion;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtTotalScores)
    TextView txtTotalScores;
    private Event event;
    private IGenericInterface.OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = getArguments().getParcelable("event");

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mListener.setDrawerEnabled(false);
        mListener.hideFloatingButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_race, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgRace.setImageBitmap(event.getBitmap());

        //toolbar.setTitle(event.getName());
        collapsingToolbar.setTitle(event.getName());
        //collapsingToolbar.setExpandedTitleColor(getActivity().getColor(android.R.color.transparent));
        txtDistance.setText(event.getDistance() + "KM");
        SimpleDateFormat formato =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale.getDefault());
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(event.getDate_time_init());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fecha = formato.format(date);
        txtFecha.setText(fecha);
        txtLugar.setText(event.getPlace());
        txtWeb.setText(event.getWeb());
        txtDescripcion.setText(event.getDescription());
        txtRating.setText(Double.toString(event.getRating()));
        ratingBar.setRating((float)event.getRating());
        txtTotalScores.setText(String.valueOf(event.getTotal_scores()).concat(" Votos en total"));
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.setDrawerEnabled(true);
        mListener.showFloatingButton();
    }
}
