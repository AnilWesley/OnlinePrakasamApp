package com.news.onlineprakasamapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.activities.HomeActivity;
import com.news.onlineprakasamapp.adapters.LatestNewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class LatestNewsFragment extends Fragment {


    public LatestNewsFragment() {
        // Required empty public constructor
    }


    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    private RecyclerView articlesRecycle;
    private TextView emptyView;


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

        View view = inflater.inflate(R.layout.fragment_latest, container, false);

        articlesRecycle = view.findViewById(R.id.articlesRecycle);
        emptyView = view.findViewById(R.id.emptyView);

        getNews();



        return view;


    }


    public void getNews() {

        if (mRecyclerViewItems.size() > 0) {
            // in content do not change the layout size of the RecyclerView.
            articlesRecycle.setHasFixedSize(true);

            // Specify a linear layout manager.
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            articlesRecycle.setLayoutManager(layoutManager);

            // Specify an adapter.
            LatestNewsAdapter adapter = new LatestNewsAdapter(getActivity(), mRecyclerViewItems);
            articlesRecycle.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            articlesRecycle.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}