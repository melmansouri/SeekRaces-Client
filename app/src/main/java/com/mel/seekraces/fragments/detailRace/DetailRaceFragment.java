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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.INetworkConnectionApi;

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
    private Race race;
    private IGenericInterface.OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        race = getArguments().getParcelable("race");
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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        //mListener.setActionBar(toolbar);
        //imgRace.setImageBitmap(race.getBitmap());
        Glide.with(getContext()).load(INetworkConnectionApi.BASE_URL_PICTURES+race.getImageName()).error(R.drawable.default_race).into(imgRace);

        //toolbar.setTitle(race.getName());
        collapsingToolbar.setTitle(race.getName());
        //collapsingToolbar.setExpandedTitleColor(getActivity().getColor(android.R.color.transparent));
        txtDistance.setText(race.getDistance() + "KM");
        SimpleDateFormat formato =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale.getDefault());
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String fecha="";
        try {
            date = sdf.parse(race.getDate_time_init());
            fecha = formato.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtFecha.setText(fecha);
        txtLugar.setText(race.getPlace());
        txtWeb.setText(race.getWeb());
        txtDescripcion.setText(race.getDescription());
        toolbar.inflateMenu(R.menu.menu_fragment_detail);
        toolbar.getMenu().setGroupVisible(R.id.group_reviews,(race.getId()==0)?false:true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mListener.startScreenReviews(race.getId());
                return false;
            }
        });
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
