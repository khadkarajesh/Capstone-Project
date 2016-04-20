package com.example.rajesh.expensetracker.dashboard;


public interface DashboardPresenterContract {
    void getData(String expenseType);

    void getTotalAmount();

    void getDistinctRecurringExpense(String expenseType);
}
