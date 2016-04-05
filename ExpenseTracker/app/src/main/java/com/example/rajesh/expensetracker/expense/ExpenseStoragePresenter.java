package com.example.rajesh.expensetracker.expense;


import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.dashboard.ExpenseView;

public class ExpenseStoragePresenter implements ExpenseStoragePresenterContract, OnExpenseSaveListener {

    ExpenseView.Storage expenseView;
    ExpenseViewStorageModelContract expenseViewStorageModelContract;

    public ExpenseStoragePresenter(ExpenseView.Storage expenseView) {
        this.expenseView = expenseView;
        expenseViewStorageModelContract = new ExpenseStorageModel();
    }

    @Override
    public void saveExpense(Expense expense) {
        expenseViewStorageModelContract.saveExpense(expense, this);
    }

    @Override
    public void onExpenseSaveSuccess() {
        expenseView.dataSaveSuccess();
    }

    @Override
    public void onExpenseSaveFailure(String message) {
        expenseView.dataSaveFailure(message);
    }
}
