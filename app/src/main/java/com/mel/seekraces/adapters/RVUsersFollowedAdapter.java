package com.mel.seekraces.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IListenerInteractionListUsersFollowed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RVUsersFollowedAdapter extends RecyclerView.Adapter<RVUsersFollowedAdapter.ViewHolder> {

    private List<User> mValues;
    private Context c;
    private IListenerInteractionListUsersFollowed listenerInteractionListUsersFollowed;

    public RVUsersFollowedAdapter(Context context, List<User> items, IListenerInteractionListUsersFollowed listenerInteractionListUsersFollowed) {
        mValues=new ArrayList<>();
        mValues = items;
        c = context;
        this.listenerInteractionListUsersFollowed=listenerInteractionListUsersFollowed;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_followed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.user = mValues.get(position);

        Glide.with(c).load(INetworkConnectionApi.BASE_URL_PICTURES + holder.user.getPhoto_url()).error(R.drawable.user_default).into(holder.imgProfileUser);
        holder.txtUserName.setText(holder.user.getUsername());
        holder.txtLugar.setText(holder.user.getPlace());

        if (holder.user.isSentNotificacion()){
            holder.imgBtnSentNotificacion.setImageResource(R.drawable.ic_notifications_button);
        } else {
            holder.imgBtnSentNotificacion.setImageResource(R.drawable.ic_turn_notifications_off_button);
        }

        holder.imgBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerInteractionListUsersFollowed.unFollow(holder.user.getEmail());
            }
        });

        holder.imgBtnSentNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Follow follow=new Follow();
                follow.setUserFollowed(holder.user.getEmail());
                if (holder.user.isSentNotificacion()){
                    follow.setSentNotificacion(0);
                }else{
                    follow.setSentNotificacion(1);
                }
                listenerInteractionListUsersFollowed.setSendNotificacion(follow);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void unfollow(String userFollowedSelectedToDelete) {
        for (int i=0;i< mValues.size();i++){
            User userToUnFollow=mValues.get(i);
            if (TextUtils.equals(userToUnFollow.getEmail(),userFollowedSelectedToDelete)){
                mValues.remove(userToUnFollow);
                break;
            }
        }
        if (mValues.size()==0){
            listenerInteractionListUsersFollowed.showMessage("Ya no sigues a ningún usuario");
        }
        notifyDataSetChanged();
    }

    public void setSendNotificacionFrom(String userFollowedSelectedToSetSendNotificacion) {
        for (int i=0;i< mValues.size();i++){
            User userToUpdate=mValues.get(i);
            if (TextUtils.equals(userToUpdate.getEmail(),userFollowedSelectedToSetSendNotificacion)){
                mValues.remove(userToUpdate);
                if (userToUpdate.isSentNotificacion()){
                    userToUpdate.setSentNotificacion(false);
                }else{
                    userToUpdate.setSentNotificacion(true);
                }
                mValues.add(i,userToUpdate);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View v;
        @BindView(R.id.imgProfileUser)
        CircleImageView imgProfileUser;
        @BindView(R.id.txtUserName)
        TextView txtUserName;
        @BindView(R.id.txtLugar)
        TextView txtLugar;
        @BindView(R.id.imgBtnSentNotificacion)
        ImageButton imgBtnSentNotificacion;
        @BindView(R.id.imgBtnFollow)
        ImageButton imgBtnFollow;
        public User user;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            v = view;
        }
    }
}
