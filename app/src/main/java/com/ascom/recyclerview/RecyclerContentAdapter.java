package com.ascom.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerContentAdapter extends RecyclerView.Adapter<RecyclerContentAdapter.RecyclerViewHolder> {

    private ArrayList<String> mContentList = new ArrayList<>();
    Context mContext;
    private static final String TAG = "!!!!";

    public RecyclerContentAdapter(Context aContext) {
        mContext = aContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder aHolder, int aPosition) {
        aHolder.bind(mContentList.get(aPosition));

        Log.d(TAG, "onBindViewHolder: +");
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: "+mContentList.size());
        return mContentList.size();
    }

    public void setContentList(ArrayList<String> aContentList) {
        mContentList.addAll(aContentList);
        notifyDataSetChanged();
    }

    public void clearContentList() {
        mContentList.clear();
        notifyDataSetChanged();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind (String aContent) {
            mTextView.setText(aContent);
            Log.d(TAG, "bind: ++");
        }
    }
}
