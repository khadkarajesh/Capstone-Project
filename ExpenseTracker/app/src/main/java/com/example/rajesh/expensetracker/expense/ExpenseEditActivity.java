package com.example.rajesh.expensetracker.expense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.Expense;

public class ExpenseEditActivity extends BaseActivity {

    Expense expense = null;
    ExpenseCategory expenseCategory = null;
    Toolbar toolbar;
    String toolbarTitle;

    private static final String ADD_EXPENSE = "Add Expense";
    private static final String EDIT_EXPENSE = "Edit Expense";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getIntent().getBundleExtra(Constant.EXPENSE_EDIT_ACTIVITY_BUNDLE) != null) {
            expense = getIntent().getBundleExtra(Constant.EXPENSE_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.EXPENSE);
            expenseCategory = getIntent().getBundleExtra(Constant.EXPENSE_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.EXPENSE_CATEGORY);
            toolbarTitle = expense == null ? ADD_EXPENSE : EDIT_EXPENSE;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_expense_edit_container, ExpenseFragment.getInstance(expense, expenseCategory), Constant.FragmentTag.EXPENSE_FRAGMENT).commit();
        getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_expense_edit;
    }

    public static Intent getLaunchIntent(Context context, Expense expense, ExpenseCategory expenseCategory) {
        Intent intent = new Intent(context, ExpenseEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXPENSE, expense);
        bundle.putParcelable(Constant.EXPENSE_CATEGORY, expenseCategory);
        intent.putExtra(Constant.EXPENSE_EDIT_ACTIVITY_BUNDLE, bundle);
        return intent;
    }
}
