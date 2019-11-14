package com.ascom.recyclerview.data;

import android.os.Parcel;
import android.os.Parcelable;

    public class DataUnit implements Parcelable {
        private String mTag;
        private String mTimestamp;
        private String mPriority;
        private String mText;

        public DataUnit(String aTag, String aTimestamp, String aPriority, String aText) {
            mTag = aTag;
            mTimestamp = aTimestamp;
            mPriority = aPriority;
            mText = aText;
        }

        protected DataUnit(Parcel in) {
            mTag = in.readString();
            mTimestamp = in.readString();
            mPriority = in.readString();
            mText = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mTag);
            dest.writeString(mTimestamp);
            dest.writeString(mPriority);
            dest.writeString(mText);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Parcelable.Creator<DataUnit> CREATOR = new Parcelable.Creator<DataUnit>() {
            @Override
            public DataUnit createFromParcel(Parcel in) {
                return new DataUnit(in);
            }

            @Override
            public DataUnit[] newArray(int size) {
                return new DataUnit[size];
            }
        };

        public String getTag() {
            return mTag;
        }

        public void setTag(String aTag) {
            mTag = aTag;
        }

        public String getTimestamp() {
            return mTimestamp;
        }

        public void setTimestamp(String aTimestamp) {
            mTimestamp = aTimestamp;
        }

        public String getText() {
            return mText;
        }

        public void setText(String aText) {
            mText = aText;
        }

        public String getPriority() {
            return mPriority;
        }

        public void setPriority(String aPriority) {
            mPriority = aPriority;
        }
    }