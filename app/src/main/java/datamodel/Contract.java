package datamodel;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robo on 11/12/2017.
 */

public class Contract {
    public static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="courseManager";

    public static final String CONTENT_AUTHORITY="com.example.robo.datamodel.Contentprovider";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH="sprints";


    private Contract(){}

    public static abstract class table implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String DATABASE_NAME = "sprintMonitor";
        public static final String TABLE_sprints = "sprints";
        public static final String KEY_AvgSpeed = "avg_speed";
        public static final String KEY_MaxSpeed = "max_speed";
        public static final String KEY_DISTANCE_TRAVELLED = "distance_travelled";
        public static final String KEY_TIME_TAKEN = "time_taken";

        public static final String CREATE_SPRINTS_TABLE = "CREATE TABLE"+TABLE_sprints+"(" +
                _ID + "INTEGER PRIMARY KEY,"+KEY_AvgSpeed + "TEXT,"
                + KEY_MaxSpeed + "TEXT," + KEY_DISTANCE_TRAVELLED + "TEXT,"
                + KEY_TIME_TAKEN + "TEXT"+")";
        public static final String DELETE_TABLE="DROP TABLE IF EXISTS "+ TABLE_sprints;


    }






}
