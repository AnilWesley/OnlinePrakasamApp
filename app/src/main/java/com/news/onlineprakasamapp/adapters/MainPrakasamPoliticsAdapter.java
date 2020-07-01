package com.news.onlineprakasamapp.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.modals.PrakasamPolitics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainPrakasamPoliticsAdapter extends RecyclerView.Adapter<MainPrakasamPoliticsAdapter.MyViewHolder> {

    private List<PrakasamPolitics.ResponseBean> newsModalList;

    Context context;


    public MainPrakasamPoliticsAdapter(Context context1, List<PrakasamPolitics.ResponseBean> newsModalList1) {
        this.context = context1;
        this.newsModalList = newsModalList1;


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                           int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.itemlayoutsmall, parent, false);

        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PrakasamPolitics.ResponseBean newsModal = (PrakasamPolitics.ResponseBean) newsModalList.get(position);

        ((MyViewHolder) holder).tvName.setText(newsModal.getTitle());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = inputFormat.parse(newsModal.getCreated_on());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        String niceDateStr = String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));

        ((MyViewHolder) holder).tvDate.setText(niceDateStr);


        Glide.with(context)
                .load(newsModal.getImage())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((MyViewHolder) holder).imageView.setImageDrawable(resource);
                        ((MyViewHolder) holder).progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                        ((MyViewHolder) holder).progressBar.setVisibility(View.GONE);
                    }

                });


    }


    @Override
    public int getItemCount() {
        return newsModalList == null ? 0 : newsModalList.size();
    }


    //
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDate;
        ImageView imageView;

        ProgressBar progressBar;


        MyViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.newsTitle);
            tvDate = (TextView) v.findViewById(R.id.newsDate);

            imageView = (ImageView) v.findViewById(R.id.newsImage);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);


        }
    }

}