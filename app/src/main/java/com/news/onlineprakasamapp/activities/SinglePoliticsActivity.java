package com.news.onlineprakasamapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.constants.ConstantValues;
import com.news.onlineprakasamapp.constants.MyAppPrefsManager;
import com.news.onlineprakasamapp.modals.SingleDetails;
import com.news.onlineprakasamapp.retrofit.ApiInterface;
import com.news.onlineprakasamapp.retrofit.RetrofitClientInstance;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePoliticsActivity extends AppCompatActivity {

    String TAG = "Articles";
    @BindView(R.id.textTitle1)
    TextView textTitle1;
    @BindView(R.id.textImage)
    ImageView textImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textDate)
    TextView textDate;
    @BindView(R.id.textDesc)
    WebView textDesc;

    private List<SingleDetails.ResponseBean> infoList;


    private String news_id;
    private String imagePath = "http://apnewsnviews.com/onlineprakasam/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.politics));
        Intent i = getIntent();
        news_id = i.getStringExtra("news_id");


        getNews();


    }


    public void getNews() {


        String url = "http://apnewsnviews.com/onlineprakasam/api/view_prakasam_politics/" + news_id;
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<SingleDetails> call = service.processSinglePolitics(url);
        call.enqueue(new Callback<SingleDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SingleDetails> call, @NonNull Response<SingleDetails> response) {


                // Check if the Response is successful
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    SingleDetails articlesData = response.body();


                    if (articlesData.isStatus()) {
                        infoList = response.body().getResponse();
                        if (infoList != null && infoList.size() > 0) {
                            for (int j = 0; j < infoList.size(); j++) {


                                textDate.setText("Published on : " + ConstantValues.getFormattedDate(MyAppPrefsManager.DD_MMM_YYYY_DATE_FORMAT, infoList.get(0).getCreated_on()));
                                textTitle1.setText(infoList.get(0).getTitle());


                                //Font must be placed in assets/fonts folder
                                String text = "<html><style type='text/css'>@font-face { font-family: Mandali-Regular; src: url('fonts/Mandali-Regular.ttf'); } body p {font-family: Mandali-Regular;}</style>"
                                        + "<body >" + "<p align=\"justify\" style=\"font-size: 24px; font-family: Mandali-Regular;\">" + infoList.get(0).getDescription() + "</p> " + "</body></html>";

                                textDesc.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "utf-8", null);

                                Glide.with(SinglePoliticsActivity.this)
                                        .load(imagePath + infoList.get(0).getImage())
                                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                        .into(new CustomTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                textImage.setImageDrawable(resource);
                                                progressBar.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                                progressBar.setVisibility(View.GONE);
                                            }

                                        });


                            }
                            //get values

                        }

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<SingleDetails> call, @NonNull Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);


        // return true so that the menu pop up is opened
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            // do your code

            try {
                // shorten the link
                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://apnewsnviews.com/" + infoList.get(0).getId() + "politics"))// manually
                        .setDomainUriPrefix("https://onlineprakasam.page.link")
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                                .build())
                        .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(infoList.get(0).getTitle())
                                .setImageUrl(Uri.parse(imagePath + infoList.get(0).getImage()))

                                .setDescription(getResources().getString(R.string.access__news))

                                .build())
                        .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Short link created
                                Uri shortLink = Objects.requireNonNull(task.getResult()).getShortLink();
                                Uri flowchartLink = task.getResult().getPreviewLink();


                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);

                                assert shortLink != null;
                                intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                                intent.putExtra("title", "politics");
                                intent.setType("text/plain");
                                startActivity(intent);


                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();

            }

            return true;

        } else if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
