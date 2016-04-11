package com.example.rajesh.expensetracker.account;


import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    public int accountId;
    public String accountName;
    public int amount;
    public String accountType;
    public long date;

    public Account() {
    }

    protected Account(Parcel in) {
        accountId = in.readInt();
        accountName = in.readString();
        amount = in.readInt();
        accountType = in.readString();
        date = in.readLong();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accountId);
        dest.writeString(accountName);
        dest.writeInt(amount);
        dest.writeString(accountType);
        dest.writeLong(date);
    }
}
