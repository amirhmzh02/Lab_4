package my.edu.utem.ftmk.lab4.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import my.edu.utem.ftmk.lab4.Expense;  // Use the Expense class instead of ExpenseAdapter

public class DatabaseExpense extends SQLiteOpenHelper {

    public static final String dbName = "dbExpense"; // Database name
    public static final int dbVersion = 1; // Database version

    public static final String tblExpense = "tblExpense"; // Table name
    public static final String colId = "id"; // Column for id
    public static final String colExpName = "exp_name"; // Column for expense name
    public static final String colExpDate = "exp_date"; // Column for expense date
    public static final String colExpValue = "exp_value"; // Column for expense value
    public static final String colExpQty = "exp_qty"; // Column for expense quantity

    public static final String createTable = "CREATE TABLE " + tblExpense + " (" +
            colId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            colExpName + " TEXT, " +
            colExpDate + " TEXT, " +
            colExpValue + " TEXT, " +
            colExpQty + " TEXT);";

    public static final String dropTable = "DROP TABLE IF EXISTS " + tblExpense;

    // Constructor
    public DatabaseExpense(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        sqliteDatabase.execSQL(createTable); // Executes the SQL to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, int oldVersion, int newVersion) {
        sqliteDatabase.execSQL(dropTable); // Drops the table if it exists during an upgrade
        onCreate(sqliteDatabase); // Calls onCreate to recreate the table after upgrade
    }

    // CREATE operation (Insert)
    public long fnInsertExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to hold column data
        ContentValues values = new ContentValues();
        values.put(colExpName, expense.getExpName());  // Insert expense name
        values.put(colExpDate, expense.getExpDate());  // Insert expense date
        values.put(colExpValue, expense.getExpValue()); // Insert expense value
        values.put(colExpQty, expense.getExpQty());   // Insert expense quantity

        // Insert the data into the database and return the result
        return db.insert(tblExpense, null, values); // Return the row ID of the inserted record
    }

    // READ operation (Select all expenses)
    @SuppressLint("Range")
    public List<Expense> fnGetAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to get all expenses
        Cursor cursor = db.query(tblExpense,
                new String[]{colId, colExpName, colExpDate, colExpValue, colExpQty},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new Expense object and populate it with data
                Expense expense = new Expense(
                        cursor.getString(cursor.getColumnIndex(colExpName)),
                        cursor.getString(cursor.getColumnIndex(colExpDate)),
                        cursor.getFloat(cursor.getColumnIndex(colExpValue)),
                        cursor.getInt(cursor.getColumnIndex(colExpQty))
                );
                expenses.add(expense);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return expenses;
    }

    // UPDATE operation (Update an existing expense)
    public int fnUpdateExpense(Expense expense, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to hold column data
        ContentValues values = new ContentValues();
        values.put(colExpName, expense.getExpName());  // Update expense name
        values.put(colExpDate, expense.getExpDate());  // Update expense date
        values.put(colExpValue, expense.getExpValue()); // Update expense value
        values.put(colExpQty, expense.getExpQty());   // Update expense quantity

        // Update the record where the id matches
        return db.update(tblExpense, values, colId + " = ?", new String[]{String.valueOf(id)});
    }

    // DELETE operation (Delete an expense by ID)
    public int fnDeleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the record where the id matches
        return db.delete(tblExpense, colId + " = ?", new String[]{String.valueOf(id)});
    }
}
