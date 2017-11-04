package application;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import com.asocs.sprintmaster.R;
/**
 * Created by ASOCS on 11/4/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/in.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
