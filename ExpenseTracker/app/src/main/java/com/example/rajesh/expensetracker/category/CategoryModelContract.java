package com.example.rajesh.expensetracker.category;


public interface CategoryModelContract {
    void fetchCategories(OnCategoryLoadedListener onCategoryLoadedListener);
}
