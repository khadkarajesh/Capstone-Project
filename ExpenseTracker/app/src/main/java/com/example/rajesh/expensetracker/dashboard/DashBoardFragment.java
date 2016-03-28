package com.example.rajesh.expensetracker.dashboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements ExpenseView {
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
        rvDashBoard.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseAdapter = new ExpenseAdapter(expenses);
        rvDashBoard.setAdapter(expenseAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showExpenses(ArrayList<Expense> expenses) {
        expenseAdapter.addExpenses(expenses);
    }

    @Override
    public void showNoExpensesView() {

    }
}
