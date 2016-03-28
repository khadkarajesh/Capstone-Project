package com.example.rajesh.expensetracker.expense;


import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.dashboard.ExpenseCategory;

import java.util.ArrayList;

import butterknife.Bind;


public class AddExpense extends BaseFragment {


    @Bind(R.id.edt_expense_date)
    EditText edtExpenseDate;

    @Bind(R.id.edt_expense_title)
    EditText edtExpenseTitle;

    @Bind(R.id.edt_expense_amount)
    EditText edtExpenseAmount;

    @Bind(R.id.spinner_expense_categories)
    AppCompatSpinner spinnerExpenseCategories;

    @Bind(R.id.tv_category_label)
    TextView tvCategoryLabel;

    public AddExpense() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       edtExpenseAmount.getHintTextColors();


        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        ExpenseCategoryAdapter expenseCategoriesAdapter = new ExpenseCategoryAdapter(getActivity(), expenseCategories);
        spinnerExpenseCategories.setAdapter(expenseCategoriesAdapter);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_expense;
    }

}
