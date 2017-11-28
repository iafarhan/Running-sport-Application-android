package views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asocs.sprintmaster.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import adapters.TourPagerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TourActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener ,View.OnClickListener{
    TourPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    private Handler handler;
    private int page = 0;

    private int delay = 1000; //milliseconds
    SignInButton signInButton;
    GoogleApiClient googleApiClient;
    static final int REQ_CODE = 9001;
    Runnable runnable = new Runnable() {
        public void run() {
            if (mCustomPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            mViewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        mCustomPagerAdapter = new TourPagerAdapter(this);
        handler = new Handler();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
       signInButton = (SignInButton) findViewById(R.id.sign_in);
        signInButton.setOnClickListener(this);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).
                addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();

            String imageUrl;

            try {
                imageUrl = account.getPhotoUrl().toString();

                //   Glide.with(this).load(image_url).into(profPic);
            } catch (Exception xe) {

        imageUrl="";
            }
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name);
            bundle.putString("EMAIL", email);
            bundle.putString("IMAGE_URL", imageUrl);

            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else
            Toast.makeText(this, "Failed to sign in, Try again later", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in:
                signIn();

        }

    }
    public void signIn() {
        Toast.makeText(getApplicationContext(),"CLICKED",Toast.LENGTH_SHORT).show();
    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

       /*    private void signOut() {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    updateUi(false);
                }
            });
        }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {

            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(res);
        }
    }

}