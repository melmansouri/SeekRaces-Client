package com.mel.seekraces.activities.detailRace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mel.seekraces.R;
import com.mel.seekraces.deserializers.UserDeserializer;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.INetworkConnectionApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity que inicia cuando pluso en la notificacion para ver los
 * detalles de la carrera
 * Created by moha on 9/03/17.
 */

public class DetailRaceActivity extends AppCompatActivity {

    @BindView(R.id.imgRace)
    ImageView imgRace;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.distancia)
    TextView distancia;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.lugar)
    TextView lugar;
    @BindView(R.id.txtLugar)
    TextView txtLugar;
    @BindView(R.id.fecha)
    TextView fecha;
    @BindView(R.id.txtFecha)
    TextView txtFecha;
    @BindView(R.id.web)
    TextView web;
    @BindView(R.id.txtWeb)
    TextView txtWeb;
    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.txtDescripcion)
    TextView txtDescripcion;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_race);
        ButterKnife.bind(this);
        try{
            String raceJson=getIntent().getStringExtra("race");
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
            Gson gson = gsonBuilder.create();
            Race race = gson.fromJson(raceJson, Race.class);
            if (race!=null){
                getSupportActionBar().hide();
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailRaceActivity.super.onBackPressed();
                    }
                });
                Glide.with(this).load(INetworkConnectionApi.BASE_URL_PICTURES+race.getImageName()).error(R.drawable.default_race).into(imgRace);

                collapsingToolbar.setTitle(race.getName());
                txtDistance.setText(race.getDistance() + "KM");
                SimpleDateFormat formato =
                        new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale.getDefault());
                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                String fecha="";
                try {
                    date = sdf.parse(race.getDate_time_init());
                    fecha = formato.format(date);
                } catch (Exception e) {
                    try {
                        date = sdf.parse(race.getDate_time_init()+":00");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    fecha = formato.format(date);
                    e.printStackTrace();
                }

                txtFecha.setText(fecha);
                txtLugar.setText(race.getPlace());
                txtWeb.setText(race.getWeb());
                txtDescripcion.setText(race.getDescription());

            }else{
                throw new Exception();
            }

        }catch (Exception e){
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }
}
