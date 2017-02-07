package com.mel.seekraces.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.activities.filters.FiltersActivity;
import com.mel.seekraces.activities.newRace.AddNewRace;
import com.mel.seekraces.activities.signin.SignInActivity;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.fragments.ownRacesPublished.ListOwnRacesPublishedFragment;
import com.mel.seekraces.fragments.racesPublished.ListRacesPublishedFragment;
import com.mel.seekraces.fragments.racesPublishedFavorites.ListRacesPublishedFavoritesFragment;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.OnFragmentInteractionListener;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView, OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private IMainPresenter presenter;
    private TextView txtUserName;
    private CircleImageView imgProfileUser;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private Intent intentActivityResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        presenter = new MainPresenterImpl(this, sharedPreferencesSingleton);
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        View view = navView.getHeaderView(0);
        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        imgProfileUser = (CircleImageView) view.findViewById(R.id.imgProfileUser);
        presenter.fillDataHeaderView();
        navView.getMenu().getItem(0).setChecked(true);
        navView.getMenu().performIdentifierAction(R.id.itemListEventsPublished, 0);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onNavigationItemSelected(id);
        return true;
    }

    @Override
    public void closeDrawerLayout() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void fillNavHeaderTxtUserName(String username) {
        txtUserName.setText(username);
    }

    @Override
    public void fillNavHeaderImgProfile(String namePicture) {
        Glide.with(getApplicationContext()).load(INetworkConnectionApi.BASE_URL_PICTURES + namePicture).error(R.drawable.ic_default_user).into(imgProfileUser);
    }

    @Override
    public void returnBack() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setResult() {
        setResult(RESULT_OK);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout,message);
    }

    @Override
    public void changeTitleActionBar(int idTitle) {
        getSupportActionBar().setTitle(idTitle);
    }

    @Override
    public void startActivityFilters() {
        Intent intent = new Intent(this, FiltersActivity.class);
        startActivityForResult(intent, Constantes.REQUEST_START_FILTERS_FOR_RESULT);
    }

    @Override
    public void showMessageFromFragments(String message) {
        showMessage(message);
    }

    @Override
    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void chargeFragmentRacesPublished(Filter filter) {
        ListRacesPublishedFragment fragment;
        fragment = new ListRacesPublishedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("filter", filter);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void chargeFragmentMyRacesPublished() {
        ListOwnRacesPublishedFragment fragment = new ListOwnRacesPublishedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void chargeFragmentRacesFavorites() {
        ListRacesPublishedFavoritesFragment fragment = new ListRacesPublishedFavoritesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void chargeFragmentRacesPrevious() {

    }

    @Override
    @OnClick(R.id.fab)
    public void goToAddRaceScreen() {
        Intent i = new Intent(this, AddNewRace.class);
        startActivityForResult(i, Constantes.REQUEST_START_ADD_RACE_FOR_RESULT);
    }

    @Override
    public Intent getIntentActivityResult() {
        return intentActivityResult;
    }


    @Override
    public void onListFragmentInteraction(Event item) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentActivityResult = data;
        presenter.activityResult(requestCode, resultCode);
    }
}
