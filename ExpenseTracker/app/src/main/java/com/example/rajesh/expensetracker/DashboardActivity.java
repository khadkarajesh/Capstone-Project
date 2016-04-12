package com.example.rajesh.expensetracker;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rajesh.expensetracker.account.list.AccountFragment;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;
import com.example.rajesh.expensetracker.category.AddCategoryFragment;
import com.example.rajesh.expensetracker.category.CategoryFragment;
import com.example.rajesh.expensetracker.category.CategoryLongPressListener;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.DashBoardFragment;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.expense.ExpenseFragment;
import com.example.rajesh.expensetracker.expense.ExpenseLongPressListener;
import com.example.rajesh.expensetracker.expense.recurring.RecurringFragment;
import com.example.rajesh.expensetracker.report.ReportFragment;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, CategoryLongPressListener, ExpenseLongPressListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        setSupportActionBar(toolbar);


        setNavigationDrawer();

        addFragment(new DashBoardFragment(), Constant.FragmentTag.DASHBOARD_FRAGMENT_TAG);
    }

    private void setNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_navigation;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        String fragmentTag = null;

        switch (id) {
            case R.id.nav_account:
                fragment = new AccountFragment();
                fragmentTag = "NewFragment";
                break;
            case R.id.nav_categories:
                fragment = new CategoryFragment();
                fragmentTag = Constant.FragmentTag.CATEGORY_FRAGMENT;
                break;
            case R.id.nav_history_report:
                fragment = new ReportFragment();
                fragmentTag = Constant.FragmentTag.REPORT_FRAGMENT;
                break;
            case R.id.nav_recurring_expense:
                fragment = new RecurringFragment();
                fragmentTag = Constant.FragmentTag.EXPENSE_FRAGMENT;
                break;
            case R.id.nav_settings:

                break;
            case R.id.nav_dashboard:
                fragment = new DashBoardFragment();
                fragmentTag = Constant.FragmentTag.DASHBOARD_FRAGMENT_TAG;
                break;
        }
        addFragment(fragment, fragmentTag);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCategoryLongPress(ExpenseCategory expenseCategory) {
        AddCategoryFragment addCategoryFragment = AddCategoryFragment.getInstance(expenseCategory);
        addFragment(addCategoryFragment, Constant.FragmentTag.CATEGORY_FRAGMENT);
    }


    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_dashboard_wrapper, fragment, tag).commit();
    }

    @Override
    public void onExpenseLongPress(Expense expense, ExpenseCategory expenseCategory) {
        ExpenseFragment expenseFragment = ExpenseFragment.getInstance(null, null);
        addFragment(expenseFragment, Constant.FragmentTag.EXPENSE_FRAGMENT);
    }
}
