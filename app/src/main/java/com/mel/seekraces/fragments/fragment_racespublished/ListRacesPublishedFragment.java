package com.mel.seekraces.fragments.fragment_racespublished;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRacesPublishedFragment extends Fragment implements IListFragmentRacesPublishedView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RVRacesPublishedAdapter adapter;
    private IListFragmentRacesPublishedPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Filter filter=getArguments().getParcelable("filter");
        presenter=new ListFragmentRacesPublishedPresenterImpl(this);
        presenter.getRacesPublished(filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_racespublished, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void fillAdapterList(List<Event> races) {
        adapter=new RVRacesPublishedAdapter(races,null);
        recyclerView.setAdapter(adapter);
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
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


}
