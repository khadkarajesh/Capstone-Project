package com.example.rajesh.expensetracker.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.account.list.AccountPresenter;
import com.example.rajesh.expensetracker.account.list.AccountPresenterContract;
import com.example.rajesh.expensetracker.account.list.AccountView;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.DashboardPresenter;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.dashboard.ExpenseView;

import java.util.ArrayList;
import java.util.Calendar;

import timber.log.Timber;


public class ExpenseTrackerBroadCastReceiver extends BroadcastReceiver implements ExpenseView.Display, AccountView {

    DashboardPresenter dashboardPresenter;
    AccountPresenterContract accountPresenterContract;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        Timber.d("receiver called");
        mContext = context;
        checkRecurringAccount();
        checkRecurringExpense();
    }

    private void checkRecurringExpense() {
        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getDistinctRecurringExpense(Constant.RECURRING_TYPE);
    }

    private void checkRecurringAccount() {
        accountPresenterContract = new AccountPresenter(this);
        accountPresenterContract.getDistinctRecurringAccounts(Constant.RECURRING_TYPE);
    }

    @Override
    public void showExpenses(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> expenseCategories) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = 0, currentDay = 0;
        currentYear = calendar.get(Calendar.YEAR);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        for (Expense expense : expenses) {
            calendar.setTimeInMillis(expense.expenseDate);

            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (year == currentYear && day == currentDay) {
                Timber.d(" year %d and day %d", year, day);
                showExpenseNotification(expense);
            }
        }
    }

    @Override
    public void showNoExpensesView() {

    }

    @Override
    public void provideTotalAmount(long totalAmount) {

    }


    public void showExpenseNotification(Expense expense) {
        Timber.d("tick tick tick  expense amount %d type ", expense.expenseAmount);


        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.tongue);

        Intent intent = new Intent(mContext, ConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXPENSE, expense);
        bundle.putString(ConfirmationActivity.EXTRA_DATA_TYPE, ConfirmationActivity.EXPENSE_DATA_TYPE);
        intent.putExtra(ConfirmationActivity.EXTRA_BUNDLE_VALUE, bundle);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle("Expense Tracker")
                .setContentText("Add Recurring Expense")
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.tongue)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(100, builder.build());
    }

    public void showAccountNotification(Account account) {
        Timber.d("account notification called");
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.tongue);

        Intent intent = new Intent(mContext, ConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACCOUNT, account);
        bundle.putString(ConfirmationActivity.EXTRA_DATA_TYPE, ConfirmationActivity.ACCOUNT_DATA_TYPE);
        intent.putExtra(ConfirmationActivity.EXTRA_BUNDLE_VALUE, bundle);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle("Expense Tracker")
                .setContentText("Add Recurring Account")
                .setSmallIcon(R.drawable.tongue)
                .setLargeIcon(bitmap)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);

        notificationManager.notify(200, builder.build());
    }

    @Override
    public void showAccounts(ArrayList<Account> accounts) {
        Timber.d("hello recurring accounts ");
        Calendar calendar = Calendar.getInstance();
        int currentYear = 0, currentDay = 0;
        currentYear = calendar.get(Calendar.YEAR);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        for (Account account : accounts) {
            Timber.d("accounts ----- type %s", account.accountType);
            calendar.setTimeInMillis(account.date);

            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (year == currentYear && day == currentDay) {
                Timber.d("account :: %d and year :: %d ", year, day);
                showAccountNotification(account);
            }
        }
    }

    @Override
    public void showError(String message) {

    }
}
