package com.ascom.recyclerview;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ascom.recyclerview.data.DataControlContract;
import com.ascom.recyclerview.data.DataUnit;

import java.util.ArrayList;
@SuppressWarnings("checkstyle:LeftCurly")

public class ContentService extends IntentService {
    private static final String TAG = "AppAscomRES " + "ContentService";

    private Messenger mMessenger;
    private IBinder mBinder = new ContentServiceBinder();
    private MainActivity.ActivityHandler mActivityHandler;


    public ContentService() {
        super(ContentService.class.toString());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent aIntent) {
        Log.d(TAG, "onBind: ");

        mMessenger = aIntent.getParcelableExtra(MainActivity.MAIN_ACTIVITY_HANDLER);
        return mBinder;
    }

    class ContentServiceBinder extends Binder {

        ContentService getServiceBinder() {
            return ContentService.this;
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    void sendMsgToUI (int aWhat, Bundle aData) {
        Message _msg = new Message();
        _msg.what = aWhat;
        _msg.setData(aData);

        try {
            mMessenger.send(_msg);
        } catch (RemoteException aE) {
            aE.printStackTrace();
        }
    }

    public void updateDataRequest(int what, @Nullable String aParam) {
        Log.d(TAG, "updateDataRequest: "+what);

        if (aParam == null)
        {
            aParam = "Nullable param";
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.AVAILABLE_DATA, loadContentToView(aParam));

        sendMsgToUI(MainActivity.INIT_DATA_LOAD, bundle);

        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        insertItem();
        getDataFromDb();
    }

    void getDataFromDb() {

        String[] projections = {
                DataControlContract.ViewsData._ID,
                DataControlContract.ViewsData.COLUMN_TAG,
                DataControlContract.ViewsData.COLUMN_TIMESTAMP,
                DataControlContract.ViewsData.COLUMN_PRIORITY,
                DataControlContract.ViewsData.COLUMN_TEXT
        };

        Cursor cursor = getContentResolver().query(
                DataControlContract.ViewsData.CONTENT_URI,
                projections,
                null,
                null,
                null
        );

        ArrayList<DataUnit> fromDb = new ArrayList<>();

        int indexID = cursor.getColumnIndex(DataControlContract.ViewsData._ID);
        int indexTag = cursor.getColumnIndex(DataControlContract.ViewsData.COLUMN_TAG);
        int indexTimestamp = cursor.getColumnIndex(DataControlContract.ViewsData.COLUMN_TIMESTAMP);
        int indexPriority = cursor.getColumnIndex(DataControlContract.ViewsData.COLUMN_PRIORITY);
        int indexText = cursor.getColumnIndex(DataControlContract.ViewsData.COLUMN_TEXT);

        while (cursor.moveToNext()) {
            fromDb.add(new DataUnit(
                    cursor.getString(indexTag), cursor.getString(indexTimestamp),
                    cursor.getString(indexPriority), cursor.getString(indexText)
            ));
        }

        cursor.close();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.AVAILABLE_DATA, fromDb);
        sendMsgToUI(MainActivity.NEW_DATA_AVAILABLE, bundle);
    }

    private void insertItem() {
        // Считываем данные из текстовых полей
        String tag = "nothing";
        Long tsLong = System.currentTimeMillis()/1000;
        String time = tsLong.toString();
        String prior = "LOW";
        String text = "static text";


        ContentValues values = new ContentValues();
        values.put(DataControlContract.ViewsData.COLUMN_TAG, tag);
        values.put(DataControlContract.ViewsData.COLUMN_TIMESTAMP, System.currentTimeMillis()/1000);
        values.put(DataControlContract.ViewsData.COLUMN_PRIORITY, prior);
        values.put(DataControlContract.ViewsData.COLUMN_TEXT, text);

        Uri newUri = getContentResolver().insert(DataControlContract.ViewsData.CONTENT_URI, values);


        if (newUri == null) {
            Toast.makeText(this, "Error while insertion item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insertion successful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    ArrayList<DataUnit> loadContentToView (String aS) {
        ArrayList<DataUnit> dataUnitArrayList = new ArrayList<>();
        for (int i=0; i<10; i++)
        {
            if (i%2 == 0)
            {
                dataUnitArrayList.add(new DataUnit(aS, aS, aS, aS));
            }
            else
            {
                dataUnitArrayList.add(new DataUnit("" + i, "" + i, "" + i, "" + i));
            }
        }
        return dataUnitArrayList;
    }
}
