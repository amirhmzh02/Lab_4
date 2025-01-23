package my.edu.utem.ftmk.lab4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    // Constructor
    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    // ViewHolder class
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView txtExpName, txtExpDate, txtExpValue, txtExpQty;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            txtExpName = itemView.findViewById(R.id.txtExpName);
            txtExpDate = itemView.findViewById(R.id.txtExpDate);
            txtExpValue = itemView.findViewById(R.id.txtTotalPrice);
            txtExpQty = itemView.findViewById(R.id.txtquantity);
        }
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        // Get the expense object at the current position
        Expense expense = expenseList.get(position);

        // Bind the data to the views
        holder.txtExpName.setText(expense.getExpName());
        holder.txtExpDate.setText(expense.getExpDate());
        holder.txtExpValue.setText(String.valueOf(expense.getExpValue()));
        holder.txtExpQty.setText(String.valueOf(expense.getExpQty()));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
