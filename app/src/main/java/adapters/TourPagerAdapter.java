package adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asocs.sprintmaster.R;

/**
 * Created by ASOCS on 11/3/2017.
 */

public class TourPagerAdapter extends PagerAdapter {

    int[] mResources = {R.drawable.tour_sprint,R.drawable.tour_workout,R.drawable.tour_diet};
    String[] mDescription={"sprint monitoring","workout plans","diet plans"};
//
    Context mContext;
    LayoutInflater mLayoutInflater;

    public TourPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.tour_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);
        TextView textView=(TextView) itemView.findViewById(R.id.description_view);
        textView.setText(mDescription[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
