package com.mel.seekraces.fragments.racesFinished;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.customsViews.SwipeRefreshLayoutWithEmpty;
import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFinished.IListFragmentRacesFinishedPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFinished.IListFragmentRacesFinishedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRacesFinishedFragment extends Fragment implements IListFragmentRacesFinishedView, IGenericInterface.OnListInteractionListener,SearchView.OnQueryTextListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayoutWithEmpty swipeRefresh;
    private RVRacesPublishedAdapter adapter;
    private IListFragmentRacesFinishedPresenter presenter;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private List<Race> racesOwnWithoutFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(getActivity());
        presenter = new ListFragmentRacesFinishedPresenterImpl(this, sharedPreferencesSingleton);
        racesOwnWithoutFilter=new ArrayList<>();
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
                presenter.getOwnRacesPublished(Utils.isOnline(getContext()));
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.changeTitleActionBar(RMapped.TITLE_CARRERAS_ANTERIORES.getValue());
        mListener.hideFloatingButton();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getOwnRacesPublished(Utils.isOnline(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        /*final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.filter(racesOwnWithoutFilter,"");
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
                    }
                });*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void fillAdapterList(List<Race> races) {
        hideProgressBar();
        //racesOwnWithoutFilter.addAll(races);
        adapter = new RVRacesPublishedAdapter(getActivity(), races, this, mListener);
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
        mListener.showFloatingButton();
        presenter.onDestroy();
    }

    @Override
    public void addEventToFavorite(Favorite item) {

    }

    @Override
    public void deleteEventFromFavorite(String user, int id) {

    }

    @Override
    public void onItemLongClickListener(final Object object) {
        /*final String[] options={"Editar","Eliminar"};
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), null);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.selectOptionDialogLongClickList(options, i,(Race)object);
            }
        });

        builder.show();*/

    }

    @Override
    public void deleteOwnEvent(final Object object) {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), "Importante");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteOwnRacePublished(Utils.isOnline(getContext()), object);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage("¿Está seguro de eliminar esta carrera?");
        builder.show();
    }

    @Override
    public void editEvent(Object object) {
        mListener.editEvent((Race) object);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.filter(adapter,racesOwnWithoutFilter,newText);
        return true;
    }
}
