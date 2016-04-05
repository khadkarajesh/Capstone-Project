package com.example.rajesh.expensetracker.category;


import android.database.Cursor;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

import java.util.ArrayList;

public class CategoryModel implements CategoryModelContract {
    @Override
    public void fetchCategories(OnCategoryLoadedListener categoryLoadedListener) {
        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        Cursor cursor = ExpenseTrackerApplication
                .getExpenseTrackerApplication()
                .getContentResolver()
                .query(ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_URI, null, null, null, ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME + " ASC");

        while (cursor.moveToNext()) {
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.id=cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.ExpenseCategoriesEntry._ID));
            expenseCategory.categoryTitle = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME));
            expenseCategory.categoryColor = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_COLOR));
            expenseCategories.add(expenseCategory);
        }

        if (!expenseCategories.isEmpty()) {
            categoryLoadedListener.onCategoryLoadedSuccess(expenseCategories);
        } else {
            categoryLoadedListener.onCategoryLoadedFailure("No Categories found");
        }
    }
}
