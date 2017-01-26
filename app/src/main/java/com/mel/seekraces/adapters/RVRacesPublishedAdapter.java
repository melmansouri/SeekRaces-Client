package com.mel.seekraces.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.interfaces.OnListFragmentInteractionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RVRacesPublishedAdapter extends RecyclerView.Adapter<RVRacesPublishedAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RVRacesPublishedAdapter(List<Event> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_racespublished, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.imgRace.setImageBitmap(holder.mItem.getBitmap());
        holder.txtNameRace.setText(holder.mItem.getName());
        holder.txtCountryCity.setText(holder.mItem.getCountry()+", "+holder.mItem.getCity());
        SimpleDateFormat formato =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", Locale.getDefault());
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= null;
        try {
            date = sdf.parse(holder.mItem.getDate_time_init());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fecha = formato.format(date);
        holder.txtDate.setText(fecha);
        holder.txtPublicadoPor.setText("Publicado por "+holder.mItem.getUserName());
        holder.txtDistance.setText(holder.mItem.getDistance()+"KM");

        if (holder.mItem.isFavorite()){
            holder.imgBtnLikeRace.setImageResource(R.drawable.ic_favorite);
        }else{
            holder.imgBtnLikeRace.setImageResource(R.drawable.ic_not_favorite);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.imgRace)
        ImageView imgRace;
        @BindView(R.id.txtNameRace)
        TextView txtNameRace;
        @BindView(R.id.txtDistance)
        TextView txtDistance;
        @BindView(R.id.txtCountryCity)
        TextView txtCountryCity;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtPublicadoPor)
        TextView txtPublicadoPor;
        @BindView(R.id.imgBtnLikeRace)
        ImageButton imgBtnLikeRace;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            mView = view;
        }
    }
}
