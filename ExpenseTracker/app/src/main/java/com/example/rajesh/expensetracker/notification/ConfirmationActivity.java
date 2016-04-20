package com.example.rajesh.expensetracker.notification;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.DashboardActivity;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.account.edit.AccountAddView;
import com.example.rajesh.expensetracker.account.edit.AddAccountPresenter;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.dashboard.ExpenseView;
import com.example.rajesh.expensetracker.expense.ExpenseStoragePresenter;

import java.util.Calendar;

import timber.log.Timber;

public class ConfirmationActivity extends BaseActivity implements ExpenseView.Storage, AccountAddView {

    public static final String EXTRA_DATA_TYPE = "extra_data_type";
    public static final String EXPENSE_DATA_TYPE = "expense_data_type";
    public static final String ACCOUNT_DATA_TYPE = "account_data_type";
    public static final String EXTRA_BUNDLE_VALUE = "extra_bundle_value";

    private static final String CONFIRMATION_DIALOG_CONTENT_TEMPLATE = "Do you want to add recurring ";
    private Expense expense = null;
    private Account account = null;

    String extraType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String title = null;
        String content = null;
        extraType = getIntent().getBundleExtra(EXTRA_BUNDLE_VALUE).getString(EXTRA_DATA_TYPE);


        if (extraType.equals(EXPENSE_DATA_TYPE)) {
            expense = getIntent().getBundleExtra(EXTRA_BUNDLE_VALUE).getParcelable(Constant.EXPENSE);
            title = "Add Expense";
            content = CONFIRMATION_DIALOG_CONTENT_TEMPLATE + " " + " expense " + expense.expenseAmount + "?";
        }
        if (extraType.equals(ACCOUNT_DATA_TYPE)) {
            Timber.d("account type data notification");
            account = getIntent().getBundleExtra(EXTRA_BUNDLE_VALUE).getParcelable(Constant.ACCOUNT);
            title = "Add Account";
            content = CONFIRMATION_DIALOG_CONTENT_TEMPLATE + " " + " account " + account.amount + "?";
        }
        showConfirmationDialog(title, content);
    }

    private void showConfirmationDialog(String title, String content) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(content).setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeStateOfData();
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    private void changeStateOfData() {
        if (extraType.equals(EXPENSE_DATA_TYPE)) {
            insertRecurringExpense();
        }
        if (extraType.equals(ACCOUNT_DATA_TYPE)) {
            insertRecurringAccount();
        }
    }

    private void insertRecurringAccount() {
        AddAccountPresenter addAccountPresenter = new AddAccountPresenter(this);
        Calendar calendar = Calendar.getInstance();
        account.date = calendar.getTimeInMillis();
        addAccountPresenter.addAccount(account);
    }

    private void insertRecurringExpense() {
        ExpenseStoragePresenter expenseStoragePresenter = new ExpenseStoragePresenter(this);
        Calendar calendar = Calendar.getInstance();
        expense.expenseDate = calendar.getTimeInMillis();
        expenseStoragePresenter.saveExpense(expense);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_confirmation;
    }

    @Override
    public void dataSaveSuccess() {
        startActivity(DashboardActivity.getLaunchIntent(this));
        finish();
    }

    @Override
    public void dataSaveFailure(String message) {

    }

    @Override
    public void navigateToActivity() {
        startActivity(DashboardActivity.getLaunchIntent(this));
        finish();
    }

    @Override
    public void showFailureMessage(String message) {

    }
}
