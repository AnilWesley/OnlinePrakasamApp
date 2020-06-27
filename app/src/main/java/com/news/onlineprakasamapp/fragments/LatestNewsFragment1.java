package com.news.onlineprakasamapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.news.onlineprakasamapp.activities.HomeActivity;
import com.news.onlineprakasamapp.adapters.MainSmallAdapter;
import com.news.onlineprakasamapp.adapters.RecyclerViewAdapter;
import com.news.onlineprakasamapp.modals.Banners;

import java.util.ArrayList;
import java.util.List;

public class LatestNewsFragment1 extends Fragment {


    //private SliderLayout sliderLayout;
    //private PagerIndicator pagerIndicator;
    private List<Banners.ResponseBean> bannerDetailsList;


    public LatestNewsFragment1() {
        // Required empty public constructor
    }


    private MainSmallAdapter mAdapter;




    private String TAG = "News";

    private ProgressDialog pDialog;


    private ShimmerFrameLayout shimmer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView articlesRecycle;
    private TextView emptyView;

    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);

        HomeActivity activity = (HomeActivity) getActivity();
        assert activity != null;
        mRecyclerViewItems = activity.getRecyclerViewItems();
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
        /*sliderLayout = (SliderLayout) view.findViewById(R.id.banner_slider1);
        pagerIndicator = (PagerIndicator) view.findViewById(R.id.banner_slider_indicator);
*/


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

    public void getNews() {
        articlesRecycle.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // use a linear layout manager
        articlesRecycle.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        //mAdapter = new MainSmallAdapter(getActivity(), infoNews);

        // set the adapter object to the Recyclerview
        //articlesRecycle.setAdapter(mAdapter);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mRecyclerViewItems);
        articlesRecycle.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);




        //get values


        shimmer.stopShimmer();
        shimmer.setVisibility(View.GONE);
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