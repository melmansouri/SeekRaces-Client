package com.mel.seekraces.activities.filters;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.fragments.DatePickerFragment;
import com.mel.seekraces.interfaces.filters.IFiltersView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by void on 29/01/2017.
 */

public class FiltersActivity extends AppCompatActivity implements IFiltersView {
    @BindView(R.id.edtPais)
    TextInputEditText edtPais;
    @BindView(R.id.edtCiudad)
    TextInputEditText edtCiudad;
    @BindView(R.id.dtpFechaDesde)
    TextView dtpFechaDesde;
    @BindView(R.id.dtpFechaHasta)
    TextView dtpFechaHasta;
    @BindView(R.id.edtDistancia)
    TextInputEditText edtDistancia;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_filters);
        sharedPreferencesSingleton=SharedPreferencesSingleton.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        backToListRacesPublished();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void backToListRacesPublished() {
        Filter filter= new Filter();
        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
        filter.setCountry(edtCiudad.getText().toString());
        filter.setCity(edtPais.getText().toString());
        filter.setDistance(edtDistancia.getText().toString());
        Intent intent=new Intent();
        intent.putExtra("filter",filter);
        setResult(RESULT_OK);
    }

    @Override
    @OnClick(R.id.dtpFechaDesde)
    public void showDialogDateFrom() {
        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dtpFechaDesde.setText(dayOfMonth+"-"+month+"-"+year+"-");
            }
        };
        showDatePickerDialog(onDateSetListener);
    }

    @Override
    @OnClick(R.id.dtpFechaHasta)
    public void showDialogDateUntil() {
        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dtpFechaHasta.setText(dayOfMonth+"-"+month+"-"+year+"-");
            }
        };
        showDatePickerDialog(onDateSetListener);
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener onDateSetListener){
        DialogFragment datePickerFragment= new DatePickerFragment(onDateSetListener);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
