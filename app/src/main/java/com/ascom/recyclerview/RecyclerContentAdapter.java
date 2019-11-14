package com.ascom.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascom.recyclerview.data.DataControlContract;
import com.ascom.recyclerview.data.DataUnit;

import java.util.ArrayList;

public class RecyclerContentAdapter extends RecyclerView.Adapter<RecyclerContentAdapter.RecyclerViewHolder> {

    private ArrayList<DataUnit> mContentList = new ArrayList<>();
    Context mContext;
    private static final String TAG = "AppAscomRES " + "RecyclerContentAdapter";

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

        Log.d(TAG, "onBindViewHolder: new content bind");
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: "+mContentList.size());
        return mContentList.size();
    }

    public void setContentList(ArrayList<DataUnit> aContentList) {
        mContentList.addAll(aContentList);
        notifyDataSetChanged();
    }

    public void clearContentList() {
        mContentList.clear();
        notifyDataSetChanged();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTag;
        private TextView mItemTimestamp;
        private TextView mItemPriority;
        private TextView mItemText;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mItemTag = itemView.findViewById(R.id.item_tag_field);
            mItemTimestamp = itemView.findViewById(R.id.item_timestamp_field);
            mItemPriority = itemView.findViewById(R.id.item_priority_field);
            mItemText = itemView.findViewById(R.id.item_text_field);
        }

        public void bind (DataUnit aDataUnit) {
            mItemTag.setText(aDataUnit.getTag());
            mItemTimestamp.setText(aDataUnit.getTimestamp());
            mItemPriority.setText(aDataUnit.getPriority());
            mItemText.setText(aDataUnit.getText());

            Log.d(TAG, "bind: content set");
        }
    }
}
