package com.example.rajesh.expensetracker.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import butterknife.Bind;


public class AccountFragment extends BaseFragment {

    @Bind(R.id.edt_account_title)
    EditText edtAccountTitle;

    @Bind(R.id.edt_account_amount)
    EditText edtAccountAmount;

    @Bind(R.id.swh_account_type)
    SwitchCompat swhAccountType;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_account;
    }

}
