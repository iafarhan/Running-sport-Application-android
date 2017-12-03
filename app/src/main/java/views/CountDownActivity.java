package views;

import com.asocs.sprintmaster.R;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;

import io.saeid.fabloading.LoadingView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CountDownActivity extends AppCompatActivity {
    TextToSpeech tts;
    LoadingView mLoadingView;
    TextView textView;
    LinearLayout ll;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        setContentView(R.layout.activity_count_down);
        ll = (LinearLayout) findViewById(R.id.ll);
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        mLoadingView.addAnimation(Color.RED, R.drawable.marks, LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.RED, R.drawable.set, LoadingView.FROM_BOTTOM);
        mLoadingView.addAnimation(Color.WHITE, R.drawable.go, LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.RED, R.drawable.marks, LoadingView.FROM_LEFT);

        textView = (TextView) findViewById(R.id.textView);

        //also you can add listener for getting callback (optional)
        mLoadingView.addListener(new LoadingView.LoadingListener() {
            @Override
            public void onAnimationStart(int currentItemPosition) {

                if (currentItemPosition == 1) {

                    textView.setText("ON YOUR MARKS");

                }
                if (currentItemPosition == 2) {

                    textView.setText("Set");
                    ll.setBackgroundColor(Color.parseColor("#802392"));
                    tts.speak("GET SET", TextToSpeech.QUEUE_ADD, null);

                }
                if (currentItemPosition == 3) {
                    tts.speak("GO", TextToSpeech.QUEUE_ADD, null);

                    textView.setText("Go");


                    ll.setBackgroundColor(Color.parseColor("#F58F29"));
                    Intent i = new Intent(CountDownActivity.this, MapsActivity.class);
                    startActivity(i);

                }

            }

            @Override
            public void onAnimationRepeat(int nextItemPosition) {
            }

            @Override
            public void onAnimationEnd(int nextItemPosition) {
                //         finish();
            }
        });

        mLoadingView.startAnimation();




/*
        Intent i=new Intent(CountDownActivity.this,MapsActivity.class);
        startActivity(i);
*/

    }

    @Override
    protected void onStop() {

       tts.stop();
        super.onStop();
    }
}