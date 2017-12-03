package datamodel;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robo on 11/12/2017.
 */

public class Contract {
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME = "sprintMaster";
  public static final String CONTENT_AUTHORITY="com.asocs.sprintmaster.datamodel.Contentprovider";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_STATS = "stats";

    private Contract(){}
    public static abstract class User implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String TABLE_USER = "user";
        public static final String COL_NAME="name";
        public static final String COL_EMAIL="email";
        public static final String COL_PHOTO="photo";

public static final String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_USER+"(" +
                _ID + " INTEGER primary key,"
                + COL_NAME + " TEXT," + COL_EMAIL + " TEXT,"
                + COL_PHOTO + " TEXT);";

        public static final String DELETE_USER_TABLE="DROP TABLE IF EXISTS "+ TABLE_USER;

    }

    public static abstract class Stats implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();

        public static final String TABLE_STATS = "USER_GRAPH";
        public static final String COL_MAX = "max_speed";
        public static final String COL_RUNS = "runs";
        public static final String COL_TIME = "time_taken";
        public static final String COL_DISTANCE = "time_taken";

        public static final String CREATE_STATS_TABLE = "CREATE TABLE "+TABLE_STATS+"(" +
                _ID + " INTEGER PRIMARY KEY,"+COL_MAX+ " TEXT,"+COL_RUNS + " INTEGER,"
                + COL_TIME + " TEXT," + COL_DISTANCE + " TEXT"+")";
        public static final String DELETE_STATS_TABLE="DROP TABLE IF EXISTS "+ TABLE_STATS;

    }






}
