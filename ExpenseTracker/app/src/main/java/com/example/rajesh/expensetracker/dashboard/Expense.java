package com.example.rajesh.expensetracker.dashboard;


import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {
    public int expenseId;
    public long expenseDate;
    public String expenseTitle;
    public String expenseDescription;
    public int expenseAmount;
    public int categoryId;
    public String expenseType;

    public Expense() {
    }

    protected Expense(Parcel in) {
        expenseId = in.readInt();
        expenseDate = in.readLong();
        expenseTitle = in.readString();
        expenseDescription = in.readString();
        expenseAmount = in.readInt();
        categoryId = in.readInt();
        expenseType = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenseId);
        dest.writeLong(expenseDate);
        dest.writeString(expenseTitle);
        dest.writeString(expenseDescription);
        dest.writeInt(expenseAmount);
        dest.writeInt(categoryId);
        dest.writeString(expenseType);
    }
}
