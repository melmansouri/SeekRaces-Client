package com.mel.seekraces.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.interfaces.INetworkConnectionApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RVReviewsAdapter extends RecyclerView.Adapter<RVReviewsAdapter.ViewHolder> {


    private List<Review> mValues;
    private Context c;

    public RVReviewsAdapter(Context context, List<Review> items) {
        mValues = items;
        c = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_reviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.review = mValues.get(position);

        Glide.with(c).load(INetworkConnectionApi.BASE_URL_PICTURES + holder.review.getPhoto_name()).error(R.drawable.default_user).into(holder.imgProfileUser);
        holder.txtUserName.setText(holder.review.getUserName());
        holder.ratingBar.setRating(holder.review.getScore());
        holder.txtfecha.setText(Utils.convertDateEnglishToSpanish(holder.review.getDateOpinion()));
        holder.txtComentario.setText(holder.review.getComment());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View v;
        @BindView(R.id.imgProfileUser)
        CircleImageView imgProfileUser;
        @BindView(R.id.txtUserName)
        TextView txtUserName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.txtfecha)
        TextView txtfecha;
        @BindView(R.id.txtComentario)
        TextView txtComentario;
        public Review review;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            v = view;
        }
    }
}
