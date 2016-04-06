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

import com.example.rajesh.expensetracker.account.AccountFragment;
import com.example.rajesh.expensetracker.base.activity.BaseActivity;
import com.example.rajesh.expensetracker.category.AddCategoryFragment;
import com.example.rajesh.expensetracker.category.CategoryFragment;
import com.example.rajesh.expensetracker.category.CategoryLongPressListener;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.expense.ExpenseFragment;
import com.example.rajesh.expensetracker.report.ReportFragment;

import timber.log.Timber;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, CategoryLongPressListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addFragment(AddCategoryFragment.getInstance(null), "add_category_fragment");

        //getSupportFragmentManager().beginTransaction().replace(R.id.ll_container, new AddCategoryFragment(), "fragment").commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_account) {
            fragment = new AccountFragment();
        } else if (id == R.id.nav_categories) {
            fragment = new CategoryFragment();
        } else if (id == R.id.nav_history_report) {
            fragment = new ReportFragment();
        } else if (id == R.id.nav_recurring_expense) {
            fragment = new ExpenseFragment();
        } else if (id == R.id.nav_settings) {

        }
        addFragment(fragment, "fragment");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCategoryLongPress(ExpenseCategory expenseCategory) {
        Timber.d("expenseTracker %s",expenseCategory.categoryTitle);
        AddCategoryFragment addCategoryFragment = AddCategoryFragment.getInstance(expenseCategory);
        addFragment(addCategoryFragment, "category_fragment");
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_container, fragment, tag).commit();
    }
}
