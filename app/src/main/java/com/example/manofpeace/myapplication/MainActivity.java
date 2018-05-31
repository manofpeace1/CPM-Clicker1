package com.example.manofpeace.myapplication;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    //class for AdMob
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    //MediaPlayer class
    private MediaPlayer moneySound;
    private MediaPlayer coinSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize AdMob AppID
        MobileAds.initialize(this, "ca-app-pub-3278806895948346~9668037504");

        //Admob Banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Load Admob Interstitial (loads new one when closed)
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3278806895948346/1305497608");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        //Gif
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.raw.dollar_sign);
    }

    // Show Interstitial Ad
    public void ShowInterstitial(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast interstitialToast = Toast.makeText(this, "Interstitial Ad is not ready yet", Toast.LENGTH_SHORT);
            interstitialToast.show();
        }
    }



    // play Money Music
    public void toastMe(View view){

        if (moneySound == null) {
            moneySound = MediaPlayer.create(getApplicationContext(), R.raw.money_music);
            moneySound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (moneySound != null) {
                        moneySound.release();
                        moneySound = null;
                    }
                }
            });
        }

        moneySound.start();

                // Toast myToast = Toast.makeText(this, message, duration);
        Toast myToast = Toast.makeText(this, "Hurray!",
                Toast.LENGTH_SHORT);
        myToast.show();
    }

    // Increase counter number
    public void countMe (View view) {

        // play coin sound
        if (coinSound == null) {
            coinSound = MediaPlayer.create(getApplicationContext(), R.raw.coin);
            coinSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (coinSound != null) {
                        coinSound.release();
                        coinSound = null;
                    }
                }
            });
        }

        coinSound.start();

        // Get the text view
        TextView showCountTextView =
                (TextView) findViewById(R.id.textView);

        // Get the value of the text view.
        String countString = showCountTextView.getText().toString();

        // Convert value to a number and increment it
        Integer count = Integer.parseInt(countString);
        count++;

        //Display the new value in the text view.
        showCountTextView.setText(count.toString());


    }





}
