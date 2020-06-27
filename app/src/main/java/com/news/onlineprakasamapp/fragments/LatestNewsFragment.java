package com.news.onlineprakasamapp.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.activities.SingleNewsActivity;
import com.news.onlineprakasamapp.adapters.MainSmallAdapter;
import com.news.onlineprakasamapp.adapters.RecyclerViewAdapter;
import com.news.onlineprakasamapp.constants.RecyclerItemClickListener;
import com.news.onlineprakasamapp.modals.Banners;
import com.news.onlineprakasamapp.modals.FullListDetails;
import com.news.onlineprakasamapp.modals.MenuItem1;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LatestNewsFragment extends Fragment {


    //private SliderLayout sliderLayout;
    //private PagerIndicator pagerIndicator;
    private List<Banners.ResponseBean> bannerDetailsList;


    public LatestNewsFragment() {
        // Required empty public constructor
    }

    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 1;

    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    MenuItem1 menuItem;


    private MainSmallAdapter mAdapter;

    private List<FullListDetails.ResponseBean> infoNews;
    private LinearLayoutManager mLayoutManager;


    private String TAG = "News";

    private ProgressDialog pDialog;


    private ShimmerFrameLayout shimmer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView articlesRecycle;
    private TextView emptyView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getNews();
        loadNativeAds();*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        MobileAds.initialize(getActivity(), getResources().getString(R.string.admob_app_id));
        shimmer = view.findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout);
        articlesRecycle = view.findViewById(R.id.articlesRecycle);
        emptyView = view.findViewById(R.id.emptyView);
        /*sliderLayout = (SliderLayout) view.findViewById(R.id.banner_slider1);
        pagerIndicator = (PagerIndicator) view.findViewById(R.id.banner_slider_indicator);
*/


        shimmer.startShimmer();

        pDialog = new ProgressDialog(getActivity());

        getNews();
        //loadNativeAds();

        swipeRefreshLayout.setOnRefreshListener(() -> {


            swipeRefreshLayout.post(() -> {
                        //mSwipeLayout = true;

                        swipeRefreshLayout.setRefreshing(true);
                        shimmer.startShimmer();

                         getNews();
                         //loadNativeAds();


                    }
            );

        });
        swipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.blue);


        return view;


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

                        mRecyclerViewItems.clear();
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

                        articlesRecycle.setHasFixedSize(true);

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        // use a linear layout manager
                        articlesRecycle.setLayoutManager(mLayoutManager);

                        // create an Object for Adapter
                      /*  mAdapter = new MainSmallAdapter(getActivity(), infoNews);

                        // set the adapter object to the Recyclerview
                        articlesRecycle.setAdapter(mAdapter);*/

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mRecyclerViewItems);
                        articlesRecycle.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);

                        //set click event
                        articlesRecycle.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), articlesRecycle, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent setIntent = new Intent(getActivity(), SingleNewsActivity.class);

                                setIntent.putExtra("news_id", infoNews.get(position).getId());
                                setIntent.putExtra("title", getResources().getString(R.string.news));
                                setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(setIntent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));


                    }
                    //get values

                    else {
                        articlesRecycle.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    shimmer.stopShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FullListDetails> call, @NonNull Throwable t) {
                pDialog.dismiss();
                Log.d("ResponseF", "" + t);
            }
        });


    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), getString(R.string.admob_native_id));
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


    private void insertAdsInMenuItems() {
        if (mNativeAds.size() <= 0) {
            return;
        }

        int offset = (mRecyclerViewItems.size() / mNativeAds.size()) + 1;
        int index = 4;
        for (UnifiedNativeAd ad : mNativeAds) {
            mRecyclerViewItems.add(index, ad);
            index = index + offset;
        }

    }





    @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmer.stopShimmer();
    }


}