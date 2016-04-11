package com.example.rajesh.expensetracker.account.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;


public class AccountFragment extends BaseFragment implements AccountView {

    @Bind(R.id.rv_account_list)
    RecyclerView rvAccount;

    AccountListAdapter accountListAdapter;
    AccountPresenterContract accountPresenterContract;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView();

        accountPresenterContract = new AccountPresenter(this);
        accountPresenterContract.getAccounts();
    }

    private void setRecyclerView() {
        rvAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Account> accounts = new ArrayList<>();
        accountListAdapter = new AccountListAdapter(getActivity(), accounts);
        rvAccount.setAdapter(accountListAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                accountListAdapter.removeAccount(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvAccount);
    }

    @Override
    public void showAccounts(ArrayList<Account> accounts) {
        accountListAdapter.addAccounts(accounts);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_account;
    }
}
