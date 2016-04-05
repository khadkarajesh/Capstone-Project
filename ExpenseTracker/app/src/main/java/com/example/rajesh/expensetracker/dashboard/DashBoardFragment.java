package com.example.rajesh.expensetracker.dashboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

import butterknife.Bind;


public class DashBoardFragment extends BaseFragment implements ExpenseView.Display {
    ExpenseAdapter expenseAdapter;

    @Bind(R.id.rv_dashboard)
    RecyclerView rvDashBoard;

    DashboardPresenter dashboardPresenter;


    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewAdapter();

        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getData();

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dash_board;
    }

    private void setRecyclerViewAdapter() {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        rvDashBoard.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseAdapter = new ExpenseAdapter(expenses,expenseCategories);
        rvDashBoard.setAdapter(expenseAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showExpenses(ArrayList<Expense> expenses, ArrayList<ExpenseCategory>categories) {
        expenseAdapter.addExpenses(expenses,categories);
    }

    @Override
    public void showNoExpensesView() {

    }
}
