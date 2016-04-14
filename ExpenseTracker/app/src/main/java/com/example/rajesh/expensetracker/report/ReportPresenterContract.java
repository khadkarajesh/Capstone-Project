package com.example.rajesh.expensetracker.report;


public interface ReportPresenterContract {
    void getTotalAmountByTimeStamp(ReportFragment.ReportType reportType);

    void getExpenseCategoryByTimeStamp(ReportFragment.ReportType reportType);
}
