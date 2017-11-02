package views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.asocs.sprintmaster.R;

import fragments.NavigationFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                 WindowManager.LayoutParams. FLAG_FULLSCREEN );
        getSupportActionBar().hide();

    }
}
