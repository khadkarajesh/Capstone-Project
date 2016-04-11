package com.example.rajesh.expensetracker.dashboard;

import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;


public class DashboardPresenter implements DashboardPresenterContract, OnExpenseResultListener {

    private static final String TAG = DashboardPresenter.class.getSimpleName();
    ExpenseView.Display expenseView;
    ExpenseModel expenseModel;

    public DashboardPresenter(ExpenseView.Display expenseView) {
        this.expenseView = expenseView;
        this.expenseModel = new ExpenseModel();
    }

    @Override
    public void getData(String expenseType) {
        expenseModel.getExpense(this, expenseType);
    }


    @Override
    public void onExpenseSuccess(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> expenseCategories) {
        expenseView.showExpenses(expenses, expenseCategories);
    }

    @Override
    public void onExpenseFailure() {

    }
}
