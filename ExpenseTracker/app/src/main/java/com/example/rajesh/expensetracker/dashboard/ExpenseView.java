package com.example.rajesh.expensetracker.dashboard;


import java.util.ArrayList;

public interface ExpenseView {
    void showExpenses(ArrayList<Expense> expenses);

    void showNoExpensesView();
}
