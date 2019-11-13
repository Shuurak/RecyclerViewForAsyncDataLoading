package com.ascom.recyclerview.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ItemsProvider extends ContentProvider {
    private static final String TAG = "ItemsProvider";

    private ItemsDbHelper mItemsDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URI_ITEMS = 1000;
    private static final int URI_ITEMS_ID = 1001;


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
        switch (match) {
            case URI_ITEMS:
                return insertItem(uri, values);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertItem(Uri aUri, ContentValues aValues) {
        return null;
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
