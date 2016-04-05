package com.example.rajesh.expensetracker.account;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.TestActivity;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;


public class AccountFragment extends BaseFragment implements AccountView {

    @Bind(R.id.edt_account_title)
    EditText edtAccountTitle;

    @Bind(R.id.edt_account_created_date)
    EditText edtAccountCreateDate;

    @Bind(R.id.edt_account_amount)
    EditText edtAccountAmount;

    @Bind(R.id.swh_account_type)
    SwitchCompat swhAccountType;

    @Bind(R.id.btn_add_contact)
    Button btnAddContact;

    AccountPresenterContract accountPresenterContract;
    long accountCreationTime;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountPresenterContract = new AccountPresenter(this);

        edtAccountCreateDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtAccountCreateDate.setText(getExpenseDate());
                } else {

                }
            }
        });

    }

    @OnClick({R.id.btn_add_contact})
    public void onClick() {
        addAccount();
    }

    private void addAccount() {
        Account account = new Account();
        account.accountName = edtAccountTitle.getText().toString();
        account.amount = Integer.parseInt(edtAccountAmount.getText().toString());
        account.date = accountCreationTime;
        account.accountType = swhAccountType.isChecked() ? Constant.RECURRING_TYPE : Constant.NON_RECURRING_TYPE;
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

    /**
     * Provides expense date in Format Jul 23,2016
     *
     * @return expense date as string
     */
    public String getExpenseDate() {
        final String[] expenseDate = {null};

        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyy");
                String expenseDate = simpleDateFormat.format(calendar.getTime());
                Timber.d("time in milliseconds %d", calendar.getTimeInMillis());
                accountCreationTime = calendar.getTimeInMillis();
                edtAccountCreateDate.setText(expenseDate.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();

        return expenseDate[0];
    }
}
