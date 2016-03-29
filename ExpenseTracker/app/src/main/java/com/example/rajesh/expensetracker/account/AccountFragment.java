package com.example.rajesh.expensetracker.account;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.TestActivity;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;


public class AccountFragment extends BaseFragment implements AccountView {

    @Bind(R.id.edt_account_title)
    EditText edtAccountTitle;

    @Bind(R.id.edt_account_amount)
    EditText edtAccountAmount;

    @Bind(R.id.swh_account_type)
    SwitchCompat swhAccountType;

    @Bind(R.id.btn_add_contact)
    Button btnAddContact;

    AccountPresenterContract accountPresenterContract;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenterContract = new AccountPresenter(this);

       // addAccount();
    }

    @OnClick({R.id.btn_add_contact})
    public void onClick() {
        addAccount();
    }

    private void addAccount() {
        Account account = new Account();
        account.accountName = edtAccountTitle.getText().toString();
        account.amount = Integer.parseInt(edtAccountAmount.getText().toString());
        account.date = 34249823;
        account.accountType = swhAccountType.isChecked() ? "recurring" : "non-recurring";

        accountPresenterContract.addAccount(account);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_account;
    }

    @Override
    public void navigateToActivity() {
        startActivity(new Intent(getActivity(), TestActivity.class));
        getActivity().finish();
    }

    @Override
    public void showFailureMessage(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
    }
}
