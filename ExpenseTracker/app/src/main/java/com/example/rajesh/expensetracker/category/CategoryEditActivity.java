package com.example.rajesh.expensetracker.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;

public class CategoryEditActivity extends BaseActivity {

    Toolbar toolbar;
    ExpenseCategory expenseCategory;

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

        if (getIntent().getBundleExtra(Constant.CATEGORY_EDIT_ACTIVITY_BUNDLE) != null) {
            expenseCategory = getIntent().getBundleExtra(Constant.CATEGORY_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.CATEGORY);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.ll_category_edit_container, AddCategoryFragment.getInstance(expenseCategory), Constant.FragmentTag.EXPENSE_FRAGMENT).commit();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_category_edit;
    }

    public static Intent getLaunchIntent(Context context, ExpenseCategory expenseCategory) {
        Intent intent = new Intent(context, CategoryEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.CATEGORY, expenseCategory);
        intent.putExtra(Constant.CATEGORY_EDIT_ACTIVITY_BUNDLE, bundle);
        return intent;
    }
}
