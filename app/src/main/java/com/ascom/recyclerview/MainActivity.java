package com.ascom.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerContentAdapter mRecyclerContentAdapter;
    private ArrayList<String> mStringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    void initRecyclerView() {
        mRecyclerView = findViewById(R.id.mainRecyclerView);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerContentAdapter = new RecyclerContentAdapter(this);
        mRecyclerView.setAdapter(mRecyclerContentAdapter);

        mRecyclerContentAdapter.setContentList(loadContentToView());
    }

    ArrayList<String> loadContentToView () {
        for (int i=1; i<10; i++)
        {
            mStringArrayList.add("Thing "+i);
        }
        return mStringArrayList;
    }
}
