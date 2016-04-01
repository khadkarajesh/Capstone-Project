package com.example.rajesh.expensetracker.dashboard;

import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

import timber.log.Timber;


public class ExpenseModel implements ExpenseModelContract {
    @Override
    public void getExpense(OnExpenseResultListener onExpenseResultListener) {
        ArrayList<Expense> expenses = new ArrayList<>();

        Timber.d("called at Expense Model");

        Expense expense = new Expense();
        expense.expenseTitle = "MoMo";
        expense.expenseAmount = 100;

        ExpenseCategory expenseCategories = new ExpenseCategory();
        expenseCategories.categoryTitle = "Food";

        expense.expenseCategories = expenseCategories;
        expense.expenseDescription = "had momo at coffe time";
        expense.expenseType = Expense.ExpenseType.NON_RECURRING;


        Expense expenseOne = new Expense();
        expenseOne.expenseTitle = "Drinks";
        expenseOne.expenseAmount = 200;

        ExpenseCategory expenseCategoriesOne = new ExpenseCategory();
        expenseCategoriesOne.categoryTitle = "Food";

        expenseOne.expenseCategories = expenseCategories;
        expenseOne.expenseDescription = "had food on magic store";
        expenseOne.expenseType = Expense.ExpenseType.NON_RECURRING;

        expenses.add(expense);
        expenses.add(expenseOne);

        onExpenseResultListener.onExpenseSuccess(expenses);
    }
}
