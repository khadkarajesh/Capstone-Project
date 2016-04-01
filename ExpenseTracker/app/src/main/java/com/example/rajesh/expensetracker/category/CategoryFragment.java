package com.example.rajesh.expensetracker.category;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements CategoryView {


    @Bind(R.id.rv_category)
    RecyclerView rvCategory;

    CategoryAdapter categoryAdapter;

    CategoryPresenter categoryPresenter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryPresenter = new CategoryPresenter(this);
        setRecyclerViewAdapter();
        categoryPresenter.getCategories();

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void showCategories(ArrayList<ExpenseCategory> categories) {
        categoryAdapter.addCategories(categories);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
    }

    private void setRecyclerViewAdapter() {
        ArrayList<ExpenseCategory> expenses = new ArrayList<>();
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(expenses);
        rvCategory.setAdapter(categoryAdapter);
    }
}
