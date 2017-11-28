package datamodel;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robo on 11/12/2017.
 */

public class Contract {
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME = "sprintMonitor";
  public static final String CONTENT_AUTHORITY="datamodel.Contentprovider";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_BIO = "USER_BIO";
    public static final String PATH_GRAPH = "USER_GRAPH";

    private Contract(){}
    public static abstract class table1 implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_BIO).build();
        public static final String TABLE_BIO = "USER_BIO";
        public static final String KEY_AvgSpeed = "avg_speed";
        public static final String KEY_MaxSpeed = "max_speed";
        public static final String KEY_TOTAL_DISTANCE_TRAVELLED = "total_distance_travelled";
        public static final String KEY_TOTAL_TIME_TAKEN = "time_taken";
        public static final String TOTAL_RUN = "total_run";

        public static final String CREATE_BIO_TABLE = "CREATE TABLE "+TABLE_BIO+"(" +
                _ID + " INTEGER PRIMARY KEY,"+KEY_AvgSpeed + " TEXT,"
                + KEY_MaxSpeed + " TEXT," + KEY_TOTAL_DISTANCE_TRAVELLED + " TEXT,"
                + KEY_TOTAL_TIME_TAKEN + " TEXT,"+TOTAL_RUN + " TEXT"+")";

        public static final String DELETE_BIO_TABLE="DROP TABLE IF EXISTS "+ TABLE_BIO;

    }

    public static abstract class table2 implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_GRAPH).build();

        public static final String TABLE_GRAPH = "USER_GRAPH";
        public static final String USER_RUN = "user_run";
        public static final String KEY_SPEED = "speed";
        public static final String KEY_TIME = "time";
        public static final String KEY_DISTANCE = "distance";


        public static final String CREATE_GRAPH_TABLE = "CREATE TABLE "+TABLE_GRAPH+"(" +
                _ID + " INTEGER PRIMARY KEY,"+USER_RUN+ " TEXT,"+KEY_SPEED + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_DISTANCE + " TEXT"+")";
        public static final String DELETE_GRAPH_TABLE="DROP TABLE IF EXISTS "+ TABLE_GRAPH;

    }






}
