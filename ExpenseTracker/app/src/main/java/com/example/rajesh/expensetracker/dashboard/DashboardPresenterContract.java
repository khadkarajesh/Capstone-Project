package com.example.rajesh.expensetracker.dashboard;

/**
 * Created by rajesh on 3/24/16.
 */
public interface DashboardPresenterContract {
    void getData(String expenseType);

    void getTotalAmount();

}
