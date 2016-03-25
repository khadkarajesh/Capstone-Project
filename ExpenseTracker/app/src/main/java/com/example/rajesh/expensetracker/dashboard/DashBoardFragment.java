package com.example.rajesh.expensetracker.dashboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rajesh.expensetracker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment implements ExpenseView {

    private static final String TAG = DashBoardFragment.class.getSimpleName();
    View view;
    ExpenseAdapter expenseAdapter;
    RecyclerView rvDashBoard;
    DashboardPresenter dashboardPresenter;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        setRecyclerViewAdapter();

        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.getData();

        return view;
    }

    private void setRecyclerViewAdapter() {
        ArrayList<Expense> expenses = new ArrayList<>();
        rvDashBoard = (RecyclerView) view.findViewById(R.id.rv_dashboard);
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
