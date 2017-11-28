package datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Altaf Hussain on 11/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Contract.table1.CREATE_BIO_TABLE);
        db.execSQL(Contract.table2.CREATE_GRAPH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.table1.DELETE_BIO_TABLE);
        db.execSQL(Contract.table2.DELETE_GRAPH_TABLE);
        onCreate(db);
    }
/*
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

*/
    public void ADDBIO(ContentValues values){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(Contract.table1.TABLE_BIO,null,values);
        db.close();
    }
    public void addAthleteGraph(ContentValues values){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(Contract.table2.TABLE_GRAPH,null,values);
        db.close();
    }



    //return information of one athlete.

    Cursor getDATA(String table, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(table,projection,selection,selectionArgs,sortOrder,null,null,null);
//        Cursor cursor = db.query(TABLE_BIO, new String[] { _ID,
  //                      KEY_AvgSpeed, KEY_MaxSpeed,KEY_TOTAL_TIME_TAKEN,KEY_TOTAL_DISTANCE_TRAVELLED,TOTAL_RUN }, _ID + "=?",
    //            new String[] { String.valueOf(id) }, null, null, null, null);

        return cursor;
        //
        //
        //  if (cursor != null)
   //         cursor.moveToFirst();

        //Athlete athlete = new Athlete(Integer.parseInt(cursor.getString(0)),
        //        cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return contact
      //  return athlete;

    }
    //Graph getGraphofAthlete(String table, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
      //  SQLiteDatabase db = this.getReadableDatabase();





        //   Cursor cursor = db.query(TABLE_GRAPH, new String[] { _ID,USER_RUN,
       //                 KEY_SPEED, KEY_TIME,KEY_DISTANCE }, _ID + "=?",
         //       new String[] { String.valueOf(id) }, null, null, null, null);

        /*  if (cursor != null)
            cursor.moveToFirst();
        Graph graph = new Graph(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3));
        // return contact
        return graph;

    */
    //}

    public int updateBIO(String table, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(table,values,selection,selectionArgs);
   //     return db.update(table, values, _ID + " = ?",
 //               new String[] { String.valueOf(id) });


    }



    public int updateGraph(String table, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table,values,selection,selectionArgs);
        //return db.update(TABLE_BIO, values, _ID + " = ?",
          //      new String[] { String.valueOf(id) });

    }

    public int deleteFromTable(@NonNull String table, @Nullable String selection, @Nullable String[] selectionArgs){
        SQLiteDatabase db = this.getWritableDatabase();
     return   db.delete(table,selection,selectionArgs);
    }


    public Cursor getALLAthlete(){
        //List<Course> courseList=new ArrayList<Course>();
        String selectQuery ="SELECT * FROM "+ Contract.table1.TABLE_BIO;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        return cursor;

    }
    public void CreateBioTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Contract.table1.CREATE_BIO_TABLE);
    }

}
