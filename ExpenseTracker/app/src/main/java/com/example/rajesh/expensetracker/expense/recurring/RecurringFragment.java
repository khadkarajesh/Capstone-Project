package com.example.rajesh.expensetracker.expense.recurring;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.DashboardPresenter;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.dashboard.ExpenseAdapter;
import com.example.rajesh.expensetracker.dashboard.ExpenseView;

import java.util.ArrayList;

import butterknife.Bind;


public class RecurringFragment extends BaseFragment implements ExpenseView.Display {

    ExpenseAdapter expenseAdapter;

    @Bind(R.id.rv_recurring_expense)
    RecyclerView rvDashBoard;

    DashboardPresenter dashboardPresenter;

    public RecurringFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewAdapter();

        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getData(Constant.RECURRING_TYPE);

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


    @Override
    protected int getLayout() {
        return R.layout.fragment_reccuring;
    }

    @Override
    public void showExpenses(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> expenseCategories) {
        expenseAdapter.addExpenses(expenses, expenseCategories);
    }

    @Override
    public void showNoExpensesView() {

    }

    @Override
    public void provideTotalAmount(long totalAmount) {

    }
}
