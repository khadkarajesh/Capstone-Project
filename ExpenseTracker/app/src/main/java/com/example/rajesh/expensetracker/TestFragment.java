package com.example.rajesh.expensetracker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.CategoryAdapter;
import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BaseFragment {


    @Bind(R.id.rv_test)
    RecyclerView recyclerView;

    public TestFragment() {
        // Required empty public constructor
    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CategoryAdapter(getActivity(),new ArrayList<ExpenseCategory>()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_test;
    }

}
