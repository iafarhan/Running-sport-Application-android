package datamodel;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;public class Contentprovider extends ContentProvider {

    public DBHelper db;

    public static final int BIO=100;
    public static final int BIO_ID=101;
    public static final int GRAPH=102;
    public static final int GRAPH_ID=103;

    public static final UriMatcher uriMatcher=urimatcher();

    @Override
    public boolean onCreate() {

        Context context=getContext();
        db=new DBHelper(context);
        //db.CreateBioTable();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match=uriMatcher.match(uri);
        if(match==BIO)
            return db.getDATA(Contract.table1.TABLE_BIO,projection,selection,selectionArgs,sortOrder);
        else if(match==GRAPH)
            return db.getDATA(Contract.table2.TABLE_GRAPH,projection,selection,selectionArgs,sortOrder);
        return null;
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

        if(match==BIO)
        {
            db.ADDBIO(values);
            return uri;

        }
        else if(match==GRAPH) {
            db.addAthleteGraph(values);
            return uri;
        }
        return null;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match=uriMatcher.match(uri);

        if(match==BIO)
        {
            return db.deleteFromTable(Contract.table1.TABLE_BIO,selection,selectionArgs);


        }
        else if(match==GRAPH) {
            return db.deleteFromTable(Contract.table2.TABLE_GRAPH,selection,selectionArgs);
        }



        db.close();
        return 0;
    }

   // void deleteTABLE(){

 //       db.deleteAthleteInfo();

//    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int match = uriMatcher.match(uri);

        if (match == BIO) {
            return db.updateBIO(Contract.table1.TABLE_BIO, values, selection, selectionArgs);
        } else if (match == GRAPH) {
            return db.updateGraph(Contract.table2.TABLE_GRAPH, values, selection, selectionArgs);
        }
return 0;

    }

    public static UriMatcher urimatcher(){

        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH_BIO,BIO);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH_BIO+"/#",BIO_ID);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH_GRAPH,GRAPH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.PATH_GRAPH+"/#",GRAPH_ID);
        return uriMatcher;
    }

}
