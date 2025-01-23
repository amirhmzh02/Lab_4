package my.edu.utem.ftmk.lab4;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String ExpName;
    public String ExpDate;
    public float ExpValue;
    public int ExpQty;

    // Default constructor required by Room
    public Expense() {
        // No-argument constructor for Room
    }

    // Parameterized constructor
    public Expense(String expName, String expDate, float expValue, int expQty) {
        ExpName = expName;
        ExpDate = expDate;
        ExpValue = expValue;
        ExpQty = expQty;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpName() {
        return ExpName;
    }

    public void setExpName(String expName) {
        ExpName = expName;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public float getExpValue() {
        return ExpValue;
    }

    public void setExpValue(float expValue) {
        ExpValue = expValue;
    }

    public int getExpQty() {
        return ExpQty;
    }

    public void setExpQty(int expQty) {
        ExpQty = expQty;
    }
}
