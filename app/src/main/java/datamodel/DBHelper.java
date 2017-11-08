package datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.StringBuilderPrinter;

/**
 * Created by Altaf Hussain on 11/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sprintMonitor";
    private static final String TABLE_sprints = "sprints";
    private static final String KEY_ID = "id";
    private static final String KEY_AvgSpeed = "avg_speed";
    private static final String KEY_MaxSpeed = "max_speed";
    private static final String KEY_DISTANCE_TRAVELLED = "distance_travelled";
    private static final String KEY_TIME_TAKEN = "time_taken";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SPRINTS_TABLE = "CREATE TABLE"+TABLE_sprints+"(" +
                KEY_ID + "INTEGER PRIMARY KEY,"+KEY_AvgSpeed + "TEXT,"
                + KEY_MaxSpeed + "TEXT," + KEY_DISTANCE_TRAVELLED + "TEXT,"
                + KEY_TIME_TAKEN + "TEXT"+")";
        db.execSQL(CREATE_SPRINTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_sprints);
        onCreate(db);
    }

    //add sprint information related to athlete
    void addSprintsInfo(Athlete athlete) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AvgSpeed, athlete.getAverageSpeed());
        values.put(KEY_MaxSpeed, athlete.getMaximumSpeed());
        values.put(KEY_DISTANCE_TRAVELLED, athlete.getDistanceTravelled());
        values.put(KEY_TIME_TAKEN, athlete.getTimeTakenTOCompleteRace());

        // Inserting Row
        db.insert(TABLE_sprints, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    //return information of one athlete.

    Athlete getAthlete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_sprints, new String[] { KEY_ID,
                        KEY_AvgSpeed, KEY_MaxSpeed,KEY_TIME_TAKEN,KEY_DISTANCE_TRAVELLED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Athlete athlete = new Athlete(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return athlete;
    }

    public int updateAthleteInfo(Athlete athlete) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AvgSpeed, athlete.getAverageSpeed());
        values.put(KEY_MaxSpeed, athlete.getMaximumSpeed());
        values.put(KEY_DISTANCE_TRAVELLED, athlete.getDistanceTravelled());
        values.put(KEY_TIME_TAKEN, athlete.getTimeTakenTOCompleteRace());

        // updating row
        return db.update(TABLE_sprints, values, KEY_ID + " = ?",
                new String[] { String.valueOf(athlete.getID()) });
    }

    public void deleteAthleteInfo(Athlete athlete) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_sprints, KEY_ID + " = ?",
                new String[] { String.valueOf(athlete.getID()) });
        db.close();
    }
}
