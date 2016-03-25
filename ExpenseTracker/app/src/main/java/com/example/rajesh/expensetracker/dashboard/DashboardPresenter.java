package com.example.rajesh.expensetracker.dashboard;

import java.util.ArrayList;


public class DashboardPresenter implements DashboardPresenterContract, OnExpenseResultListener {

    private static final String TAG = DashboardPresenter.class.getSimpleName();
    ExpenseView expenseView;
    ExpenseModel expenseModel;

    public DashboardPresenter(ExpenseView expenseView) {
        this.expenseView = expenseView;
        this.expenseModel = new ExpenseModel();
    }

    @Override
    public void getData() {
        expenseModel.getExpense(this);
    }


    @Override
    public void onExpenseSuccess(ArrayList<Expense> expenses) {
        expenseView.showExpenses(expenses);
    }

    @Override
    public void onExpenseFailure() {

    }
}
