package com.example.rajesh.expensetracker.expense;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.dashboard.ExpenseCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;


public class ExpenseFragment extends BaseFragment {


    @Bind(R.id.edt_expense_date)
    EditText edtExpenseDate;

    @Bind(R.id.edt_expense_title)
    EditText edtExpenseTitle;

    @Bind(R.id.edt_expense_amount)
    EditText edtExpenseAmount;

    @Bind(R.id.spinner_expense_categories)
    AppCompatSpinner spinnerExpenseCategories;

    @Bind(R.id.tv_category_label)
    TextView tvCategoryLabel;


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtExpenseAmount.getHintTextColors();


        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        ExpenseCategoryAdapter expenseCategoriesAdapter = new ExpenseCategoryAdapter(getActivity(), expenseCategories);
        spinnerExpenseCategories.setAdapter(expenseCategoriesAdapter);

        edtExpenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtExpenseDate.setText(getExpenseDate());
                } else {

                }
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_expense;
    }

    @OnClick({R.id.edt_expense_date})
    public void onClick() {
        //getExpenseDate();
    }

    /**
     * Provides expense date in Format Jul 23,2016
     *
     * @return expense date as string
     */
    public String getExpenseDate() {
        final String[] expenseDate = {null};

        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyy");
                String expenseDate = simpleDateFormat.format(calendar.getTime());

                edtExpenseDate.setText(expenseDate.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();

        return expenseDate[0];
    }

}
