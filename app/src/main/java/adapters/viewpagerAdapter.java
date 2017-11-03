package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.viewpagerFrag1;
import fragments.viewpagerFrag2;
import fragments.viewpagerFrag3;

/**
 * Created by robo on 11/3/2017.
 */

public class viewpagerAdapter extends FragmentPagerAdapter {

    public viewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new viewpagerFrag1();
        } else if (position == 1){
            return new viewpagerFrag2();
        } else {
            return new viewpagerFrag3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}