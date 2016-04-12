package com.example.rajesh.expensetracker.dashboard;


import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

public interface ExpenseView {
    interface Display extends ExpenseView {
        void showExpenses(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> expenseCategories);

        void showNoExpensesView();

        void provideTotalAmount(long totalAmount);
    }

    interface Storage extends ExpenseView {
        void dataSaveSuccess();

        void dataSaveFailure(String message);
    }
}
