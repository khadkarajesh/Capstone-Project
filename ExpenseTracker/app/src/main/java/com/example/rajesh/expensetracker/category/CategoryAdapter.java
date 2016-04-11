package com.example.rajesh.expensetracker.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;
import com.example.rajesh.expensetracker.widget.CircularView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ExpenseHolder> {

    ArrayList<ExpenseCategory> categories = new ArrayList<>();
    CategoryLongPressListener categoryLongPressListener = null;
    Context context;

    public CategoryAdapter(Context context, ArrayList<ExpenseCategory> expenses) {
        this.context = context;
        this.categories = expenses;
    }

    public void setOnCategoryLongPressListener(CategoryLongPressListener categoryLongPressListener) {

        this.categoryLongPressListener = categoryLongPressListener;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_list_layout, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, final int position) {
        holder.expenseTitle.setText(categories.get(position).categoryTitle);
        holder.circularView.setFillColor(categories.get(position).categoryColor);
        holder.llContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.startActivity(CategoryEditActivity.getLaunchIntent(context, categories.get(position)));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategories(ArrayList<ExpenseCategory> expenses) {
        categories.addAll(expenses);
        notifyDataSetChanged();
    }

    public void deleteItem(int itemPosition) {
        ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().delete(ExpenseTrackerContract.ExpenseCategoriesEntry.buildUriWithCategoryId(categories.get(itemPosition).id), null, null);
        categories.remove(itemPosition);
        notifyItemRangeRemoved(itemPosition, categories.size());
        notifyItemRemoved(itemPosition);
        notifyDataSetChanged();
    }


    public static class ExpenseHolder extends RecyclerView.ViewHolder {
        TextView expenseTitle;
        CircularView circularView;
        LinearLayout llContainer;

        public ExpenseHolder(View itemView) {
            super(itemView);
            expenseTitle = (TextView) itemView.findViewById(R.id.tv_categories_title);
            circularView = (CircularView) itemView.findViewById(R.id.iv_categories_indicator);
            llContainer = (LinearLayout) itemView.findViewById(R.id.ll_container);
        }
    }
}
