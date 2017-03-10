package com.mel.seekraces.fragments.usersFollowed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.RVUsersFollowedAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedPresenter;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedView;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IListenerInteractionListUsersFollowed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by void on 26/02/2017.
 */

public class ListFragmentUsersFollowed extends Fragment implements IFragmentListUsersFollowedView,IListenerInteractionListUsersFollowed {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private IFragmentListUsersFollowedPresenter presenter;
    private RVUsersFollowedAdapter adapter;
    private String userFollowedSelectedToDelete;
    private String userFollowedSelectedToSetSendNotificacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.changeTitleActionBar(R.string.title_usuarios_seguidos);
        mListener.hideFloatingButton();
        presenter = new ListFragmentUsersFollowedPresenterImpl(SharedPreferencesSingleton.getInstance(getContext()), this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_user_followed, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getUsersFollowed(Utils.isOnline(getContext()));
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
        mListener.showFloatingButton();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout, message);
    }

    @Override
    public void showComponents() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponents() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void fillAdapter(List<User> followeds){
        adapter=new RVUsersFollowedAdapter(getContext(),followeds,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void unFollowFromAdapter() {
        adapter.unfollow(userFollowedSelectedToDelete);
        userFollowedSelectedToDelete="";
    }

    @Override
    public String getUserFollowedSelectedToDelete() {
        return userFollowedSelectedToDelete;
    }

    @Override
    public String getUserFollowedSelectedToSetSendNotificacion() {
        return userFollowedSelectedToSetSendNotificacion;
    }

    @Override
    public void setSendNotificacionFromAdapter() {
        adapter.setSendNotificacionFrom(userFollowedSelectedToSetSendNotificacion);
        userFollowedSelectedToSetSendNotificacion="";
    }

    @Override
    public void unFollow(String followed) {
        userFollowedSelectedToDelete=followed;
        presenter.unFollow(Utils.isOnline(getContext()),followed);
    }

    @Override
    public void setSendNotificacion(Follow follow) {
        userFollowedSelectedToSetSendNotificacion=follow.getUserFollowed();
        follow.setUserFollower(SharedPreferencesSingleton.getInstance(getContext()).getStringSP(Constantes.KEY_USER));
        presenter.setSentNotificacion(Utils.isOnline(getContext()),follow);
    }
}
