package com.example.rajesh.expensetracker.expense;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.CategoryPresenter;
import com.example.rajesh.expensetracker.category.CategoryPresenterContract;
import com.example.rajesh.expensetracker.category.CategoryView;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.dashboard.ExpenseView;
import com.example.rajesh.expensetracker.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;


public class ExpenseFragment extends BaseFragment implements CategoryView, ExpenseView.Storage {

    @Bind(R.id.edt_expense_date)
    EditText edtExpenseDate;

    @Bind(R.id.edt_expense_title)
    EditText edtExpenseTitle;

    @Bind(R.id.edt_expense_amount)
    EditText edtExpenseAmount;

    @Bind(R.id.edt_expense_description)
    EditText edtExpenseDescription;

    @Bind(R.id.spinner_expense_categories)
    AppCompatSpinner spinnerExpenseCategories;

    @Bind(R.id.tv_category_label)
    TextView tvCategoryLabel;

    @Bind(R.id.swh_expense_type)
    SwitchCompat swhExpenseType;

    CategoryPresenterContract categoryPresenterContract;
    ExpenseStoragePresenterContract storagePresenterContract;


    private Expense mExpense;
    private ExpenseCategory expenseCategory;

    ExpenseCategoryAdapter expenseCategoriesAdapter;
    long expenseTime;
    int categoryId = 0;

    public static ExpenseFragment getInstance(Expense expense, ExpenseCategory expenseCategory) {
        ExpenseFragment expenseFragment = new ExpenseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXPENSE, expense);
        bundle.putParcelable(Constant.EXPENSE_CATEGORY, expenseCategory);
        expenseFragment.setArguments(bundle);
        return expenseFragment;
    }


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtExpenseAmount.getHintTextColors();


        showExpenseCategory();

        categoryPresenterContract = new CategoryPresenter(this);
        categoryPresenterContract.getCategories();


        edtExpenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtExpenseDate.setText(getExpenseDate());
                }
            }
        });

        storagePresenterContract = new ExpenseStoragePresenter(this);


        setExpenseDataToUiFields();

    }

    private void setExpenseDataToUiFields() {
        if (mExpense != null) {
            edtExpenseTitle.setText(mExpense.expenseTitle);
            edtExpenseAmount.setText("" + mExpense.expenseAmount);
            edtExpenseDate.setText(DateTimeUtil.getTimeInFormattedString(mExpense.expenseDate));
            edtExpenseDescription.setText(mExpense.expenseDescription);
            swhExpenseType.setChecked(mExpense.expenseType.equals(Constant.RECURRING_TYPE) ? true : false);
        }
    }

    private void showExpenseCategory() {
        final ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
        expenseCategoriesAdapter = new ExpenseCategoryAdapter(getActivity(), expenseCategories);
        spinnerExpenseCategories.setAdapter(expenseCategoriesAdapter);

        spinnerExpenseCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = expenseCategories.get(position).id;
                Timber.d("category id  %d category string %s", categoryId, expenseCategories.get(position).categoryTitle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getParcelable(Constant.EXPENSE) != null) {
            mExpense = getArguments().getParcelable(Constant.EXPENSE);
            expenseCategory = getArguments().getParcelable(Constant.EXPENSE_CATEGORY);
            expenseTime = mExpense.expenseDate;
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_add_expense;
    }

    @OnClick({R.id.btn_save_data})
    public void onClick() {
        if (mExpense == null) {
            storagePresenterContract.saveExpense(getExpense());
        } else {
            storagePresenterContract.updateExpense(getExpense());
        }
    }

    private Expense getExpense() {
        Expense expense = new Expense();
        if (mExpense != null) {
            expense.expenseId = mExpense.expenseId;
        }
        expense.expenseTitle = edtExpenseTitle.getText().toString();
        expense.expenseDate = expenseTime;
        expense.expenseAmount = Integer.parseInt(edtExpenseAmount.getText().toString());
        expense.expenseDescription = edtExpenseDescription.getText().toString();
        expense.categoryId = categoryId;
        expense.expenseType = swhExpenseType.isChecked() ? Constant.RECURRING_TYPE : Constant.NON_RECURRING_TYPE;
        return expense;
    }

    /**
     * Provides mExpense date in Format Jul 23,2016
     *
     * @return mExpense date as string
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
                Timber.d("time in milliseconds %d", calendar.getTimeInMillis());
                expenseTime = calendar.getTimeInMillis();
                edtExpenseDate.setText(expenseDate.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();

        return expenseDate[0];
    }

    @Override
    public void showCategories(ArrayList<ExpenseCategory> categories) {
        expenseCategoriesAdapter.addCategory(categories);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void dataSaveSuccess() {
        Toast.makeText(getActivity(), "Successfully saved data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dataSaveFailure(String message) {
        Toast.makeText(getActivity(), "Failed to saved data", Toast.LENGTH_SHORT).show();
    }
}
