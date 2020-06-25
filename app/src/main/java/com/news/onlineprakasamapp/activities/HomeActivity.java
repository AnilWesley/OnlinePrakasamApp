package com.news.onlineprakasamapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.news.onlineprakasamapp.BuildConfig;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.constants.ConstantValues;
import com.news.onlineprakasamapp.constants.MyAppPrefsManager;
import com.news.onlineprakasamapp.firebase.ForceUpdateChecker;
import com.news.onlineprakasamapp.fragments.EditorialFragment;
import com.news.onlineprakasamapp.fragments.InvestigationandCrimeFragment;
import com.news.onlineprakasamapp.fragments.LatestNewsFragment;
import com.news.onlineprakasamapp.fragments.PrakasamPoliticsFragment;
import com.news.onlineprakasamapp.fragments.StateandNationalFragment;
import com.news.onlineprakasamapp.fragments.TopStoriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ForceUpdateChecker.OnUpdateNeededListener {


    Toolbar toolbar;
    private static final String TAG = "RESPONSE_DATA";

    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    boolean doubleBackToExitPressedOnce = false;
    final Fragment latestNewsFragment = new LatestNewsFragment();
    final Fragment editorialFragment = new EditorialFragment();
    final Fragment prakasamPoliticsFragment = new PrakasamPoliticsFragment();
    final Fragment stateandNationalFragment = new StateandNationalFragment();
    final Fragment topStoriesFragment = new TopStoriesFragment();
    final Fragment investigationandCrimeFragment = new InvestigationandCrimeFragment();


    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = latestNewsFragment;
    MyAppPrefsManager myAppPrefsManager;
    FragmentTransaction transaction;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getResources().getString(R.string.home));

        bottomView.setItemIconTintList(null);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        myAppPrefsManager = new MyAppPrefsManager(HomeActivity.this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        TextView name = (TextView) v.findViewById(R.id.nav_name);
        TextView number = (TextView) v.findViewById(R.id.nav_number);

        name.setText("Welcome to");
        number.setText("Online Prakasam");

        TextView appversion = (TextView) v.findViewById(R.id.appversion);

        appversion.setText("V : " + ConstantValues.getAppVersion(HomeActivity.this));


        BottomNavigationView navigation = findViewById(R.id.bottom_view);
        navigation.setSelectedItemId(R.id.navigation_latest_news);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(this);


        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();


        transaction = getSupportFragmentManager().beginTransaction();




        fm.beginTransaction().replace(R.id.main_container, editorialFragment, "6").hide(editorialFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, investigationandCrimeFragment, "5").hide(investigationandCrimeFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, prakasamPoliticsFragment, "4").hide(prakasamPoliticsFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, topStoriesFragment, "3").hide(topStoriesFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, stateandNationalFragment, "2").hide(stateandNationalFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, latestNewsFragment, "1").commit();




    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_latest_news:

                fm.beginTransaction().replace(R.id.main_container, latestNewsFragment, "1").commit();
                transaction.addToBackStack(null);
                active = latestNewsFragment;
                return true;

            case R.id.navigation_state:


                fm.beginTransaction().replace(R.id.main_container, stateandNationalFragment, "2").commit();
                transaction.addToBackStack(null);
                active = stateandNationalFragment;

                return true;

            case R.id.navigation_editorial:


                fm.beginTransaction().replace(R.id.main_container, editorialFragment, "9").commit();
                transaction.addToBackStack(null);
                active = editorialFragment;
                return true;
            case R.id.navigation_politics:

                fm.beginTransaction().replace(R.id.main_container, prakasamPoliticsFragment, "4").commit();
                transaction.addToBackStack(null);
                active = prakasamPoliticsFragment;
                return true;


        }
        return false;
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;


            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_latest_news) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, latestNewsFragment, "1").commit();
            transaction.addToBackStack(null);
            active = latestNewsFragment;
        } else if (id == R.id.navigation_state) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, stateandNationalFragment, "2").commit();
            transaction.addToBackStack(null);
            active = stateandNationalFragment;
        } else if (id == R.id.navigation_top_stories) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, topStoriesFragment, "3").commit();
            transaction.addToBackStack(null);
            active = topStoriesFragment;
        } else if (id == R.id.navigation_politics) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, prakasamPoliticsFragment, "4").commit();
            transaction.addToBackStack(null);
            active = prakasamPoliticsFragment;
        } else if (id == R.id.navigation_investigation) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, investigationandCrimeFragment, "5").commit();
            transaction.addToBackStack(null);
            active = investigationandCrimeFragment;
        }  else if (id == R.id.navigation_editorial) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, editorialFragment, "6").commit();
            transaction.addToBackStack(null);
            active = editorialFragment;
        }else if (id == R.id.nav_share) {
            ConstantValues.shareMyApp(HomeActivity.this);

        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue.")
                .setPositiveButton("Update",
                        (dialog12, which) ->
                                redirectStore(updateUrl)).setNegativeButton("No, thanks",
                        (dialog1, which) ->
                                dialog1.dismiss()).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
