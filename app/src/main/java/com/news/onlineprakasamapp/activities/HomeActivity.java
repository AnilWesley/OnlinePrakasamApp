package com.news.onlineprakasamapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.news.onlineprakasamapp.BuildConfig;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.constants.ConstantValues;
import com.news.onlineprakasamapp.constants.MyAppPrefsManager;
import com.news.onlineprakasamapp.firebase.ForceUpdateChecker;
import com.news.onlineprakasamapp.fragments.EditorialFragment;
import com.news.onlineprakasamapp.fragments.InvestigationandCrimeFragment;
import com.news.onlineprakasamapp.fragments.LatestNewsFragment1;
import com.news.onlineprakasamapp.fragments.PrakasamPoliticsFragment;
import com.news.onlineprakasamapp.fragments.StateandNationalFragment;
import com.news.onlineprakasamapp.fragments.TopStoriesFragment;
import com.news.onlineprakasamapp.modals.FullListDetails;
import com.news.onlineprakasamapp.modals.MenuItem1;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ForceUpdateChecker.OnUpdateNeededListener {


    Toolbar toolbar;
    private static final String TAG = "RESPONSE_DATA";

    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    boolean doubleBackToExitPressedOnce = false;
    final Fragment latestNewsFragment = new LatestNewsFragment1();
    final Fragment editorialFragment = new EditorialFragment();
    final Fragment prakasamPoliticsFragment = new PrakasamPoliticsFragment();
    final Fragment stateandNationalFragment = new StateandNationalFragment();
    final Fragment topStoriesFragment = new TopStoriesFragment();
    final Fragment investigationandCrimeFragment = new InvestigationandCrimeFragment();


    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = latestNewsFragment;
    MyAppPrefsManager myAppPrefsManager;
    FragmentTransaction transaction;
    private List<FullListDetails.ResponseBean> infoNews;
    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 2;

    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    MenuItem1 menuItem;

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


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


   /*     if (savedInstanceState == null) {
            // Create new fragment to display a progress spinner while the data set for the
            // RecyclerView is populated.
            Fragment loadingScreenFragment = new LoadingScreenFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_container, loadingScreenFragment);

            // Commit the transaction.
            transaction.commit();


        }*/
        // Update the RecyclerView item's list with menu items.
        getNews();
        // Update the RecyclerView item's list with native ads.
        loadNativeAds();

        transaction = getSupportFragmentManager().beginTransaction();


        fm.beginTransaction().replace(R.id.main_container, editorialFragment, "6").hide(editorialFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, investigationandCrimeFragment, "5").hide(investigationandCrimeFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, prakasamPoliticsFragment, "4").hide(prakasamPoliticsFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, topStoriesFragment, "3").hide(topStoriesFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, stateandNationalFragment, "2").hide(stateandNationalFragment).commit();
        fm.beginTransaction().replace(R.id.main_container, latestNewsFragment, "1").commit();


    }


    public List<Object> getRecyclerViewItems() {
        return mRecyclerViewItems;
    }

    private void insertAdsInMenuItems() {
        if (mNativeAds.size() <= 0) {
            return;
        }

        int offset = (mRecyclerViewItems.size() / mNativeAds.size()) + 1;
        int index = 3;
        for (UnifiedNativeAd ad : mNativeAds) {
            mRecyclerViewItems.add(index, ad);
            index = index + offset;
        }
        loadMenu();
    }

    private void loadMenu() {
        // Create new fragment and transaction
        Fragment newFragment = new LatestNewsFragment1();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.main_container, newFragment);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }


    private void getNews() {

        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<FullListDetails> call = service.processLatestNews();
        call.enqueue(new Callback<FullListDetails>() {
            @Override
            public void onResponse(@NonNull Call<FullListDetails> call, @NonNull retrofit2.Response<FullListDetails> response) {

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    Log.d(TAG, "" + response.toString());
                    assert response.body() != null;
                    FullListDetails fullListDetails = response.body();


                    if (fullListDetails.isStatus()) {
                        infoNews = response.body().getResponse();

                        for (FullListDetails.ResponseBean infoo : infoNews) {
                            String id = infoo.getId();
                            String language_id = infoo.getLanguage_id();
                            String title = infoo.getTitle();
                            String description = infoo.getDescription();
                            String image_path = infoo.getImage_path();
                            String status = infoo.getStatus();
                            String created_on = infoo.getCreated_on();
                            menuItem = new MenuItem1(id, language_id, title, description, image_path, status, created_on, "");
                            mRecyclerViewItems.add(menuItem);
                        }

                        Log.d(TAG, "onResponse: " + mRecyclerViewItems.size());


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FullListDetails> call, @NonNull Throwable t) {

                Log.d("ResponseF", "" + t);
            }
        });


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
        } else if (id == R.id.navigation_editorial) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fm.beginTransaction().replace(R.id.main_container, editorialFragment, "6").commit();
            transaction.addToBackStack(null);
            active = editorialFragment;
        } else if (id == R.id.nav_share) {
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
