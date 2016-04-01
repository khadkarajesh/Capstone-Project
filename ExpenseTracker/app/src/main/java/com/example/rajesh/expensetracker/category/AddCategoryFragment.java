package com.example.rajesh.expensetracker.category;


import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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


public class AddCategoryFragment extends BaseFragment {

    @Bind(R.id.edt_category_name)
    EditText edtCategoryName;

    @Bind(R.id.iv_color_picker)
    ImageView ivColorPicker;


    String color = "#ff0000";


    public AddCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivColorPicker.setBackgroundColor(Color.parseColor(color));
    }

    @OnClick({R.id.iv_color_picker, R.id.btn_add_category})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_color_picker) {
            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .onColorSelected(new ColorSelectListener() {
                        @Override
                        public void onColorSelected(@ColorInt int i) {
                            color = Integer.toHexString(i);
                            //Timber.d("color code %d", color);
                        }
                    })
                    .create()
                    .show(getActivity().getSupportFragmentManager(), "ChromaDialog");
        }
        if (view.getId() == R.id.btn_add_category) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME, edtCategoryName.getText().toString());
            contentValues.put(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_COLOR, color);
            Uri uri = getActivity().getContentResolver().insert(ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_URI, contentValues);
            if (ContentUris.parseId(uri) > 0) {
                Toast.makeText(getActivity(), "category inserted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_category;
    }

}
