package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.category.CategoryModel;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.category.OnCategoryLoadedListener;
import com.example.rajesh.expensetracker.dashboard.ExpenseModel;
import com.example.rajesh.expensetracker.dashboard.OnAccountResultListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportPresenter implements ReportPresenterContract, OnAccountResultListener,OnCategoryLoadedListener,OnExpenseListListener {
    ReportView reportView;
    ExpenseModel expenseModel;
    CategoryModel categoryModel;
    ReportFragment.ReportType reportType;
    ReportModelContract reportModelContract;

    public ReportPresenter(ReportView reportView) {
        this.reportView = reportView;
        expenseModel = new ExpenseModel();
        categoryModel=new CategoryModel();
        reportModelContract=new ReportModel();

    }

    @Override
    public void getTotalAmountByTimeStamp(ReportFragment.ReportType reportType) {
        expenseModel.getAccountsByMonth(this, reportType);
    }

    @Override
    public void getExpenseCategoryByTimeStamp(ReportFragment.ReportType reportType) {
        this.reportType = reportType;
        categoryModel.fetchCategories(this);
    }

    @Override
    public void onAccountByTimeStampListSuccess(long totalAmount) {
        reportView.provideTotalAccountByTimeStamp(totalAmount);
    }

    @Override
    public void onAccountByTimeStampListFailure(String message) {

    }

    @Override
    public void onCategoryLoadedSuccess(ArrayList<ExpenseCategory> categories) {
        reportModelContract.getExpenseByCategory(categories,reportType,this);
    }

    @Override
    public void onCategoryLoadedFailure(String message) {

    }

    @Override
    public void listExpenseByCategory(HashMap<ExpenseCategory, Integer> hashMap) {
        reportView.provideExpenseByCategory(hashMap);
    }

    @Override
    public void onFailureToListExpense(String message) {
    }
}
