package com.example.rajesh.expensetracker.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ExpenseTrackerContract {
    public static final String CONTENT_AUTHORITY = "com.example.rajesh.expensetracker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String ACCOUNT_PATH = "account";
    public static final String EXPENSE_PATH = "expense";
    public static final String EXPENSE_CATEGORIES_PATH = "categories";

    public static class AccountEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(ACCOUNT_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ACCOUNT_PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ACCOUNT_PATH;

        public static final String TABLE_NAME = "account";

        public static final String COLUMNS_ACCOUNT_TITLE = "account_title";
        public static final String COLUMNS_ACCOUNT_CREATED_DATE = "created_date";
        public static final String COLUMNS_ACCOUNT_AMOUNT = "account_amount";
        public static final String COLUMNS_ACCOUNT_TYPE = "account_type";

        public static int getInsertedAccountId(Uri uri) {
            return Integer.parseInt(uri.getLastPathSegment());
        }

        public static Uri buildAccountUri(long accountId) {
            return ContentUris.withAppendedId(CONTENT_URI, accountId);
        }

        public static int getAccountId(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri buildAccountUriByMonth(String startDate, String endDate) {
            return CONTENT_URI.buildUpon().appendPath(startDate).appendPath(endDate).build();
        }

        public static String getLastDateOfMonth(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getStartDateOfMonth(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static class ExpenseEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(EXPENSE_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EXPENSE_PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EXPENSE_PATH;

        public static final String EXPENSE_TYPE="expenseType";

        public static final String TABLE_NAME = "expense";

        public static final String COLUMNS_EXPENSE_DATE = "expense_date";
        public static final String COLUMNS_EXPENSE_TITLE = "expense_title";
        public static final String COLUMNS_EXPENSE_DESCRIPTION = "expense_description";
        public static final String COLUMNS_EXPENSE_AMOUNT = "expense_amount";
        public static final String COLUMNS_EXPENSE_TYPE = "expense_type";
        public static final String COLUMNS_EXPENSE_CATEGORIES_ID = "expense_categories_id";


        public static Uri buildExpenseUri(long expenseId) {
            return ContentUris.withAppendedId(CONTENT_URI, expenseId);
        }

        public static Uri buildExpenseCategoryUri(String startDate, String endDate) {
            return CONTENT_URI.buildUpon().appendPath(startDate).appendPath(endDate).build();
        }

        public static Uri buildExpenseCategoryUriExpenseType(String startDate, String endDate, String expenseType) {
            return CONTENT_URI.buildUpon().appendPath(EXPENSE_TYPE).appendPath(startDate).appendPath(endDate).appendPath(expenseType).build();
        }

        public static String getLastDateOfMonth(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getStartDateOfMonth(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getExpenseType(Uri uri) {
            return uri.getPathSegments().get(4);
        }

        public static int getExpenseId(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri buildUriWithExpenseId(int id) {
            return CONTENT_URI.buildUpon().appendPath("" + id).build();
        }

        public static Uri buildExpenseByCategoryIdUri(String categoryId, String startTimeStamp, String endTimeStamp) {
            return CONTENT_URI.buildUpon().appendPath(categoryId).appendPath(startTimeStamp).appendPath(endTimeStamp).build();
        }

        public static String getStartTimeStamp(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getEndTimeStamp(Uri uri) {
            return uri.getPathSegments().get(3);
        }

        public static String getCategoryId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class ExpenseCategoriesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(EXPENSE_CATEGORIES_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EXPENSE_CATEGORIES_PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EXPENSE_CATEGORIES_PATH;

        public static final String TABLE_NAME = "categories";

        public static final String COLUMNS_CATEGORIES_NAME = "categories_name";
        public static final String COLUMNS_CATEGORIES_COLOR = "categories_color";

        public static Uri buildAccountUri(long categoryId) {
            return ContentUris.withAppendedId(CONTENT_URI, categoryId);
        }

        public static Uri buildUriWithCategoryId(int id) {
            return CONTENT_URI.buildUpon().appendPath("" + id).build();
        }

        public static int getCategoryId(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }
}
