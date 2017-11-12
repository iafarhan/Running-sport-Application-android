package datamodel;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by robo on 11/12/2017.
 */

public class Contentprovider extends ContentProvider {

    public DBHelper db;

    public static final int TASK=100;
    public static final int TASK_ID=101;

    public static final UriMatcher uriMatcher=urimatcher();

    @Override
    public boolean onCreate() {

        Context context=getContext();
        db=new DBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match=uriMatcher.match(uri);
        if(match==TASK)
            return db.getALLAthlete();
        return db.getALLAthlete();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match=uriMatcher.match(uri);
        if(match==TASK)
            db.addAthlete(values);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int x=db.deleteAthleteInfo();
        db.close();
        return x;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db.updateAthleteInfo(values,1);
        return 0;
    }



    public static UriMatcher urimatcher(){

        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH,TASK);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH+"/#",TASK_ID);
        return uriMatcher;
    }



}
