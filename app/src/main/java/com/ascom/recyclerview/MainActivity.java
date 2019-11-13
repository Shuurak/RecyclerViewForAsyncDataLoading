package com.ascom.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String MAIN_ACTIVITY_HANDLER = "MAIN_ACTIVITY_HANDLER";
    public static final String AVAILABLE_DATA = "AVAILABLE_DATA";
    public static final int INIT_DATA_LOAD = 0;
    public static final int NEW_DATA_AVAILABLE = 1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerContentAdapter mRecyclerContentAdapter;
    private ArrayList<String> mStringArrayList = new ArrayList<>();

    private ServiceConnection mServiceConnection;
    private boolean mServiceBindFlag = false;
    private ContentService mContentService;
    private ContentService.ContentServiceBinder mContentServiceBinder;
    private Handler mActivityHandler;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityHandler = new ActivityHandler(this);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName aName, IBinder aService) {
                Log.d(TAG, "onServiceConnected: ContentService connected");

                mContentServiceBinder = (ContentService.ContentServiceBinder) aService;

                mContentService = mContentServiceBinder.getServiceBinder();
                mServiceBindFlag = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName aName) {
                Log.d(TAG, "onServiceDisconnected: ContentService disconnected");

                mServiceBindFlag = false;
            }
        };

        Intent intent = new Intent(getBaseContext(), ContentService.class);
        intent.putExtra(MAIN_ACTIVITY_HANDLER, new Messenger(mActivityHandler));

        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

        initRecyclerView();

        mContentService.updateDataRequest("first data");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityHandler.removeCallbacksAndMessages(null);
        Log.d(TAG, "onDestroy: MainActivity destroyed");
    }
    @Override
    protected void onSaveInstanceState(Bundle saveInstState) {
        super.onSaveInstanceState(saveInstState);

        Log.d(TAG, "SaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstState) {
        super.onRestoreInstanceState(saveInstState);

        Log.d(TAG, "RestoreInstanceState");
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.mainRecyclerView);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerContentAdapter = new RecyclerContentAdapter(this);
        mRecyclerView.setAdapter(mRecyclerContentAdapter);

        mRecyclerContentAdapter.setContentList(loadContentToView());
    }

    void updateData(ArrayList<String> aStringArrayList) {
        mRecyclerContentAdapter.setContentList(aStringArrayList);
    }

    ArrayList<String> loadContentToView () {
        for (int i=0; i<2; i++)
        {
            mStringArrayList.add("Thing "+i);
        }
        return mStringArrayList;
    }

    class ActivityHandler extends Handler {
        WeakReference<MainActivity> mMainActivityWeakReference;

        public ActivityHandler (MainActivity aMainActivity) {
            mMainActivityWeakReference = new WeakReference<>(aMainActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            MainActivity mainActivity = mMainActivityWeakReference.get();

            if (mainActivity!=null) {
                switch (msg.what) {
                    case INIT_DATA_LOAD: {
                        Log.d(TAG, "handleMessage: msg - Init Data Load");
                        updateData(msg.getData().getStringArrayList(AVAILABLE_DATA));
                        break;
                    }
                    case NEW_DATA_AVAILABLE: {
                        Log.d(TAG, "handleMessage: msg - New Data Available");
                        break;
                    }
                }
            }
        }
    }
}
