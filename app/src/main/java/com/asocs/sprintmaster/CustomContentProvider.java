package com.asocs.sprintmaster;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by ASOCS on 10/14/2017.
 */

public class CustomContentProvider extends ContentProvider {

    private SQLiteDatabase sqlDb;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + ContractCp.ContactsCols.TABLE_NAME +
            "(id INTEGER PRIMARY KEY, distance TEXT NOT NULL,max_speed TEXT, time TEXT);";

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDb = dbHelper.getWritableDatabase();
        if (sqlDb != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ContractCp.ContactsCols.TABLE_NAME);
        switch (ContractCp.uriMatcher.match(uri)) {

            case ContractCp.uriCode:
                //   queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("UNKOWN URI" + uri);
        }

        Cursor cursor = queryBuilder.query(sqlDb, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        //we want the cursor to watch uri changes
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    //type of data
    {
        switch (ContractCp.uriMatcher.match(uri)) {

            case ContractCp.uriCode:
                return "vnd.android.cursor.dir/stats";
            default:
                throw new IllegalArgumentException("UNSUPPORTED URI" + uri);

        }


    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long rowID = sqlDb.insert(ContractCp.ContactsCols.TABLE_NAME, null, values);
        if (rowID > 0) {

            Uri _uri = ContentUris.withAppendedId(ContractCp.ContactsCols.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        } else {
            Toast.makeText(getContext(), "ROW INSERTION FAILED", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowDeleted = 0;
        switch (ContractCp.uriMatcher.match(uri)) {
            case ContractCp.uriCode:
                rowDeleted = sqlDb.delete(ContractCp.ContactsCols.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UNKOWN URI");

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsUpdated = 0;

        switch (ContractCp.uriMatcher.match(uri)) {

            case ContractCp.uriCode:
                rowsUpdated = sqlDb.update(ContractCp.ContactsCols.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UNKNOWN URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, ContractCp.DATABASE_NAME, null, ContractCp.DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ContractCp.ContactsCols.TABLE_NAME);
            onCreate(db);
        }
    }
}
