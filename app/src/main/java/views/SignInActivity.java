package views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asocs.sprintmaster.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    LinearLayout profSelection;
    Button signOut;
    SignInButton signIn;
    TextView name, email;
    ImageView profPic;
    GoogleApiClient googleApiClient;
    static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        profSelection = (LinearLayout) findViewById(R.id.Prof_Selection);
        signIn = (SignInButton) findViewById(R.id.login_btn);
        signOut = (Button) findViewById(R.id.logout_btn);
        name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email_adress);
        profPic = (ImageView) findViewById(R.id.profile_pic);
        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        profSelection.setVisibility(View.GONE);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_btn:

                signIn();
                break;
            case R.id.logout_btn:
                signOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void signIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });
    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            String n = account.getDisplayName();
            String e = account.getEmail();

            name.setText(n);
            email.setText(e);
            try {
                String image_url = account.getPhotoUrl().toString();

                Glide.with(this).load(image_url).into(profPic);
            } catch (Exception xe) {
                profPic.setImageResource(R.drawable.user_default);

            }
            updateUi(true);
        } else {
            this.finish();
        }
    }

    private void updateUi(boolean logIn) {

        if (logIn) {

            profSelection.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
        } else {

            profSelection.setVisibility(View.GONE);
            signIn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {

            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(res);
        }
    }
}

