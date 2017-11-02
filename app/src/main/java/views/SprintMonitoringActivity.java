package views;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asocs.sprintmaster.R;

import fragments.NavigationFragment;

public class SprintMonitoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_monitoring);
        getSupportActionBar().hide();
        NavigationFragment navigationFragment=new NavigationFragment();
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_view,navigationFragment);
        transaction.commit();
    }
}
