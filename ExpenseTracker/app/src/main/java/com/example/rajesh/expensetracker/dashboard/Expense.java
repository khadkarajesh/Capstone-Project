package com.example.rajesh.expensetracker.dashboard;


public class Expense {

    public enum ExpenseType {
        RECURRING, NON_RECURRING;
    }

    public long expenseDate;
    public String expenseTitle;
    public String expenseDescription;
    public int expenseAmount;
    public ExpenseCategories expenseCategories;
    public ExpenseType expenseType;
}
