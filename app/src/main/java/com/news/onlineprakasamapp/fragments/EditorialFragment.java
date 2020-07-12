package com.news.onlineprakasamapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.activities.SingleEditorialActivity;
import com.news.onlineprakasamapp.adapters.MainEditorialAdapter;
import com.news.onlineprakasamapp.constants.RecyclerItemClickListener;
import com.news.onlineprakasamapp.modals.FullListDetails;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialFragment extends Fragment {


    public EditorialFragment() {
        // Required empty public constructor
    }

    private MainEditorialAdapter mAdapter;
    private List<FullListDetails.ResponseBean> infoNews;
    private LinearLayoutManager mLayoutManager;
    private ShimmerFrameLayout shimmer;
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
        articlesRecycle = view.findViewById(R.id.articlesRecycle);
        emptyView = view.findViewById(R.id.emptyView);


        shimmer.startShimmer();


        getNews();


        return view;


    }


    private void getNews() {


        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<FullListDetails> call = service.processEditorial();
        call.enqueue(new Callback<FullListDetails>() {
            @Override
            public void onResponse(@NonNull Call<FullListDetails> call, @NonNull Response<FullListDetails> response) {

                // Check if the Response is successful
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    FullListDetails fullListDetails = response.body();


                    if (fullListDetails.isStatus()) {
                        infoNews = response.body().getResponse();

                        articlesRecycle.setHasFixedSize(true);

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        // use a linear layout manager
                        articlesRecycle.setLayoutManager(mLayoutManager);

                        // create an Object for Adapter
                        mAdapter = new MainEditorialAdapter(getActivity(), infoNews);

                        // set the adapter object to the Recyclerview
                        articlesRecycle.setAdapter(mAdapter);


                        //set click event
                        articlesRecycle.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), articlesRecycle, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent setIntent = new Intent(getActivity(), SingleEditorialActivity.class);

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