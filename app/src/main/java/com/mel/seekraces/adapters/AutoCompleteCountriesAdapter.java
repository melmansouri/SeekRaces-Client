package com.mel.seekraces.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.entities.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 07/02/2017.
 */

public class AutoCompleteCountriesAdapter extends ArrayAdapter<Country> {

    private Context context;
    private List<Country> countries;
    private List<Country> countriesTmp;
    private List<Country> suggestions;
    private int resource;

    public AutoCompleteCountriesAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.countries=objects;
        this.countriesTmp= new ArrayList<>(countries);
        this.suggestions=new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        Country country = countries.get(position);
        if (country != null) {
            TextView lblName = (TextView) view.findViewById(R.id.txtAutoCompleteCountryCity);
            if (lblName != null)
                lblName.setText(country.getName());
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Country) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Country country: countriesTmp) {
                    if (country.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(country);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Country> filterList = (ArrayList<Country>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Country country : filterList) {
                    add(country);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
