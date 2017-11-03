package fragments;

/**
 * Created by robo on 11/3/2017.
 */


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.asocs.sprintmaster.R;

public class viewpagerFrag1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.opening_screen1, container, false);
    }

}