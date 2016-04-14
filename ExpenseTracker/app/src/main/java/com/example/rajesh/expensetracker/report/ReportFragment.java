package com.example.rajesh.expensetracker.report;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.base.frament.BaseFragment;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import timber.log.Timber;


public class ReportFragment extends BaseFragment implements OnChartValueSelectedListener, ReportView {

    public enum ReportType {
        REPORT_BY_WEEK, REPORT_BY_MONTH, REPORT_BY_YEAR
    }

    @Bind(R.id.chart)
    PieChart pieChart;

    @Bind(R.id.spinner)
    Spinner spinner;

    private Typeface tf;

    ReportPresenterContract reportPresenterContract;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateSpinner();
        setPieChart();

        reportPresenterContract = new ReportPresenter(this);
        reportPresenterContract.getExpenseByCategory(ReportType.REPORT_BY_WEEK);


    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        pieChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);

        //setData(3, 100);
        setData();

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_report;
    }

    private ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Health");
        categories.add("Fitness");
        return categories;
    }

    private ArrayList<Entry> getExpenses() {
        ArrayList<Entry> expenses = new ArrayList<>();
        expenses.add(new Entry(10, 0));
        expenses.add(new Entry(40, 1));
        expenses.add(new Entry(50, 2));
        return expenses;
    }

    private void setData() {
        PieDataSet dataSet = new PieDataSet(getExpenses(), "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(getColors());

        PieData data = new PieData(getCategories(), dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.DEFAULT);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        return colors;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void showReport(HashMap<ExpenseCategory, Integer> hashMap) {
        Toast.makeText(getActivity(), "hash map size" + hashMap.size(), Toast.LENGTH_SHORT).show();

        ArrayList<ExpenseCategory> expenseCategoryList = new ArrayList<>();
        for (ExpenseCategory expenseCategory : hashMap.keySet()) {
            expenseCategoryList.add(expenseCategory);
        }
        for (int i = 0; i < expenseCategoryList.size(); i++) {
            Timber.d("expense category %s :: expense amount %d", expenseCategoryList.get(i).categoryTitle, hashMap.get(expenseCategoryList.get(i)));
        }
    }
}
