package datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static datamodel.Contract.DATABASE_VERSION;
import static datamodel.Contract.table.CREATE_SPRINTS_TABLE;
import static datamodel.Contract.table.DATABASE_NAME;
import static datamodel.Contract.table.DELETE_TABLE;
import static datamodel.Contract.table.KEY_AvgSpeed;
import static datamodel.Contract.table.KEY_DISTANCE_TRAVELLED;
import static datamodel.Contract.table.KEY_MaxSpeed;
import static datamodel.Contract.table.KEY_TIME_TAKEN;
import static datamodel.Contract.table.TABLE_sprints;

/**
 * Created by Altaf Hussain on 11/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SPRINTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
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


    public void addAthlete(ContentValues values){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(Contract.table.TABLE_sprints,null,values);
        db.close();
    }

    //return information of one athlete.

    Athlete getAthlete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_sprints, new String[] { _ID,
                        KEY_AvgSpeed, KEY_MaxSpeed,KEY_TIME_TAKEN,KEY_DISTANCE_TRAVELLED }, _ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Athlete athlete = new Athlete(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return athlete;
    }

    public int updateAthleteInfo(ContentValues values,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues values = new ContentValues();
        //values.put(KEY_AvgSpeed, athlete.getAverageSpeed());
        //values.put(KEY_MaxSpeed, athlete.getMaximumSpeed());
        //values.put(KEY_DISTANCE_TRAVELLED, athlete.getDistanceTravelled());
        //values.put(KEY_TIME_TAKEN, athlete.getTimeTakenTOCompleteRace());

        // updating row
        return db.update(TABLE_sprints, values, _ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int deleteAthleteInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        int x=db.delete(TABLE_sprints,null, null);
        db.close();
        return x;
    }



    public Cursor getALLAthlete(){
        //List<Course> courseList=new ArrayList<Course>();
        String selectQuery ="SELECT * FROM "+TABLE_sprints;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        return cursor;

    }
}
