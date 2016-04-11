package com.example.rajesh.expensetracker.account.edit;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.TestActivity;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;


public class AddAccountFragment extends BaseFragment implements AccountAddView {

    @Bind(R.id.edt_account_title)
    EditText edtAccountTitle;

    @Bind(R.id.edt_account_created_date)
    EditText edtAccountCreateDate;

    @Bind(R.id.edt_account_amount)
    EditText edtAccountAmount;

    @Bind(R.id.swh_account_type)
    SwitchCompat swhAccountType;

    AddAccountPresenterContract addAccountPresenterContract;

    long accountCreationTime = 0;
    Account mAccount;

    public static AddAccountFragment getInstance(Account account) {
        AddAccountFragment addAccountFragment = new AddAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACCOUNT, account);
        addAccountFragment.setArguments(bundle);
        return addAccountFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addAccountPresenterContract = new AddAccountPresenter(this);

        edtAccountCreateDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtAccountCreateDate.setText(getExpenseDate());
                }
            }
        });

        if (mAccount != null) {
            edtAccountTitle.setText(mAccount.accountName);
            edtAccountAmount.setText("" + mAccount.amount);
            edtAccountCreateDate.setText(DateTimeUtil.getTimeInFormattedString(accountCreationTime));
            swhAccountType.setChecked(mAccount.accountType.equals(Constant.RECURRING_TYPE) ? true : false);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getParcelable(Constant.ACCOUNT) != null) {
            mAccount = getArguments().getParcelable(Constant.ACCOUNT);
            accountCreationTime = mAccount.date;
            Timber.d("called time in millis %d", accountCreationTime);
        }
    }

    @OnClick({R.id.btn_add_contact})
    public void onClick() {
        if (mAccount == null) {
            addAccountPresenterContract.addAccount(getAccount());
        } else {
            addAccountPresenterContract.updateAccount(getAccount());
        }
    }

    private Account getAccount() {
        Account account = new Account();
        if (mAccount != null) {
            account.accountId = mAccount.accountId;
        }
        account.accountName = edtAccountTitle.getText().toString();
        account.amount = Integer.parseInt(edtAccountAmount.getText().toString());
        account.date = accountCreationTime;
        account.accountType = swhAccountType.isChecked() ? Constant.RECURRING_TYPE : Constant.NON_RECURRING_TYPE;
        return account;
    }

    @Override
    protected int getLayout() {
        return R.layout.add_fragment_account;
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
