package my.edu.utem.ftmk.lab4.sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import my.edu.utem.ftmk.lab4.Expense;

@Dao
public interface ExpenseDAO {

    @Insert
    void insertExpense(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM tbl_expense")
    List<Expense> getAllExpense();

    @Query("SELECT * FROM tbl_expense WHERE id = :id")
    Expense getExpenseById(int id);

}
