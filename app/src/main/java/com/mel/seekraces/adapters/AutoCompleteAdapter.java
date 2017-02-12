package com.mel.seekraces.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.entities.PlaceAutoComplete;

import java.util.List;

/**
 * Created by void on 11/02/2017.
 */

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable{
    Context context;
    List<String> Places;

    public AutoCompleteAdapter(Context context, List<String> modelsArrayList) {
        super(context, R.layout.item_autocomplete_places, modelsArrayList);
        this.context = context;
        this.Places = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_autocomplete_places, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.txtAutoCompletePlaces);
            rowView.setTag(holder);

        } else
            holder = (ViewHolder) rowView.getTag();


        if (Places.size()>0){
        holder.Place = Places.get(position);


        holder.name.setText(holder.Place);
        }

        return rowView;
    }

    class ViewHolder {
        String Place;
        TextView name;
    }

    @Override
    public int getCount(){
        return Places.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length()>3) {

                    filterResults.values = Places;
                    filterResults.count = Places.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
