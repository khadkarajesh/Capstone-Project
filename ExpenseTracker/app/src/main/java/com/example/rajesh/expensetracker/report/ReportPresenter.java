package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.account.list.AccountModelContract;
import com.example.rajesh.expensetracker.category.CategoryModel;
import com.example.rajesh.expensetracker.category.CategoryModelContract;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.category.OnCategoryLoadedListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportPresenter implements ReportPresenterContract, OnExpenseListListener, OnCategoryLoadedListener {

    ReportView reportView;
    ReportModelContract reportModel;
    CategoryModelContract categoryModelContract;
    AccountModelContract accountModelContract;
    ReportFragment.ReportType reportType=null;

    public ReportPresenter(ReportView reportView) {
        this.reportView = reportView;
        this.reportModel = new ReportModel();
        categoryModelContract = new CategoryModel();
    }

    @Override
    public void listExpenseByCategory(HashMap<ExpenseCategory, Integer> hashMap) {
        reportView.showReport(hashMap);
    }

    @Override
    public void onFailureToListExpense(String message) {

    }

    @Override
    public void onCategoryLoadedSuccess(ArrayList<ExpenseCategory> categories) {
        reportModel.getExpenseByCategory(categories, reportType,this);
    }

    @Override
    public void onCategoryLoadedFailure(String message) {

    }

    @Override
    public void getExpenseByCategory(ReportFragment.ReportType reportType) {
        categoryModelContract.fetchCategories(this);
        this.reportType = reportType;
    }
}
