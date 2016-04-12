package com.example.rajesh.expensetracker.dashboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.edit.AccountEditActivity;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.expense.ExpenseEditActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;


public class DashBoardFragment extends BaseFragment implements ExpenseView.Display {
    ExpenseAdapter expenseAdapter;

    @Bind(R.id.rv_dashboard)
    RecyclerView rvDashBoard;

    @Bind(R.id.tv_total_amount)
    TextView tvTotalAmount;

    @Bind(R.id.tv_total_expense)
    TextView tvTotalExpense;

    @Bind(R.id.tv_remaining_amount)
    TextView tvRemainingAmount;

    DashboardPresenter dashboardPresenter;
    long mExpenses = 0;
    long mTotalAmount = 0;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewAdapter();

        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getData(null);
        dashboardPresenter.getTotalAmount();

        tvRemainingAmount.setText("" + getRemainingAmount());
    }

    private long getRemainingAmount() {
        return mTotalAmount - mExpenses;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dash_board;
    }

    private void setRecyclerViewAdapter() {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        rvDashBoard.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseAdapter = new ExpenseAdapter(getActivity(), expenses, expenseCategories);
        rvDashBoard.setAdapter(expenseAdapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                expenseAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvDashBoard);
    }

    @OnClick({R.id.fab_add_expense, R.id.fab_add_account})
    public void onClick(View view) {
        Timber.d("clicked");
        switch (view.getId()) {
            case R.id.fab_add_account:
                Timber.d("add account button clicked");
                getActivity().startActivity(AccountEditActivity.getLaunchIntent(getActivity(), null));
                break;
            case R.id.fab_add_expense:
                getActivity().startActivity(ExpenseEditActivity.getLaunchIntent(getActivity(), null, null));
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showExpenses(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> categories) {
        expenseAdapter.addExpenses(expenses, categories);
        calculateTotalExpense(expenses);
    }

    private void calculateTotalExpense(ArrayList<Expense> expenses) {
        for (Expense expense : expenses) {
            mExpenses = mExpenses + expense.expenseAmount;
        }
        tvTotalExpense.setText("" + mExpenses);
    }

    @Override
    public void showNoExpensesView() {

    }

    @Override
    public void provideTotalAmount(long totalAmount) {
        mTotalAmount = totalAmount;
        tvTotalAmount.setText("" + totalAmount);
    }
}
