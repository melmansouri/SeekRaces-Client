package com.mel.seekraces.activities.main;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.fragments.racespublished.ListRacesPublishedFragment;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private IMainPresenter presenter;
    private TextView txtUserName;
    private CircleImageView imgProfileUser;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private ProgressDialog progressDialog;
    private ListRacesPublishedFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferencesSingleton=SharedPreferencesSingleton.getInstance(this);
        presenter=new MainPresenterImpl(this,sharedPreferencesSingleton);
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        View view =navView.getHeaderView(0);
        txtUserName=(TextView) view.findViewById(R.id.txtUserName);
        imgProfileUser=(CircleImageView)view.findViewById(R.id.imgProfileUser);
        presenter.fillDataHeaderView();
        navView.getMenu().getItem(0).setChecked(true);
        navView.getMenu().performIdentifierAction(R.id.listEventsPublished, 0);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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
    public void fillNavHeaderImgProfile(final Uri uri) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imgProfileUser.setImageURI(uri);
            }
        });

    }

    @Override
    public void returnBack() {
        super.onBackPressed();
    }

    @Override
    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = UtilsViews.createProgressDialog(this,"Cargando ...");
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void chargeFragmentRacesPublished(Filter filter) {
        fragment=new ListRacesPublishedFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("filter",filter);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }

    @Override
    public void chargeFragmentMyRacesPublished() {

    }

    @Override
    public void chargeFragmentRacesFavorites() {

    }

    @Override
    public void chargeFragmentRacesPrevious() {

    }
}
