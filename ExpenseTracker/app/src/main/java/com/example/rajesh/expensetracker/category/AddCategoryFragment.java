package com.example.rajesh.expensetracker.category;


import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

import butterknife.Bind;
import butterknife.OnClick;
import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorSelectListener;
import timber.log.Timber;


public class AddCategoryFragment extends BaseFragment {

    public static final String CATEGORY_FRAGMENT = "category_fragment";

    @Bind(R.id.edt_category_name)
    EditText edtCategoryName;

    @Bind(R.id.iv_color_picker)
    ImageView ivColorPicker;

    ExpenseCategory expenseCategory;

    String hexColor = "#ff08f0";

    public static AddCategoryFragment getInstance(ExpenseCategory expenseCategory) {
        AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_FRAGMENT, expenseCategory);
        addCategoryFragment.setArguments(bundle);
        return addCategoryFragment;
    }


    public AddCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (expenseCategory != null) {
            edtCategoryName.setText(expenseCategory.categoryTitle);
            hexColor = expenseCategory.categoryColor;
        }
        ivColorPicker.setBackgroundColor(Color.parseColor(hexColor));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getParcelable(CATEGORY_FRAGMENT) != null) {
            expenseCategory = getArguments().getParcelable(CATEGORY_FRAGMENT);
            Timber.d("onLongPressed");
        }
    }


    @OnClick({R.id.iv_color_picker, R.id.btn_add_category})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_color_picker) {
            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .onColorSelected(new ColorSelectListener() {
                        @Override
                        public void onColorSelected(@ColorInt int i) {
                            hexColor = String.format("#%06X", (0xFFFFFF & i));
                            ivColorPicker.setBackgroundColor(Color.parseColor(hexColor));
                        }
                    })
                    .create()
                    .show(getActivity().getSupportFragmentManager(), "ChromaDialog");
        }

        if (expenseCategory == null) {
            if (view.getId() == R.id.btn_add_category) {
                ContentValues contentValues = getContentValues();
                Uri uri = getActivity().getContentResolver().insert(ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_URI, contentValues);
                if (ContentUris.parseId(uri) > 0) {
                    Toast.makeText(getActivity(), "category inserted", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            ContentValues contentValues = getContentValues();
            int updatedId = getActivity().getContentResolver().update(ExpenseTrackerContract.ExpenseCategoriesEntry.buildUriWithCategoryId(expenseCategory.id), contentValues, null, null);

            if (updatedId > 0) {
                Toast.makeText(getActivity(), "updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME, edtCategoryName.getText().toString());
        contentValues.put(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_COLOR, hexColor);
        return contentValues;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_category;
    }

}
