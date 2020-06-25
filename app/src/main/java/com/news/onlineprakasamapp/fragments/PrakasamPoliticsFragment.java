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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.activities.SinglePoliticsActivity;
import com.news.onlineprakasamapp.adapters.MainPrakasamPoliticsAdapter;
import com.news.onlineprakasamapp.constants.RecyclerItemClickListener;
import com.news.onlineprakasamapp.modals.Banners;
import com.news.onlineprakasamapp.modals.PrakasamPolitics;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrakasamPoliticsFragment extends Fragment {


    //private SliderLayout sliderLayout;
    //private PagerIndicator pagerIndicator;
    private List<Banners.ResponseBean> bannerDetailsList;


    public PrakasamPoliticsFragment() {
        // Required empty public constructor
    }


    private MainPrakasamPoliticsAdapter mAdapter;

    private List<PrakasamPolitics.ResponseBean> infoNews;
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        shimmer = view.findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout);
        articlesRecycle = view.findViewById(R.id.articlesRecycle);
        emptyView = view.findViewById(R.id.emptyView);
       /* sliderLayout = (SliderLayout) view.findViewById(R.id.banner_slider1);
        pagerIndicator = (PagerIndicator) view.findViewById(R.id.banner_slider_indicator);*/





        shimmer.startShimmer();

        pDialog = new ProgressDialog(getActivity());

        getNews();



        swipeRefreshLayout.setOnRefreshListener(() -> {

            swipeRefreshLayout.post(() -> {
                        //mSwipeLayout = true;

                        swipeRefreshLayout.setRefreshing(true);
                        shimmer.startShimmer();

                        getNews();


                    }
            );

        });
        swipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.blue);


        return view;


    }

/*
    private void getBanners() {


        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<Banners> call = service.processBanners();
        call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(@NonNull Call<Banners> call, @NonNull Response<Banners> response) {

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    //Log.d(TAG,""+response.toString());
                    assert response.body() != null;
                    Banners banners = response.body();



                    if (banners.isStatus()) {
                        bannerDetailsList = response.body().getResponse();
                        if (bannerDetailsList != null && bannerDetailsList.size() > 0) {
                            for (int i = 0; i < bannerDetailsList.size(); i++) {
                                Log.d(TAG, "" + bannerDetailsList.size());


                            }
                            setupBannerSlider(bannerDetailsList);
                            //get values

                        }
                    } else {
                        Toast.makeText(getActivity(), "" + banners.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<Banners> call, @NonNull Throwable t) {

                Log.d("ResponseF", "" + t);
            }
        });


    }


    private void setupBannerSlider(final List<Banners.ResponseBean> bannerImages) {


        // Initialize new LinkedHashMap<ImageName, ImagePath>
        //final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();

        HashMap<String, String> slider_covers = new HashMap<String, String>();
        //Log.e(TAG,"BANNERSIZE"+bannerImages.size());


        for (int i = 0; i < bannerImages.size(); i++) {
            // Get bannerDetails at given Position from bannerImages List
            Banners.ResponseBean bannerData = bannerImages.get(i);

            // Put Image's Name and URL to the HashMap slider_covers
            slider_covers.put
                    (

                            bannerData.getId(), bannerData.getImage()
                    );


        }

        //Log.d(TAG,"BANNER1"+slider_covers.size());


        for (String name : slider_covers.keySet()) {
            // Initialize DefaultSliderView
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());


            // Set Attributes(Name, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .empty(R.drawable.image_not_available)
                    .error(R.drawable.image_not_available)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);


        }

        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);


        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }


    }*/


    private void getNews() {


        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<PrakasamPolitics> call = service.processPoliticsList();
        call.enqueue(new Callback<PrakasamPolitics>() {
            @Override
            public void onResponse(@NonNull Call<PrakasamPolitics> call, @NonNull Response<PrakasamPolitics> response) {

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    Log.d(TAG, "" + response.toString());
                    assert response.body() != null;
                    PrakasamPolitics fullListDetails = response.body();


                    if (fullListDetails.isStatus()) {
                        infoNews = response.body().getResponse();

                        articlesRecycle.setHasFixedSize(true);

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        // use a linear layout manager
                        articlesRecycle.setLayoutManager(mLayoutManager);

                        // create an Object for Adapter
                        mAdapter = new MainPrakasamPoliticsAdapter(getActivity(), infoNews);

                        // set the adapter object to the Recyclerview
                        articlesRecycle.setAdapter(mAdapter);
                        swipeRefreshLayout.setRefreshing(false);

                        //set click event
                        articlesRecycle.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), articlesRecycle, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent setIntent = new Intent(getActivity(), SinglePoliticsActivity.class);

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
            public void onFailure(@NonNull Call<PrakasamPolitics> call, @NonNull Throwable t) {
                pDialog.dismiss();
                Log.d("ResponseF", "" + t);
            }
        });


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