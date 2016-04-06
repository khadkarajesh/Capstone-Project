package com.example.rajesh.expensetracker.category;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpenseCategory implements Parcelable {
    public int id;
    public String categoryTitle;
    public String categoryColor;

    public ExpenseCategory() {
    }

    protected ExpenseCategory(Parcel in) {
        id = in.readInt();
        categoryTitle = in.readString();
        categoryColor = in.readString();
    }

    public static final Creator<ExpenseCategory> CREATOR = new Creator<ExpenseCategory>() {
        @Override
        public ExpenseCategory createFromParcel(Parcel in) {
            return new ExpenseCategory(in);
        }

        @Override
        public ExpenseCategory[] newArray(int size) {
            return new ExpenseCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryTitle);
        dest.writeString(categoryColor);
    }
}
