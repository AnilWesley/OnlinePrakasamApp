package com.news.onlineprakasamapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.news.onlineprakasamapp.R;

public class SplashScreenActivity1 extends AppCompatActivity {

    ProgressBar progressBar;
    private String TAG = "SPLASHACTIVITY1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shareLinks();


            }
        }, 1000);


    }


    public void shareLinks() {
        try {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(SplashScreenActivity1.this, pendingDynamicLinkData -> {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            assert deepLink != null;
                            String referlink = deepLink.toString().replace("https://apnewsnviews.com/", "");


                            if (referlink.contains("editorial")) {

                                String strNew = referlink.replace("editorial", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SingleEditorialActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }

                            if (referlink.contains("investigation")) {

                                String strNew = referlink.replace("investigation", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SingleInvestigationActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }


                            if (referlink.contains("news")) {

                                String strNew = referlink.replace("news", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SingleNewsActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }


                            if (referlink.contains("politics")) {

                                String strNew = referlink.replace("politics", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SinglePoliticsActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }


                            if (referlink.contains("stateandnational")) {

                                String strNew = referlink.replace("stateandnational", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SingleStateandNationalActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }


                            if (referlink.contains("topstories")) {

                                String strNew = referlink.replace("topstories", "");
                                Intent intent1 = new Intent(SplashScreenActivity1.this, SingleTopStoriesActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.putExtra("news_id", strNew);
                                startActivity(intent1);
                                finish();


                            }


                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    })
                    .addOnFailureListener(SplashScreenActivity1.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
