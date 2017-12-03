
package views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asocs.sprintmaster.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.view.View.Y;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

@RuntimePermissions

public class MapsActivity extends AppCompatActivity {
    int minutes = 0;
    int totalSecs = 0;
    String min = "00";
    int i=0;

    boolean measureLocation = true;
    LinearLayout play_layout;
    TimerTask timerTask;
    Timer times;
    int seconds = 0;
    private LinearLayout layoutLocation, layoutStats;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    ImageButton pause;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 1000; /* 1 secs */
    double prevLat = 0, prevLong = 0;
    EditText distance_meters, current_speed, max_speed, timer;
    private final static String KEY_LOCATION = "location";

    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        play_layout = (LinearLayout) findViewById(R.id.play_layout);
        pause = (ImageButton) findViewById(R.id.pause);
        timer = (EditText) findViewById(R.id.timer);
        distance_meters = (EditText) findViewById(R.id.distance_meters);
        current_speed = (EditText) findViewById(R.id.current_speed);
        max_speed = (EditText) findViewById(R.id.max_speed);
        layoutLocation = (LinearLayout) findViewById(R.id.layout_location);
        layoutStats = (LinearLayout) findViewById(R.id.layout_stats);

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);


        }
        times = new Timer();

        times.schedule(timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInformation();
                    }
                });

            }
        }, 0, 1000);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
     //       Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void setInformation() {
        String sec = "";

        seconds++;
        totalSecs++;
        if (seconds < 10) {
            sec = "0" + String.valueOf(seconds);
        } else if (seconds < 60) {

            sec = String.valueOf(seconds);

        } else if (seconds == 60) {
            minutes++;
            min = String.valueOf(minutes);
            seconds = 0;
            sec = "00";
        }
        timer.setText(min + ":" + sec);

    }

    protected void loadMap(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("T", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("T", "Can't find style. Error: ", e);
        }
        map = googleMap;

        if (map != null) {
            // Map is ready
   //         Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
            MapsActivityPermissionsDispatcher.getMyLocationWithPermissionCheck(this);
            MapsActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
        } else {
            Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        //noinspection MissingPermission
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            try {
                                if(location!=mCurrentLocation)
                                onLocationChanged(location);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Called when the Activity becomes visible.
    */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /*
     * Called when the Activity is no longer visible.
	 */
    @Override
    protected void onStop() {

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(111);

        super.onStop();
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }

            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)

                        .setSmallIcon(R.drawable.img_running)

                        .setContentTitle("Sprint Master")
.setOngoing(true)

                        .setContentText("Tracking your Activity");
        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 111, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(111, mBuilder.build());
        // Display the connection status

        if (mCurrentLocation != null) {
            Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        } else {
//            Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
        }
        MapsActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        try {
                            onLocationChanged(locationResult.getLastLocation());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Looper.myLooper());

    }

    public void onLocationChanged(Location location) throws InterruptedException {
        // GPS may be turned off

        if (location == null) {
            return;
        }

        // Report to the UI that the location was updated
        if (measureLocation == true) {


            if (mCurrentLocation != null) {
                //    double distance = GetDistanceFromLatLonInKm(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), location.getLatitude(), location.getLongitude());
                //     Toast.makeText(this, "DISTANCE IS "+distance, Toast.LENGTH_LONG).show();
                //String newDist= new DecimalFormat("#.##").format(distance);


                //  double f1 = Double.valueOf(distance) + Float.valueOf(prevDist);
                //  distance_meters.setText(String.valueOf(f1));
                float dist = mCurrentLocation.distanceTo(location);
                double val = dist + Double.valueOf(distance_meters.getText().toString());
                distance_meters.setText(String.valueOf(new DecimalFormat("#.#").format(val)));



            }
            mCurrentLocation = location;
            current_speed.setText(String.valueOf(new DecimalFormat("#.#").format(
                    location.getSpeed()*3.6)));
            double currVal=Double.valueOf(String.valueOf(current_speed.getText()));
            if ( currVal>= Double.valueOf(max_speed.getText().toString())) {
                max_speed.setText(String.valueOf(currVal));

            }

            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude() );
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.moveCamera(cameraUpdate);

                }
    }

    /*public double GetDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);
        // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        // Distance in km
        return d * 1000;
    }*/

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void locationBtnClicked(View view) {

        layoutStats.setVisibility(View.GONE);
        layoutLocation.setVisibility(View.VISIBLE);

    }

    public void statsBtnClicked(View view) {
        layoutStats.setVisibility(View.VISIBLE);

        layoutLocation.setVisibility(View.GONE);


    }

    public void musicBtnClicked(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        startActivity(intent);
    }

    public void pauseBtnClicked(View view) {
        pause.setVisibility(View.GONE);
        play_layout.setVisibility(View.VISIBLE);
        // pause_layout.setVisibility(View.GONE);
        measureLocation = false;
//       play_layout.setVisibility(View.VISIBLE);
        mCurrentLocation = null;
        times.cancel();
    }

    @Override
    protected void onDestroy() {
        times.cancel();
        super.onDestroy();

    }

    public void playBtnClicked(View view) {

        pause.setVisibility(View.VISIBLE);
        play_layout.setVisibility(View.GONE);
        measureLocation = true;
        times = new Timer();

        times.schedule(timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInformation();
                    }
                });

            }
        }, 0, 1000);


    }

    public void stopBtnClicked(View view) {
        Intent in = new Intent(MapsActivity.this, SummaryActivity.class);
        in.putExtra("TIME", timer.getText().toString());
        in.putExtra("MAX", max_speed.getText().toString());
        in.putExtra("DISTANCE", distance_meters.getText().toString());

        startActivity(in);
        times.cancel();
        this.finish();


    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends android.support.v4.app.DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }


}