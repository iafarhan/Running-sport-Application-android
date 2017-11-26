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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

public class CountDownActivity extends FragmentActivity implements OnNavigationListener {
    int currentPage = 0;
    Timer timer;
    TextToSpeech tts;
     long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    long PERIOD_MS = 3000;
    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";
    private static final ArrayList<TransformerItem> TRANSFORM_CLASSES;

    static {
        TRANSFORM_CLASSES = new ArrayList<>();
        TRANSFORM_CLASSES.add(new TransformerItem(DefaultTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(AccordionTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(BackgroundToForegroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(CubeOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(DepthPageTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipHorizontalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(FlipVerticalTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ForegroundToBackgroundTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateDownTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(RotateUpTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ScaleInOutTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(StackTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(TabletTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomInTransformer.class));
        TRANSFORM_CLASSES.add(new TransformerItem(ZoomOutSlideTransformer.class));

    }
   Handler handler;
   Runnable runnable;
    private int mSelectedItem;
    private ViewPager mPager;
    private PageAdapter mAdapter;

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int selectedPage = 0;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(KEY_SELECTED_CLASS);
            selectedPage = savedInstanceState.getInt(KEY_SELECTED_PAGE);
        }

        final ArrayAdapter<TransformerItem> actionBarAdapter = new ArrayAdapter<TransformerItem>(
                getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, TRANSFORM_CLASSES);

        setContentView(R.layout.activity_count_down);

        mAdapter = new PageAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(selectedPage);
        handler = new Handler();
         runnable = new Runnable() {
            public void run() {


                if (currentPage == 0) {
                    tts.speak("ON YOUR MARK", TextToSpeech.QUEUE_FLUSH, null);
                    mPager.setCurrentItem(currentPage, true);
                    currentPage++;

                } else if (currentPage == 1) {
                    tts.speak("GET SET", TextToSpeech.QUEUE_FLUSH, null);
                    mPager.setCurrentItem(currentPage, true);
                    currentPage++;
                } else if (currentPage == 2) {
                    mPager.setCurrentItem(currentPage, true);

                    tts.speak("GO", TextToSpeech.QUEUE_FLUSH, null);
                    currentPage++;

                }
                else if(currentPage==3){


              //    currentPage++;
                    Intent i=new Intent(CountDownActivity.this, MapsActivity.class);
                    startActivity(i);
finish();
/*
handler.removeMessages(0);
                    handler.removeCallbacks(runnable);
*/

                }

            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled


            @Override
            public void run() {
                handler.post(runnable);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(null);
        handler.removeMessages(0);
        timer.cancel();
        timer = null;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
    @Override
    public boolean onNavigationItemSelected(int position, long itemId) {
        mSelectedItem = position;
        try {
            mPager.setPageTransformer(true, TRANSFORM_CLASSES.get(position).clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_CLASS, mSelectedItem);
        outState.putInt(KEY_SELECTED_PAGE, mPager.getCurrentItem());
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String EXTRA_POSITION = "EXTRA_POSITION";
        private static final int[] COLORS = new int[]{0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444};

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int position = getArguments().getInt(EXTRA_POSITION);
            final TextView textViewPosition = (TextView) inflater.inflate(R.layout.fragment_main, container, false);
            if (position == 1) {
                textViewPosition.setText("On your MARK!");


            }
            if (position == 2) {
                textViewPosition.setText("SET");


            }
            if (position == 3) {
                textViewPosition.setText("GO");


            }

            textViewPosition.setBackgroundColor(COLORS[position - 1]);

            return textViewPosition;
        }

    }

    private static final class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.EXTRA_POSITION, position + 1);


            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    private static final class TransformerItem {

        final String title;
        final Class<? extends PageTransformer> clazz;

        public TransformerItem(Class<? extends PageTransformer> clazz) {
            this.clazz = clazz;
            title = clazz.getSimpleName();
        }

        @Override
        public String toString() {
            return title;
        }

    }

}