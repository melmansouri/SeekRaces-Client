package com.mel.seekraces.activities.filters;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.fragments.DatePickerFragment;
import com.mel.seekraces.interfaces.filters.IFiltersPresenter;
import com.mel.seekraces.interfaces.filters.IFiltersView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by void on 29/01/2017.
 */

public class FiltersActivity extends AppCompatActivity implements IFiltersView {
    @BindView(R.id.dtpFechaDesde)
    TextView dtpFechaDesde;
    @BindView(R.id.dtpFechaHasta)
    TextView dtpFechaHasta;
    @BindView(R.id.spDistancia)
    AppCompatSpinner spDistancia;
    @BindView(R.id.edtLugar)
    AppCompatAutoCompleteTextView edtLugar;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private IFiltersPresenter presenter;
    private DialogFragment datePickerFragment;
    private MenuItem item;
    private AutoCompleteAdapter autoCompleteAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        presenter = new FilterPresenterImpl(this);
        dtpFechaDesde.setText(Utils.getCurrentDateSpanishString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spDistancia.setAdapter(UtilsViews.getSpinnerDistanceAdapter(this,R.layout.support_simple_spinner_dropdown_item,false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        return presenter.onOptionsItemSelected(item.getItemId());
    }

    @Override
    public void backToListRacesPublished() {
        Filter filter = new Filter();
        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
        filter.setPlace(edtLugar.getText().toString().trim());
        String distance = spDistancia.getSelectedItem().toString();
        try{
            filter.setDistance(Integer.valueOf(distance.replace("KM", "")));
        }catch (Exception e){
            e.printStackTrace();
        }
        String fechaDesde = Utils.convertDateSpanishToEnglish(dtpFechaDesde.getText().toString());
        filter.setDate_interval_init(fechaDesde);
        String fechaHasta = Utils.convertDateSpanishToEnglish(dtpFechaHasta.getText().toString());
        filter.setDate_interval_end(fechaHasta);
        Intent intent = new Intent();
        intent.putExtra("filter", filter);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void finishActivity() {
        //NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    @Override
    @OnClick(R.id.dtpFechaDesde)
    public void showDialogDateFrom() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dtpFechaDesde.setText(Utils.getCorrectFormatDateSpanish(dayOfMonth, month, year));
            }
        };
        datePickerFragment = new DatePickerFragment(onDateSetListener);
        showDatePickerDialog();
    }

    @Override
    @OnClick(R.id.dtpFechaHasta)
    public void showDialogDateUntil() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dtpFechaHasta.setText(Utils.getCorrectFormatDateSpanish(dayOfMonth, month, year));
            }
        };
        datePickerFragment = new DatePickerFragment(onDateSetListener, dtpFechaDesde.getText().toString());
        showDatePickerDialog();
    }

    @Override
    public boolean retunSuperOnOptionsItemSelected() {
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog() {
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    @OnTextChanged(R.id.edtLugar)
    public void onTextChangedPlaces() {
        presenter.onTextChangedPlaces(edtLugar.getText().toString());
    }

    @Override
    public AutoCompleteAdapter getAdapterAutoComplete() {
        return autoCompleteAdapter;
    }

    @Override
    public void initAdapterAutoComplete(PlacePredictions placePredictions) {
        autoCompleteAdapter = new AutoCompleteAdapter(this, placePredictions.getPlaces());
        edtLugar.setAdapter(autoCompleteAdapter);
    }

    @Override
    public void resetAdapterAutoComplete(PlacePredictions placePredictions) {
        autoCompleteAdapter.clear();
        autoCompleteAdapter.addAll(placePredictions.getPlaces());
        autoCompleteAdapter.notifyDataSetChanged();
        edtLugar.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
