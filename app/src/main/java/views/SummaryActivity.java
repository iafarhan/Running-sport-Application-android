package views;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asocs.sprintmaster.ContractCp;
import com.asocs.sprintmaster.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SummaryActivity extends AppCompatActivity {
TextView max,time,distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        if(savedInstanceState==null) {
            Intent intent = getIntent();

            max = (TextView) findViewById(R.id.max);
            time = (TextView) findViewById(R.id.timeTotal);
            distance = (TextView) findViewById(R.id.distance);
            max.setText(intent.getStringExtra("MAX")+ " Km/h");
            distance.setText(intent.getStringExtra("DISTANCE")+" m");
            time.setText(intent.getStringExtra("TIME"));
            ContentValues values=new ContentValues();
            values.put(ContractCp.ContactsCols.distance,intent.getStringExtra("DISTANCE"));

            values.put(ContractCp.ContactsCols.max_speed,intent.getStringExtra("MAX"));
            values.put(ContractCp.ContactsCols.time,time.getText().toString());

            Uri uri=getContentResolver().insert(ContractCp.ContactsCols.CONTENT_URI,values);

        }    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void doneBtnClicked(View view) {
        Intent i=new Intent(this,HomeActivity.class);
        startActivity(i);
        this.finish();
    }

}
