package my.edu.utem.ftmk.lab4;

public class Expense {
    private String expName;
    private String expDate;
    private String expValue;
    private String expQty;

    // Constructor to initialize an Expense object
    public Expense(String expName, String expDate, String expValue, String expQty) {
        this.expName = expName;
        this.expDate = expDate;
        this.expValue = expValue;
        this.expQty = expQty;
    }

    // Getter methods to access the values
    public String getExpName() {
        return expName;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getExpValue() {
        return expValue;
    }

    public String getExpQty() {
        return expQty;
    }

    // Setter methods to modify the values (optional if you need them)
    public void setExpName(String expName) {
        this.expName = expName;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setExpValue(String expValue) {
        this.expValue = expValue;
    }

    public void setExpQty(String expQty) {
        this.expQty = expQty;
    }
}

