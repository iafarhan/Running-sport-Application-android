package views;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asocs.sprintmaster.R;
import com.skyfishjy.library.RippleBackground;

import datamodel.Athlete;
import datamodel.Contract;
import fragments.NavigationFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import views.MapsActivity;

import static datamodel.Contract.table1.CONTENT_URI;
import static datamodel.Contract.table1.KEY_AvgSpeed;
import static datamodel.Contract.table1.KEY_MaxSpeed;
import static datamodel.Contract.table1.KEY_TOTAL_DISTANCE_TRAVELLED;
import static datamodel.Contract.table1.KEY_TOTAL_TIME_TAKEN;
import static datamodel.Contract.table1.TOTAL_RUN;

public class HomeActivity extends AppCompatActivity {
LinearLayout linear_layout;
TextView total_miles;
TextView MinSec;
TextView total_run;
TextView max_speed;
ContentResolver resolver;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        resolver = getContentResolver();
        //CreateBio();
        total_miles = (TextView) findViewById(R.id.textView6);
        MinSec = (TextView) findViewById(R.id.textView7);
        total_run = (TextView) findViewById(R.id.textView8);
        max_speed = (TextView) findViewById(R.id.textView9);
    //    CreateBio();
        //     update(0);
        NavigationFragment navigationFragment = new NavigationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_view, navigationFragment);
        transaction.commit();

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();


  final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();


    }


    public void startBtnClicked(View view){


        Intent intent=new Intent(this, CountDownActivity.class);
        startActivity(intent);

    }

    public void CreateBio()
    {
        Uri mNewUri;
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(KEY_AvgSpeed, "0");
        mNewValues.put(KEY_MaxSpeed, "5");
        mNewValues.put(KEY_TOTAL_DISTANCE_TRAVELLED, "15");
        mNewValues.put(KEY_TOTAL_TIME_TAKEN, "60:30");
        mNewValues.put(TOTAL_RUN, "6");
        mNewUri = resolver.insert(Contract.table1.CONTENT_URI,
                mNewValues
        );

        Cursor cursor=resolver.query(Contract.table1.CONTENT_URI,null, Contract.table1._ID + "=?",
                new String[] { String.valueOf(0) },null);
        Athlete athlete = new Athlete(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        total_miles.setText(athlete.totaldistanceTravelled);
        MinSec.setText(athlete.totaltimeTaken);
        total_run.setText(athlete.totalrun);
        max_speed.setText(athlete.maximumSpeed);

        //total_miles.setText("15");
        //MinSec.setText("60:30");
        //total_run.setText("6");
        //max_speed.setText("5");


    }
    public void update(int id){
        Log.d("before","123");
        Cursor cursor=resolver.query(Contract.table1.CONTENT_URI,null, Contract.table1._ID + "=?",
                new String[] { String.valueOf(id) },null);
        if (cursor.moveToFirst()) {
            Log.d("after_L",cursor.getString(1) );
            Athlete athlete = new Athlete(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

            total_miles.setText(athlete.totaldistanceTravelled);
            MinSec.setText(athlete.totaltimeTaken);
            total_run.setText(athlete.totalrun);
            max_speed.setText(athlete.maximumSpeed);
            Log.d("after_end", "123");
        }

    }




}
