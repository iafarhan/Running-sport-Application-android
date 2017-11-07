package views;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.asocs.sprintmaster.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(googleServicesAvailable()){
            Toast.makeText(this,"PLAY SERVICES WORKING",Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_maps);
            initMap();
        }
        else {


            //No google map found// display another layout saying that map ain't available
        }
    }

    private  void initMap(){


        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.map_Fragment);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServicesAvailable(){

        GoogleApiAvailability apiAvailability=GoogleApiAvailability.getInstance();
        int isAvailable=apiAvailability.isGooglePlayServicesAvailable(this);
        if(isAvailable== ConnectionResult.SUCCESS){

            return true;
        }
        else if (apiAvailability.isUserResolvableError(isAvailable)){

            Dialog dialog= apiAvailability.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }
        else {

            Toast.makeText(this,"CANNOT CONNECT TO PLAY SERVICES",Toast.LENGTH_SHORT).show();
        }
     return false;
    }

    @Override
    public void onMapReady(GoogleMap mgoogleMap) {
        googleMap=mgoogleMap;
    }
}
