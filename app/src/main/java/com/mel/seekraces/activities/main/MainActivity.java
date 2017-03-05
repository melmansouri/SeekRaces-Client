package com.mel.seekraces.activities.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.activities.editProfile.EditProfileActivity;
import com.mel.seekraces.activities.filters.FiltersActivity;
import com.mel.seekraces.activities.newRace.AddNewRaceActivity;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.fragments.detailRace.DetailRaceFragment;
import com.mel.seekraces.fragments.editRace.EditRaceFragment;
import com.mel.seekraces.fragments.ownRacesPublished.ListOwnRacesPublishedFragment;
import com.mel.seekraces.fragments.racesPublished.ListRacesPublishedFragment;
import com.mel.seekraces.fragments.racesPublishedFavorites.ListRacesPublishedFavoritesFragment;
import com.mel.seekraces.fragments.reviews.ReviewsFragment;
import com.mel.seekraces.interfaces.IGenericInterface;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView,IGenericInterface.OnFragmentInteractionListener {

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
    private TextView txtEmail;
    private CircleImageView imgProfileUser;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private Intent intentActivityResult;
    private LinearLayout header_nav;
    private ActionBarDrawerToggle toggle;
    private Fragment actualFragmentActive;

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
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        View view = navView.getHeaderView(0);
        header_nav=(LinearLayout) view.findViewById(R.id.header_nav);
        header_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawerLayout();
                goToEditProfileScreen();
            }
        });
        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        imgProfileUser = (CircleImageView) view.findViewById(R.id.imgProfileUser);
        presenter.fillDataHeaderView();
        setCheckedItemNavView(0);
        navView.getMenu().performIdentifierAction(R.id.itemListEventsPublished, 0);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void backToPreviousFragmentById(int id) {
        getSupportFragmentManager().popBackStackImmediate(getSupportFragmentManager().getBackStackEntryAt(id).getId(),getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void backToPreviousFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void setActualFragmentActive() {
        actualFragmentActive=getSupportFragmentManager().findFragmentById(R.id.frameLayout);
    }

    @Override
    public boolean actualFragmentActiveInstanceOf(Class clase) {
        return (actualFragmentActive!=null && clase.isAssignableFrom(actualFragmentActive.getClass()));
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
    public void fillNavHeaderTxtEmail(String email) {
        txtEmail.setText(email);
    }

    @Override
    public void setCheckedItemNavView(int index) {
        navView.getMenu().getItem(index).setChecked(true);
    }

    @Override
    public void fillNavHeaderImgProfile(String namePicture) {
        Glide.with(getApplicationContext()).load(INetworkConnectionApi.BASE_URL_PICTURES + namePicture).error(R.drawable.user_default).into(imgProfileUser);
    }

    @Override
    public void returnBack() {
        //super.onBackPressed();
        finish();
    }

    @Override
    public void exitSession() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(this, "Importante");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage("¿Seguro que quieres salir?");
        builder.show();
        //finish();
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
    public void hideFloatingButton() {
        fab.setVisibility(View.GONE);
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
    }

    @Override
    public void editEvent(Race race) {
        EditRaceFragment fragment;
        fragment = new EditRaceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("race", race);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack("edit").commit();
    }

    @Override
    public void showFloatingButton() {
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void startScreenReviews(int idEvent) {
        ReviewsFragment fragment;
        fragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,Constantes.TAG_REVIEWS_FRAGMENT).addToBackStack("review").commit();
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void showHamburgerIconDrawer(boolean show) {
        toggle.setDrawerIndicatorEnabled(show);
    }

    @Override
    public void setNavigationIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    @Override
    public void setOnClickNavigationToolbar(View.OnClickListener onToolbarNavigationClickListener) {
        toggle.setToolbarNavigationClickListener(onToolbarNavigationClickListener);
    }

    @Override
    public void chargeFragmentRacesPublished(Filter filter) {
        ListRacesPublishedFragment fragment;
        fragment = new ListRacesPublishedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("filter", filter);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,Constantes.TAG_RACES_PUBLISHED_FRAGMENT).commit();
    }

    @Override
    public void chargeFragmentMyRacesPublished() {
        ListOwnRacesPublishedFragment fragment = new ListOwnRacesPublishedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,Constantes.TAG_MY_RACES_PUBLISHED_FRAGMENT).addToBackStack("myRaces").commit();
    }

    @Override
    public void chargeFragmentRacesFavorites() {
        ListRacesPublishedFavoritesFragment fragment = new ListRacesPublishedFavoritesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,Constantes.TAG_RACES_FAVORITES_FRAGMENT).addToBackStack("races").commit();
    }

    @Override
    public void chargeFragmentRacesPrevious() {

    }

    @Override
    @OnClick(R.id.fab)
    public void goToAddRaceScreen() {
        Intent i = new Intent(this, AddNewRaceActivity.class);
        startActivityForResult(i, Constantes.REQUEST_START_ADD_RACE_FOR_RESULT);
    }

    @Override
    public void goToEditProfileScreen() {
        Intent i = new Intent(this, EditProfileActivity.class);
        startActivityForResult(i, Constantes.REQUEST_START_EDIT_PROFILE_FOR_RESULT);
    }

    @Override
    public Intent getIntentActivityResult() {
        return intentActivityResult;
    }

    @Override
    public void setIntentActivityResultToNull() {
        intentActivityResult=null;
    }


    @Override
    public void onListFragmentInteraction(Race item) {
        DetailRaceFragment fragment;
        fragment = new DetailRaceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("race", item);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment,Constantes.TAG_DETAIL_RACES_FRAGMENT).addToBackStack("detail").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentActivityResult = data;
        presenter.activityResult(requestCode, resultCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
