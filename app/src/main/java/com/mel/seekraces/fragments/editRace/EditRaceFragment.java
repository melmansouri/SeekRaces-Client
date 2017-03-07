package com.mel.seekraces.fragments.editRace;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.crash.FirebaseCrash;
import com.mel.seekraces.R;
import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.fragments.DatePickerFragment;
import com.mel.seekraces.fragments.TimePickerFragment;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRacePresenter;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRaceView;
import com.mel.seekraces.tasks.EncodeImageTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by void on 26/02/2017.
 */

public class EditRaceFragment extends Fragment implements IEditRaceView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgRace)
    ImageView imgRace;
    @BindView(R.id.edtLugar)
    AppCompatAutoCompleteTextView edtLugar;
    @BindView(R.id.text_input_layout_lugar)
    TextInputLayout textInputLayoutLugar;
    @BindView(R.id.txtFechaDesde)
    TextView txtFechaDesde;
    @BindView(R.id.dtpFechaDesde)
    TextView dtpFechaDesde;
    @BindView(R.id.lnFechaIniCarrera)
    LinearLayout lnFechaIniCarrera;
    @BindView(R.id.tipHora)
    TextView tipHora;
    @BindView(R.id.lnFechaHora)
    LinearLayout lnFechaHora;
    @BindView(R.id.edtWeb)
    TextInputEditText edtWeb;
    @BindView(R.id.text_input_layout_web)
    TextInputLayout textInputLayoutWeb;
    @BindView(R.id.edtDescription)
    TextInputEditText edtDescription;
    @BindView(R.id.text_input_layout_description)
    TextInputLayout textInputLayoutDescription;
    @BindView(R.id.edtNameRace)
    TextInputEditText edtNameRace;
    @BindView(R.id.text_input_layout_name_race)
    TextInputLayout textInputLayoutNameRace;
    @BindView(R.id.spDistancia)
    AppCompatSpinner spDistancia;
    @BindView(R.id.lnNameDistance)
    LinearLayout lnNameDistance;
    @BindView(R.id.lnDataRace)
    ScrollView lnDataRace;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.txtHora)
    TextView txtHora;
    private Race raceFromList;
    private IGenericInterface.OnFragmentInteractionListener mListener;
    private IEditRacePresenter presenter;
    private Intent intentOnActivityResult;
    private Bitmap imageBitmap;
    private AutoCompleteAdapter autoCompleteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        raceFromList = getArguments().getParcelable("race");
        mListener.changeTitleActionBar(R.string.title_edit_race);
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
        presenter = new EditRaceFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_race, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            spDistancia.setAdapter(UtilsViews.getSpinnerDistanceAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, true));
            imgRace.setImageBitmap(raceFromList.getBitmap());
            imageBitmap = raceFromList.getBitmap();
            edtNameRace.setText(raceFromList.getName());
            edtLugar.setText(raceFromList.getPlace());
            edtWeb.setText(raceFromList.getWeb());
            edtDescription.setText(raceFromList.getDescription());
            String[] fechaHora = raceFromList.getDate_time_init().split(" ");
            String fecha = fechaHora[0];
            String hora = fechaHora[1];
            dtpFechaDesde.setText(Utils.convertDateEnglishToSpanish(fecha));
            tipHora.setText(hora);
            int positionSelectedSpinnerDistance = raceFromList.getDistance() - 1;
            spDistancia.setSelection(positionSelectedSpinnerDistance);
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment_edit_race, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onOptionsItemSelected(id);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentOnActivityResult = data;
        presenter.activityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.setOnClickNavigationToolbar(null);
        mListener.showHamburgerIconDrawer(true);
        mListener.setDrawerEnabled(true);
        mListener.showFloatingButton();
    }

    @Override
    @OnClick(R.id.fab)
    public void selectPictureRace() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(getContext(), getString(R.string.elige_opcion));
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
        UtilsViews.closeKeyBoard(getActivity());
        UtilsViews.disableScreen(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        hideComponents();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        showComponents();
        UtilsViews.enableSreen(getActivity());
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
        datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
        newFragment.show(getActivity().getSupportFragmentManager(), "TimePicker");
    }

    @Override
    public void returnToMainScreen(String message) {
        getActivity().getSupportFragmentManager().popBackStack();
        mListener.showMessageFromFragments(message);
    }


    @Override
    public void finishFragment() {

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
        Bitmap bitmaptmp = Utils.getBitmapFromUriImage(getContext(), uriImage);
        //imageBitmap = Bitmap.createScaledBitmap(bitmaptmp, (int) (bitmaptmp.getWidth() * 0.5), (int) (bitmaptmp.getHeight() * 0.5), true);
        if (bitmaptmp != null) {
            imageBitmap = bitmaptmp;
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
        autoCompleteAdapter = new AutoCompleteAdapter(getContext(), placePredictions.getPlaces());
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
    public void editRace() {
        showProgress();

        final Race race = new Race();
        new EncodeImageTask(getContext(), imageBitmap) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                race.setId(raceFromList.getId());
                race.setName(edtNameRace.getText().toString().trim());
                String distance = spDistancia.getSelectedItem().toString();
                race.setDistance(Integer.valueOf(distance.replace("KM", "")));
                race.setPlace(edtLugar.getText().toString().trim());
                String fecha = Utils.convertDateSpanishToEnglish(dtpFechaDesde.getText().toString());
                String hora = tipHora.getText().toString();
                race.setDate_time_init(fecha.concat(" ").concat(hora));
                race.setImageBase64(s);
                race.setWeb(edtWeb.getText().toString().trim());
                race.setDescription(edtDescription.getText().toString());
                race.setUser(raceFromList.getUser());
                presenter.editRace(Utils.isOnline(getContext()), race);
            }
        }.execute();
    }
}
