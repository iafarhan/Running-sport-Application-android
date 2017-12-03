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

import com.asocs.sprintmaster.ContractCp;
import com.asocs.sprintmaster.R;
import com.skyfishjy.library.RippleBackground;

import datamodel.Athlete;
import datamodel.Contract;
import fragments.NavigationFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

        Cursor cursor = resolver.query(ContractCp.ContactsCols.CONTENT_URI, null, null, null, null);
/*
  Cursor cursor=null;
        int x=resolver.delete(ContractCp.ContactsCols.CONTENT_URI,null,null);
*/
        double dist_ = 0;

        int total_ = 0;
        double max = 0;
        int totSecs = 0;
        int totMins = 0;
        if (cursor != null)
            if (cursor.moveToFirst()) {


                do {

                    String timeTemp = cursor.getString(cursor.getColumnIndex("time"));
                    String dis = cursor.getString(cursor.getColumnIndex("distance"));
                    String maxx = cursor.getString(cursor.getColumnIndex("max_speed"));
                    if (Double.valueOf(maxx) > max) {
                        max = Double.valueOf(maxx);
                    }

                    dist_ += Double.valueOf(dis);

                    total_++;

                    String[] timeTempArray = timeTemp.split(":");
                    totSecs += Integer.valueOf(timeTempArray[0]);
                    totMins += Integer.valueOf(timeTempArray[1]);
                    while (totMins > 59) {
                        totMins -= 59;
                        totSecs++;
                    }

                } while (cursor.moveToNext());
            }

        total_miles = (TextView) findViewById(R.id.textView6);
        MinSec = (TextView) findViewById(R.id.textView7);
        total_run = (TextView) findViewById(R.id.textView8);
        max_speed = (TextView) findViewById(R.id.textView9);
        total_miles.setText(String.valueOf(dist_));
        if (totSecs != 0 || totMins != 0)
            if (totMins < 9) {
                MinSec.setText(String.valueOf(totSecs) + ":0" + String.valueOf(totMins));
            } else
                MinSec.setText(String.valueOf(totSecs) + ":" + String.valueOf(totMins));
        else
            MinSec.setText("00:00");


        total_run.setText(String.valueOf(total_));
        max_speed.setText(String.valueOf(max));
        total_miles.setText(String.valueOf(dist_));
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
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();


    }


    public void startBtnClicked(View view) {


        Intent intent = new Intent(this, CountDownActivity.class);
        startActivity(intent);

    }

}



