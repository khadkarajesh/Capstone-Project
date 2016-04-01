package com.example.rajesh.expensetracker.category;


import java.util.ArrayList;

public interface CategoryView {
    void showCategories(ArrayList<ExpenseCategory> categories);

    void showError(String message);
}
