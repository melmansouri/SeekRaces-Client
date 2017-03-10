package com.mel.seekraces.activities.filters;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.mel.seekraces.R;
import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.fragments.DatePickerFragment;
import com.mel.seekraces.interfaces.filters.IFiltersPresenter;
import com.mel.seekraces.interfaces.filters.IFiltersView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

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
    /*@BindView(R.id.spDistancia)
    AppCompatSpinner spDistancia;*/
    @BindView(R.id.edtLugar)
    AppCompatAutoCompleteTextView edtLugar;
    @BindView(R.id.rangeSeekBar)
    RangeSeekBar rangeSeekBar;
    @BindView(R.id.txtRangoDistancias)
    TextView txtRangoDistancias;
    @BindView(R.id.text_input_layout_lugar)
    TextInputLayout textInputLayoutLugar;
    @BindView(R.id.lncityCountry)
    LinearLayout lncityCountry;
    @BindView(R.id.edtNameRace)
    TextInputEditText edtNameRace;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private IFiltersPresenter presenter;
    private DatePickerFragment datePickerFragment;
    private MenuItem item;
    private Menu menu;
    private AutoCompleteAdapter autoCompleteAdapter;
    private Filter filter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        presenter = new FilterPresenterImpl(this);
        filter=getIntent().getParcelableExtra("filter");
        //dtpFechaDesde.setText(Utils.getCurrentDateSpanishString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //spDistancia.setAdapter(UtilsViews.getSpinnerDistanceAdapter(this,R.layout.support_simple_spinner_dropdown_item,false));
        rangeSeekBar.setRangeValues(1, 100);

        txtRangoDistancias.setText("1 km - 100 kms");

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                setTextViewRangoDistancia((int) minValue,(int) maxValue);
            }
        });
        initComponentsWithFilterData(filter);

    }

    @Override
    public void initComponentsWithFilterData(Filter filter){
        String dateInit=Utils.getCurrentDateSpanishString();
        try{
            edtNameRace.setText(filter.getName());
            edtLugar.setText(filter.getPlace());
            String dateInitTmp=filter.getDate_interval_init();
            String dateEnd=filter.getDate_interval_end();
            dateInit=TextUtils.isEmpty(dateInitTmp)?Utils.getCurrentDateSpanishString():Utils.convertDateEnglishToSpanish(dateInitTmp);
            dtpFechaHasta.setText(TextUtils.isEmpty(dateEnd)?getString(R.string.fecha_hasta_default):Utils.convertDateEnglishToSpanish(dateEnd));
            rangeSeekBar.setSelectedMinValue(filter.getDistanceMin()==0?1:filter.getDistanceMin());
            rangeSeekBar.setSelectedMaxValue(filter.getDistanceMax()==0?100:filter.getDistanceMax());
            setTextViewRangoDistancia(filter.getDistanceMin(),filter.getDistanceMax());

        }catch (Exception e){
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
        dtpFechaDesde.setText(dateInit);
    }

    private void setTextViewRangoDistancia(int minValue,int maxValue){
        minValue=minValue==0?1:minValue;
        maxValue=maxValue==0?100:maxValue;
        String kmMin = ( minValue == 1) ?  minValue + " km" :  minValue + " kms";
        String kmMax = ( maxValue == 1) ?  maxValue + " km" :  maxValue + " kms";

        txtRangoDistancias.setText(kmMin.concat(" - ").concat(kmMax));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        this.menu=menu;
        //presenter.showGroupsItemMenu(filter);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showGroupItemMenu(int idGroup, boolean show){
        menu.setGroupVisible(idGroup,show);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;

        return presenter.onOptionsItemSelected(item.getItemId(),generateFilterObject());
    }

    private Filter generateFilterObject(){
        Filter filter = new Filter();
        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
        filter.setPlace(edtLugar.getText().toString().trim());
        //String distance = spDistancia.getSelectedItem().toString();
        /*try {
            filter.setDistance(Integer.valueOf(distance.replace("KM", "")));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        filter.setName(edtNameRace.getText().toString().trim());
        filter.setDistanceMin(rangeSeekBar.getSelectedMinValue().intValue());
        filter.setDistanceMax(rangeSeekBar.getSelectedMaxValue().intValue());
        String fechaDesde = Utils.convertDateSpanishToEnglish(dtpFechaDesde.getText().toString());
        filter.setDate_interval_init(fechaDesde);
        String fechaHasta = Utils.convertDateSpanishToEnglish(dtpFechaHasta.getText().toString());
        filter.setDate_interval_end(fechaHasta);

        return filter;
    }

    @Override
    public void backToListRacesPublished(Filter filter) {

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
        datePickerFragment=null;
        datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSetListener(onDateSetListener);
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
        datePickerFragment=null;
        datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSetListener(onDateSetListener, dtpFechaDesde.getText().toString());
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
