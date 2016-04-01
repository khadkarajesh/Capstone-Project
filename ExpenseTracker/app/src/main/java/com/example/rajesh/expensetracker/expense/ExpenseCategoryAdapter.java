package com.example.rajesh.expensetracker.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpenseCategoryAdapter extends BaseAdapter {
    ArrayList<ExpenseCategory> categories = new ArrayList<>();
    Context mContext;
    View view;

    public ExpenseCategoryAdapter(Context context, ArrayList<ExpenseCategory> data) {
        this.categories = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExpenseCategoryHolder holder;
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.single_catgories_layout, null);
            holder = new ExpenseCategoryHolder(view);
            view.setTag(holder);
        } else {
            holder = (ExpenseCategoryHolder) view.getTag();
        }
        //holder.tvCategoriesName.setText(categories.get(position).categoryTitle);
        holder.tvCategoriesName.setText("hello categories");
        return view;
    }

    public static class ExpenseCategoryHolder {
        @Bind(R.id.iv_categories_indicator)
        ImageView ivCategoriesIndicator;

        @Bind(R.id.tv_categories_name)
        TextView tvCategoriesName;

        public ExpenseCategoryHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
