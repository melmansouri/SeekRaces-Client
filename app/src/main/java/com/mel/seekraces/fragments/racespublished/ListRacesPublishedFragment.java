package com.mel.seekraces.fragments.racesPublished;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.customsViews.SwipeRefreshLayoutWithEmpty;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRacesPublishedFragment extends Fragment implements IListFragmentRacesPublishedView,IGenericInterface.OnListInteractionListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayoutWithEmpty swipeRefresh;
    private RVRacesPublishedAdapter adapter;
    private IListFragmentRacesPublishedPresenter presenter;
    private Filter filter;
    private IGenericInterface.OnFragmentInteractionListener mListener;

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeTitleActionBar(RMapped.TITLE_CARRERAS_PUBLICADAS.getValue());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getParcelable("filter");
        presenter = new ListFragmentRacesPublishedPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_racespublished, container, false);
        ButterKnife.bind(this, view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefresh.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getRacesPublished(filter);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getRacesPublished(filter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment_list_races_published, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onOptionsItemSelected(id);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fillAdapterList(List<Event> races) {
        hideProgressBar();
        adapter = new RVRacesPublishedAdapter(races, this,mListener);
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
    public void showMessage(String message) {
        mListener.showMessageFromFragments(message);
    }

    @Override
    public void startScreenFilter() {
        mListener.startActivityFilters();
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
    }


    @Override
    public void addEventToFavorite(Favorite item) {
        presenter.addEventToFavorite(item);
    }

    @Override
    public void deleteEventFromFavorite(String user, int id) {
        presenter.deleteEventFromFavorite(user,id);
    }

    @Override
    public void onItemLongClickListener(Object object) {

    }
}
