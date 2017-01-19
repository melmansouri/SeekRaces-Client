package com.mel.seekraces.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRacesPublishedFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RVRacesPublishedAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_racespublished, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(new RVRacesPublishedAdapter(DummyContent.ITEMS, mListener));
        return view;
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
