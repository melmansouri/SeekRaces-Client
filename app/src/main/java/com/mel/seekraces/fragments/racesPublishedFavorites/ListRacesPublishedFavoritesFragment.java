package com.mel.seekraces.fragments.racesPublishedFavorites;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.customsViews.SwipeRefreshLayoutWithEmpty;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.interfaces.OnFragmentInteractionListener;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedView;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRacesPublishedFavoritesFragment extends Fragment implements IListFragmentRacesPublishedFavoritesView{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayoutWithEmpty swipeRefresh;
    private RVRacesPublishedAdapter adapter;
    private IListFragmentRacesPublishedFavoritesPresenter presenter;
    private OnFragmentInteractionListener mListener;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesSingleton=SharedPreferencesSingleton.getInstance(getActivity());
        presenter = new ListFragmentRacesPublishedFavoritesPresenterImpl(this,sharedPreferencesSingleton);
        mListener.changeTitleActionBar(RMapped.TITLE_CARRERAS_FAVORITAS.getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_racespublished, container, false);
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
                presenter.getRacesPublishedFavorites();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getRacesPublishedFavorites();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void fillAdapterList(List<Event> races) {
        hideProgressBar();
        adapter = new RVRacesPublishedAdapter(getActivity(),races, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {mListener.showMessageFromFragments(message);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
