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
import com.news.onlineprakasamapp.activities.SingleStateandNationalActivity;
import com.news.onlineprakasamapp.adapters.MainStateandNationalAdapter;
import com.news.onlineprakasamapp.constants.RecyclerItemClickListener;
import com.news.onlineprakasamapp.modals.StateandNational;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateandNationalFragment extends Fragment {




    public StateandNationalFragment() {
        // Required empty public constructor
    }


    private MainStateandNationalAdapter mAdapter;

    private List<StateandNational.ResponseBean> infoNews;
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






    private void getNews() {


        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<StateandNational> call = service.processStateandNational();
        call.enqueue(new Callback<StateandNational>() {
            @Override
            public void onResponse(@NonNull Call<StateandNational> call, @NonNull Response<StateandNational> response) {

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    Log.d(TAG, "" + response.toString());
                    assert response.body() != null;
                    StateandNational fullListDetails = response.body();


                    if (fullListDetails.isStatus()) {
                        infoNews = response.body().getResponse();

                        articlesRecycle.setHasFixedSize(true);

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        // use a linear layout manager
                        articlesRecycle.setLayoutManager(mLayoutManager);

                        // create an Object for Adapter
                        mAdapter = new MainStateandNationalAdapter(getActivity(), infoNews);

                        // set the adapter object to the Recyclerview
                        articlesRecycle.setAdapter(mAdapter);
                        swipeRefreshLayout.setRefreshing(false);

                        //set click event
                        articlesRecycle.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), articlesRecycle, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent setIntent = new Intent(getActivity(), SingleStateandNationalActivity.class);

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
            public void onFailure(@NonNull Call<StateandNational> call, @NonNull Throwable t) {
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