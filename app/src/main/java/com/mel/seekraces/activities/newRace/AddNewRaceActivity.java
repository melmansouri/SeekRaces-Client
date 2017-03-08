package com.mel.seekraces.activities.newRace;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.fragments.DatePickerFragment;
import com.mel.seekraces.fragments.TimePickerFragment;
import com.mel.seekraces.interfaces.newRace.IAddNewRacePresenter;
import com.mel.seekraces.interfaces.newRace.IAddNewRaceView;
import com.mel.seekraces.tasks.EncodeImageTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddNewRaceActivity extends AppCompatActivity implements IAddNewRaceView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgRace)
    ImageView imgRace;
    @BindView(R.id.edtNameRace)
    TextInputEditText edtNameRace;
    @BindView(R.id.text_input_layout_name_race)
    TextInputLayout textInputLayoutNameRace;
    @BindView(R.id.text_input_layout_lugar)
    TextInputLayout textInputLayoutLugar;
    @BindView(R.id.txtFechaDesde)
    TextView txtFechaDesde;
    @BindView(R.id.dtpFechaDesde)
    TextView dtpFechaDesde;
    @BindView(R.id.lnFechaIniCarrera)
    LinearLayout lnFechaIniCarrera;
    @BindView(R.id.edtWeb)
    TextInputEditText edtWeb;
    @BindView(R.id.text_input_layout_web)
    TextInputLayout textInputLayoutWeb;
    @BindView(R.id.edtDescription)
    TextInputEditText edtDescription;
    @BindView(R.id.text_input_layout_description)
    TextInputLayout textInputLayoutDescription;
    @BindView(R.id.lnDataRace)
    ScrollView lnDataRace;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tipHora)
    TextView tipHora;
    @BindView(R.id.edtLugar)
    AppCompatAutoCompleteTextView edtLugar;
    @BindView(R.id.txtHora)
    TextView txtHora;
    @BindView(R.id.lnFechaHora)
    LinearLayout lnFechaHora;
    @BindView(R.id.spDistancia)
    AppCompatSpinner spDistancia;

    private Intent intentOnActivityResult;
    private Bitmap imageBitmap;
    private IAddNewRacePresenter presenter;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private MenuItem item;
    private AutoCompleteAdapter autoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_race);
        ButterKnife.bind(this);
        presenter = new AddNewRacePresenterImpl(this);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        dtpFechaDesde.setText(Utils.getCurrentDateSpanishString());
        tipHora.setText(Utils.getCurrentTimeString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spDistancia.setAdapter(UtilsViews.getSpinnerDistanceAdapter(this,R.layout.support_simple_spinner_dropdown_item,true));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_race, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        return presenter.onOptionsItemSelected(item.getItemId());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentOnActivityResult = data;
        presenter.activityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode,grantResults);
    }


    @Override
    @OnClick(R.id.fab)
    public void selectPictureRace() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(this, getString(R.string.elige_opcion));
        builder.setItems(R.array.option_dialog_picture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.selectOptionDialogPicture(getResources().getStringArray(R.array.option_dialog_picture), i);
            }
        });

        builder.show();
    }

    @Override
    public void showProgress() {
        UtilsViews.closeKeyBoard(this);
        UtilsViews.disableScreen(this);
        progressBar.setVisibility(View.VISIBLE);
        hideComponents();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        //showComponents();
        UtilsViews.enableSreen(this);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout, message);
    }

    @Override
    public void showErrorName(String message) {
        textInputLayoutNameRace.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtNameRace, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorName() {
        textInputLayoutNameRace.setErrorEnabled(false);
    }


    @Override
    public void showErrorPlaces(String message) {
        textInputLayoutLugar.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtLugar, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorPlaces() {
        textInputLayoutLugar.setErrorEnabled(false);
    }

    @Override
    @OnTextChanged(R.id.edtLugar)
    public void onTextChangedPlaces() {
        presenter.onTextChangedPlaces(edtLugar.getText().toString());
    }

    @Override
    public void showComponents() {
        lnDataRace.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponents() {
        lnDataRace.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.dtpFechaDesde)
    public void showDialogDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dtpFechaDesde.setText(Utils.getCorrectFormatDateSpanish(dayOfMonth, month, year));
            }
        };
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSetListener(onDateSetListener);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    @OnClick(R.id.tipHora)
    public void showDialogTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tipHora.setText(Utils.getCorrectFormatTime(hourOfDay, minute));
            }
        };
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnTimeSetListener(onTimeSetListener);
        newFragment.show(getSupportFragmentManager(), "TimePicker");
    }

    @Override
    public void returnToMainScreen(String message) {
        Intent intent = new Intent();
        intent.putExtra("addRace", message);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean retunSuperOnOptionsItemSelected() {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void fillImageViewFromCamera() {
        Bundle extras = intentOnActivityResult.getExtras();
        imageBitmap = (Bitmap) extras.get("data");
        imgRace.setImageBitmap(imageBitmap);
    }

    @Override
    public void fillImageViewFromGallery() {
        Uri uriImage = intentOnActivityResult.getData();
        Bitmap bitmaptmp = Utils.getBitmapFromUriImage(this, uriImage);
        //imageBitmap = Bitmap.createScaledBitmap(bitmaptmp, (int) (bitmaptmp.getWidth() * 0.5), (int) (bitmaptmp.getHeight() * 0.5), true);
        if (bitmaptmp!=null){
            imageBitmap= bitmaptmp;
            imgRace.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void openCamera() {
        UtilsViews.openCamera(this);
    }

    @Override
    public void openGalery() {
        UtilsViews.openGallery(this);
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
    public void addRace() {
        showProgress();

        final Race race = new Race();
        new EncodeImageTask(this, imageBitmap) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                race.setName(edtNameRace.getText().toString().trim());
                String distance = spDistancia.getSelectedItem().toString();
                race.setDistance(Integer.valueOf(distance.replace("KM","")));
                race.setPlace(edtLugar.getText().toString().trim());
                String fecha = Utils.convertDateSpanishToEnglish(dtpFechaDesde.getText().toString());
                String hora = tipHora.getText().toString();
                race.setDate_time_init(fecha.concat(" ").concat(hora));
                race.setImageBase64(s);
                race.setWeb(edtWeb.getText().toString().trim());
                race.setDescription(edtDescription.getText().toString());
                race.setUserEmail(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                presenter.addRace(Utils.isOnline(AddNewRaceActivity.this), race);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
