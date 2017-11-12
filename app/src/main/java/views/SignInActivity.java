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

public class SignInActivity extends AppCompatActivity {


    TextView name, email;
    ImageView profPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email_adress);
        profPic = (ImageView) findViewById(R.id.profile_pic);
        name.setText(getIntent().getStringExtra("NAME"));
        email.setText(getIntent().getStringExtra("EMAIL"));

        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        if (!imageUrl .equals("")) {

            Glide.with(this).load(getIntent().getStringExtra("IMAGE_URL")).into(profPic);
        } else
            profPic.setImageResource(R.drawable.user_default);
    }


}

