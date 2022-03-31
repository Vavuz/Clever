package com.example.clever;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExpensesDatabaseManager extends SQLiteOpenHelper {

    // Properties instantiation
    private static ExpensesDatabaseManager expensesDatabaseManager;
    private static final String DATABASE_NAME = "CleverDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Expenses";
    private static final String COUNTER = "Counter";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String PRICE_FIELD = "price";
    private static final String SUBSCRIPTION_TYPE_DESC_FIELD = "subscriptionType";
    private static final String DELETED_FIELD = "deleted";
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    /**
     * Constructor
     * @param context
     */
    public ExpensesDatabaseManager(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    /**
     * Creates an instance of the database
     * @param context
     * @return
     */
    public static ExpensesDatabaseManager instanceOfDatabase(Context context)
    {
        if(expensesDatabaseManager == null)
            expensesDatabaseManager = new ExpensesDatabaseManager(context);

        return expensesDatabaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase expensesDatabase)
    {
        // Database table creation
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(PRICE_FIELD)
                .append(" TEXT, ")
                .append(SUBSCRIPTION_TYPE_DESC_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        expensesDatabase.execSQL(sql.toString());
    }

    /**
     * In case of database upgrade
     * @param expensesDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase expensesDatabase, int oldVersion, int newVersion)
    {
//        switch (oldVersion)
//        {
//            case 1:
//                expensesDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                expensesDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    /**
     * Adds an expense to the local database
     * @param expense
     */
    public void addExpenseToDatabase(Expense expense)
    {
        SQLiteDatabase expensesDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, expense.getId());
        contentValues.put(NAME_FIELD, expense.getName());
        contentValues.put(PRICE_FIELD, expense.getPrice());
        contentValues.put(SUBSCRIPTION_TYPE_DESC_FIELD, expense.getSubscriptionType());
        contentValues.put(DELETED_FIELD, getStringFromDate(expense.getDeleted()));

        expensesDatabase.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Populates the array of expenses
     */
    public void populateExpenseListArray()
    {
        SQLiteDatabase expensesDatabase = this.getReadableDatabase();

        try (Cursor result = expensesDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if (result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String price = result.getString(3);
                    String subscriptionType = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);
                    Expense expense = new Expense(id,name,price,subscriptionType,deleted);
                    Expense.expenseArrayList.add(expense);
                }
            }
        }
    }

    /**
     * Used to update the database when an expense is edited
     * @param expense
     */
    public void updateExpenseInDB(Expense expense)
    {
        SQLiteDatabase expensesDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, expense.getId());
        contentValues.put(NAME_FIELD, expense.getName());
        contentValues.put(PRICE_FIELD, expense.getPrice());
        contentValues.put(SUBSCRIPTION_TYPE_DESC_FIELD, expense.getSubscriptionType());
        contentValues.put(DELETED_FIELD, getStringFromDate(expense.getDeleted()));

        expensesDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(expense.getId())});
    }

    /**
     * Returns string from a date
     * @param date
     * @return
     */
    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    /**
     * Returns date from a string
     * @param string
     * @return
     */
    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}


















