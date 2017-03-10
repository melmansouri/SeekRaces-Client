package com.mel.seekraces.fragments.detailUserPublishRace;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.crash.FirebaseCrash;
import com.mel.seekraces.R;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRacePresenter;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by void on 26/02/2017.
 */

public class DetailUserPublishRaceFragment extends Fragment implements IDetailUserPublishRaceView {
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtLugar)
    TextView txtLugar;
    @BindView(R.id.imgBtnFollow)
    ImageButton imgBtnFollow;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgProfileUser)
    CircleImageView imgProfileUser;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.imgBtnSentNotificacion)
    ImageButton imgBtnSentNotificacion;
    private User user;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private IDetailUserPublishRacePresenter presenter;
    private boolean imgBtnSentNotificacionClicked;
    private boolean imgBtnFollowClicked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable("user");
        mListener.changeTitleActionBar(R.string.title_perfil);
        mListener.setDrawerEnabled(false);
        mListener.hideFloatingButton();
        mListener.showHamburgerIconDrawer(false);
        mListener.setNavigationIcon();
        mListener.setOnClickNavigationToolbar(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        presenter = new DetailUserPublishRacePresenterImpl(SharedPreferencesSingleton.getInstance(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user_publish_race, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{
            Glide.with(getContext()).load(INetworkConnectionApi.BASE_URL_PICTURES + user.getPhoto_url()).error(R.drawable.user_default).into(imgProfileUser);
            txtEmail.setText(user.getEmail());
            txtUserName.setText(user.getUsername());
            if (TextUtils.isEmpty(user.getPlace())) {
                txtLugar.setText(user.getPlace());
            }
            if (user.isFollowed()) {
                imgBtnSentNotificacion.setVisibility(View.VISIBLE);
                imgBtnFollow.setImageResource(R.drawable.ic_unfollow);
            } else {
                imgBtnSentNotificacion.setVisibility(View.GONE);
                imgBtnFollow.setImageResource(R.drawable.ic_follow);
            }
            if (user.isSentNotificacion()) {
                imgBtnSentNotificacion.setImageResource(R.drawable.ic_notifications_button);
            } else {
                imgBtnSentNotificacion.setImageResource(R.drawable.ic_turn_notifications_off_button);
            }
            imgBtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.isFollowed()) {
                        presenter.unFollow(Utils.isOnline(getContext()), user.getEmail());
                    } else {
                        Follow follow = new Follow();
                        follow.setUserFollower(SharedPreferencesSingleton.getInstance(getContext()).getStringSP(Constantes.KEY_USER));
                        follow.setUserFollowed(user.getEmail());
                        presenter.follow(Utils.isOnline(getContext()), follow);
                    }
                    imgBtnFollowClicked=true;
                }
            });

            imgBtnSentNotificacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Follow follow = new Follow();
                    follow.setUserFollower(SharedPreferencesSingleton.getInstance(getContext()).getStringSP(Constantes.KEY_USER));
                    follow.setUserFollowed(user.getEmail());
                    if (user.isSentNotificacion()) {

                        follow.setSentNotificacion(0);

                    } else {
                        follow.setSentNotificacion(1);
                    }
                    presenter.setSentNotificacion(Utils.isOnline(getContext()), follow);
                    imgBtnSentNotificacionClicked=true;
                }
            });
        }catch (Exception e){
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean isClickedbtnSetNotificacion(){
        return imgBtnSentNotificacionClicked;
    }

    @Override
    public boolean isClickedbtnFollow(){
        return imgBtnFollowClicked;
    }

    @Override
    public void setClickedbtnSetNotificacion(boolean clicked){
        imgBtnSentNotificacionClicked=clicked;
    }

    @Override
    public void setClickedbtnFollow(boolean clicked){
        imgBtnFollowClicked=clicked;
    }

    @Override
    public void changeIconButtonollow(int id) {
        imgBtnFollow.setImageResource(id);
    }

    @Override
    public void changeIconButtonSendNotificacion(int id) {
        imgBtnSentNotificacion.setImageResource(id);
    }

    @Override
    public void showButtonSendNotificacion(boolean show) {
        imgBtnSentNotificacion.setVisibility((show)?View.VISIBLE:View.GONE);
    }

    @Override
    public User getUser() {
        return user;
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
        mListener.setOnClickNavigationToolbar(null);
        mListener.setDrawerEnabled(true);
        mListener.showHamburgerIconDrawer(true);
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
        cardView.setVisibility(View.VISIBLE);
        imgProfileUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponents() {
        cardView.setVisibility(View.GONE);
        imgProfileUser.setVisibility(View.GONE);
    }
}
