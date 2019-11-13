package com.ascom.recyclerview.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DataControlContract {

    public DataControlContract() {

    }

    public static final String CONTENT_AUTHORITY = "com.ascom.recyclerview";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_ITEMS = "items";


    public static final class ViewsData implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String TABLE_NAME = "items";


        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
