package com.ascom.recyclerview;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContentService extends IntentService {
    private static final String TAG = "ContentService";

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

    void updateDataRequest(String aParam) {
        Log.d(TAG, "updateDataRequest: "+aParam);

    }

    ArrayList<String> loadContentToView (String aS) {
        ArrayList<String> StringArr = new ArrayList<>();
        for (int i=0; i<10; i++)
        {
            StringArr.add("Thing "+i);
        }
        return StringArr;
    }
}
