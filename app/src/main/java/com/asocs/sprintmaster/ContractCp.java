package com.asocs.sprintmaster;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.HashMap;

/**
 * Created by ASOCS on 10/18/2017.
 */

public final  class ContractCp {
    private ContractCp(){}
    static final String PROVIDER_NAME = "com.asocs.sprintmaster.CustomContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/stats";


    static final int DATABASE_VERSION = 1;
    static final int uriCode = 1;
    private static HashMap<String, String> values;
    static final UriMatcher uriMatcher;

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "stats", uriCode);

    }

    static final String DATABASE_NAME = "sprintMaster";


    public static class ContactsCols implements BaseColumns {

        public    static final String TABLE_NAME = "stats";
  public      static final String distance = "distance";
        public       static final String max_speed = "max_speed";
        public    static final String time = "time";






     public   static final Uri CONTENT_URI = Uri.parse(URL);

    }

}
