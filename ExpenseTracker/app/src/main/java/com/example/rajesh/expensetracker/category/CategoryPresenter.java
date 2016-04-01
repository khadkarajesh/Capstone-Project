package com.example.rajesh.expensetracker.category;


import java.util.ArrayList;

public class CategoryPresenter implements CategoryPresenterContract, OnCategoryLoadedListener {
    CategoryView categoryView;
    CategoryModelContract categoryModelContract;

    public CategoryPresenter(CategoryView categoryView) {
        this.categoryView = categoryView;
        categoryModelContract = new CategoryModel();
    }

    @Override
    public void getCategories() {
        categoryModelContract.fetchCategories(this);
    }

    @Override
    public void onCategoryLoadedSuccess(ArrayList<ExpenseCategory> categories) {
        categoryView.showCategories(categories);
    }

    @Override
    public void onCategoryLoadedFailure(String message) {
        categoryView.showError(message);
    }
}
