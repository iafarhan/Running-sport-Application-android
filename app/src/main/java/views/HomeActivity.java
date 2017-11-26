package views;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asocs.sprintmaster.R;
import com.skyfishjy.library.RippleBackground;

import fragments.NavigationFragment;
import views.MapsActivity;

public class HomeActivity extends AppCompatActivity {
LinearLayout linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
                NavigationFragment navigationFragment=new NavigationFragment();
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_view,navigationFragment);
        transaction.commit();

        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
                        rippleBackground.startRippleAnimation();




    }
    public void startBtnClicked(View view){


        Intent intent=new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
}
