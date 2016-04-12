package com.example.rajesh.expensetracker.account.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;

public class AccountEditActivity extends BaseActivity {

    private static final String ADD_ACCOUNT = "Add Account";
    private static final String EDIT_ACCOUNT = "Edit Account";

    Toolbar toolbar;
    Account mAccount = null;
    String toolbarTitle;

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


        if (getIntent().getBundleExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE) != null) {
            mAccount = getIntent().getBundleExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.ACCOUNT);
            toolbarTitle = mAccount == null ? ADD_ACCOUNT : EDIT_ACCOUNT;
            getSupportFragmentManager().beginTransaction().replace(R.id.ll_account_edit_container, AddAccountFragment.getInstance(mAccount)).commit();
        }

        getSupportActionBar().setTitle(toolbarTitle);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_account_edit;
    }

    public static Intent getLaunchIntent(Context context, Account account) {
        Intent intent = new Intent(context, AccountEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACCOUNT, account);
        intent.putExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE, bundle);
        return intent;
    }
}
