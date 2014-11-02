package com.example.brandon_pc.cloudapp2;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    public String name;
    public int days;

    public Contact(String n) {
        name = n;
        days = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(days);
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            Contact c = new Contact(in.readString());
            c.days = in.readInt();
            return c;
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
