package com.example.chartirvan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListChart implements Parcelable {
    private int index;
    private String parameterX;
    private String parameterY;
    private String description;

    public ListChart(int index, String parameterX, String parameterY, String description) {
        this.index = index;
        this.parameterX = parameterX;
        this.parameterY = parameterY;
        this.description = description;
    }

    public ListChart(){

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getParameterX() {
        return parameterX;
    }

    public void setParameterX(String parameterX) {
        this.parameterX = parameterX;
    }

    public String getParameterY() {
        return parameterY;
    }

    public void setParameterY(String parameterY) {
        this.parameterY = parameterY;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.parameterX);
        dest.writeString(this.parameterY);
        dest.writeString(this.description);
    }

    protected ListChart(Parcel in) {
        this.index = in.readInt();
        this.parameterX = in.readString();
        this.parameterY = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ListChart> CREATOR = new Parcelable.Creator<ListChart>() {
        @Override
        public ListChart createFromParcel(Parcel source) {
            return new ListChart(source);
        }

        @Override
        public ListChart[] newArray(int size) {
            return new ListChart[size];
        }
    };
}
