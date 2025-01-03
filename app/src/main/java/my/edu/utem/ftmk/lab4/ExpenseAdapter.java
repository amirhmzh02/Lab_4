package my.edu.utem.ftmk.lab4;

public class ExpenseAdapter {
    private String expName;
    private String expDate;
    private String expValue;
    private String expQty;

    // Constructor
    public ExpenseAdapter(String expName, String expDate, String expValue, String expQty) {
        this.expName = expName;
        this.expDate = expDate;
        this.expValue = expValue;
        this.expQty = expQty;
    }

    // Getters and Setters
    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpValue() {
        return expValue;
    }

    public void setExpValue(String expValue) {
        this.expValue = expValue;
    }

    public String getExpQty() {
        return expQty;
    }

    public void setExpQty(String expQty) {
        this.expQty = expQty;
    }
}
