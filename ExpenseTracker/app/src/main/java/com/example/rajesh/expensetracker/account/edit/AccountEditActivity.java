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

    Toolbar toolbar;
    Account mAccount = null;

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
            /*if (getIntent().getBundleExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE).getBoolean(Constant.ACCOUNT_EDIT)) {
                mAccount = getIntent().getBundleExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.ACCOUNT);
            }*/
            mAccount = getIntent().getBundleExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE).getParcelable(Constant.ACCOUNT);
            getSupportFragmentManager().beginTransaction().replace(R.id.ll_account_edit_container, AddAccountFragment.getInstance(mAccount)).commit();
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_account_edit;
    }

    public static Intent getLaunchIntent(Context context, Account account) {
        Intent intent = new Intent(context, AccountEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACCOUNT, account);
        //bundle.putBoolean(Constant.ACCOUNT_EDIT, accountEdit);
        intent.putExtra(Constant.ACCOUNT_EDIT_ACTIVITY_BUNDLE, bundle);
        return intent;
    }
}
