package views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.asocs.sprintmaster.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                 WindowManager.LayoutParams. FLAG_FULLSCREEN );
  }

    public void startTourClicked(View view) {

    Intent intent =new Intent(this,TourActivity.class);
        startActivity(intent);
    }
}
