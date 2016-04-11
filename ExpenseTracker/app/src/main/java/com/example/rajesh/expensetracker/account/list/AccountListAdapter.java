package com.example.rajesh.expensetracker.account.list;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.account.edit.AccountEditActivity;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListHolder> {

    ArrayList<Account> mAccounts;
    Context mContext;

    public AccountListAdapter(Context context, ArrayList<Account> accounts) {
        this.mContext = context;
        this.mAccounts = accounts;
    }

    @Override
    public AccountListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_layout, parent, false);
        return new AccountListHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountListHolder holder, final int position) {
        holder.tvAccountName.setText(mAccounts.get(position).accountName);
        holder.tvAccountPrice.setText("" + mAccounts.get(position).amount);

        holder.llAccountContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mContext.startActivity(AccountEditActivity.getLaunchIntent(mContext, mAccounts.get(position)));
                return false;
            }
        });


    }

    public void addAccounts(ArrayList<Account> accounts) {
        mAccounts.clear();
        mAccounts.addAll(accounts);
        notifyDataSetChanged();
    }

    public void removeAccount(int position) {
        ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().delete(ExpenseTrackerContract.AccountEntry.buildAccountUri(mAccounts.get(position).accountId), null, null);
        mAccounts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, mAccounts.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    public static class AccountListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_account_name)
        TextView tvAccountName;

        @Bind(R.id.tv_account_price)
        TextView tvAccountPrice;

        @Bind(R.id.ll_account_container)
        LinearLayout llAccountContainer;

        public AccountListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
