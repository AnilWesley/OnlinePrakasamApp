package com.news.onlineprakasamapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.news.onlineprakasamapp.R;
import com.news.onlineprakasamapp.constants.MyAppPrefsManager;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar progressBar;
    MyAppPrefsManager myAppPrefsManager;
    private String TAG = "SPLASHACTIVITY1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar);
        myAppPrefsManager = new MyAppPrefsManager(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }, 1000);

    }


}
