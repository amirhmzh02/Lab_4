package my.edu.utem.ftmk.lab4.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import my.edu.utem.ftmk.lab4.Expense;
import my.edu.utem.ftmk.lab4.sqlite.ExpenseDAO;

@Database(entities = {Expense.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    // Abstract method to access DAO
    public abstract ExpenseDAO expensesDAO();

    // Singleton to get the database instance
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "db_expense"
                            )
                            .setJournalMode(JournalMode.TRUNCATE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

