package com.wisethan.myautomessagetest;

import android.os.Parcel;
import android.os.Parcelable;

public final class MyRect implements Parcelable {
    public int left;
    public int top;
    public int right;
    public int bottom;

    public MyRect() {
    }

    private MyRect(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(left);
        dest.writeInt(top);
        dest.writeInt(right);
        dest.writeInt(bottom);
    }

    public static final Parcelable.Creator<MyRect> CREATOR = new Parcelable.Creator<MyRect>() {
        @Override
        public MyRect createFromParcel(Parcel in) {
            return new MyRect(in);
        }
        @Override
        public MyRect[] newArray(int size) {
            return new MyRect[size];
        }
    };
}

