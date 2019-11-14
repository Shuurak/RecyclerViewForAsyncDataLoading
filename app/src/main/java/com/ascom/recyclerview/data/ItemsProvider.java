package com.ascom.recyclerview.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ItemsProvider extends ContentProvider {
    private static final String TAG = "AppAscomRES " + "ItemsProvider";

    private ItemsDbHelper mItemsDbHelper;

    private static final int URI_ITEMS = 1000;
    private static final int URI_ITEMS_ID = 1001;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.guests/guests" will map to the
        // integer code {@link #GUESTS}. This URI is used to provide access to MULTIPLE rows
        // of the guests table.
        sUriMatcher.addURI(DataControlContract.CONTENT_AUTHORITY, DataControlContract.PATH_ITEMS, URI_ITEMS);

        // The content URI of the form "content://com.example.android.guests/guests/#" will map to the
        // integer code {@link #GUEST_ID}. This URI is used to provide access to ONE single row
        // of the guests table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.guests/guests/3" matches, but
        // "content://com.example.android.guests/guests" (without a number at the end) doesn't match.
        sUriMatcher.addURI(DataControlContract.CONTENT_AUTHORITY, DataControlContract.PATH_ITEMS + "/#", URI_ITEMS_ID);
    }



    @Override
    public boolean onCreate() {
        mItemsDbHelper = new ItemsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mItemsDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case URI_ITEMS:
                cursor = database.query(DataControlContract.ViewsData.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case URI_ITEMS_ID:
                selection = DataControlContract.ViewsData._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(DataControlContract.ViewsData.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case URI_ITEMS:
                return DataControlContract.ViewsData.CONTENT_LIST_TYPE;

            case URI_ITEMS_ID:
                return DataControlContract.ViewsData.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        switch (match) {
            case URI_ITEMS:
                return insertItem(uri, values);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertItem(Uri aUri, ContentValues aValues) {
        // Check that the name is not null
        String tag = aValues.getAsString(DataControlContract.ViewsData.COLUMN_TAG);
        if (tag == null) {
            throw new IllegalArgumentException("Guest requires a name");
        }

        String timestamp = aValues.getAsString(DataControlContract.ViewsData.COLUMN_TIMESTAMP);
        if (timestamp == null) {
            throw new IllegalArgumentException("Guest requires a name");
        }

        String priority = aValues.getAsString(DataControlContract.ViewsData.COLUMN_PRIORITY);
        if (priority == null) {
            throw new IllegalArgumentException("Guest requires a name");
        }

        String text = aValues.getAsString(DataControlContract.ViewsData.COLUMN_TEXT);
        if (text == null) {
            throw new IllegalArgumentException("Guest requires a name");
        }

        // Get writeable database
        SQLiteDatabase database = mItemsDbHelper.getWritableDatabase();

        // Insert the new guest with the given values
        long id = database.insert(DataControlContract.ViewsData.TABLE_NAME, null, aValues);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + aUri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(aUri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mItemsDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case URI_ITEMS:
                return database.delete(DataControlContract.ViewsData.TABLE_NAME, selection, selectionArgs);

            case URI_ITEMS_ID:
                selection = DataControlContract.ViewsData._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(DataControlContract.ViewsData.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case URI_ITEMS:
                return updateItems(uri, values, selection, selectionArgs);

            case URI_ITEMS_ID:
                selection = DataControlContract.ViewsData._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItems(uri, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItems(Uri aUri, ContentValues aValues, String aSelection, String[] aSelectionArgs) {
        return 0;
    }
}
