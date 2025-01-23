package my.edu.utem.ftmk.lab4;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import my.edu.utem.ftmk.lab4.databinding.ActivityexpenseBinding;
import my.edu.utem.ftmk.lab4.sqlite.AppDatabase;
import my.edu.utem.ftmk.lab4.sqlite.DatabaseExpense;
import my.edu.utem.ftmk.lab4.sqlite.ExpenseDAO;

public class ExpenseActivity extends AppCompatActivity {

    ActivityexpenseBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DatabaseExpense databaseExpense;
    private AppDatabase appDatabase;
    private ExpenseDAO expensesDAO;
    private ExpenseAdapter expenseAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityexpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database objects
        databaseExpense = new DatabaseExpense(this);
        appDatabase = AppDatabase.getDatabase(this);
        expensesDAO = appDatabase.expensesDAO();

        // Set up the quantity spinner (15 items from 1 to 15)
        Integer[] numbers = new Integer[15];
        for (int i = 0; i < 15; i++) {
            numbers[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnQty.setAdapter(adapter);

        // Set up the RecyclerView to display expenses
        recyclerView = binding.recyclerViewExpenses;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set listeners for buttons
        binding.btnSave.setOnClickListener(this::fnSaveExp);
        binding.imgExp.setOnClickListener(this::fnTakePic);

        // Date picker for expense date
        binding.edtExpDate.setOnClickListener(v -> fnInvokeDatePicker());

        // Drawer layout setup
        drawerLayout = binding.myDrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Sync the ActionBarDrawerToggle with the ActionBar
        actionBarDrawerToggle.syncState();

        // Enable the "Up" button in the ActionBar (hamburger icon)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up the navigation view
        navigationView = binding.navigation;
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_login_activity:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_register_activity:
                    intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_expenses_activity:
                    intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        });

        // Load existing expenses into RecyclerView
        loadExpenses();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to open the camera and take a picture
    private void fnTakePic(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    // Method to handle saving the expense
    private void fnSaveExp(View view) {
        // Validate the inputs before processing
        String expName = binding.edtName.getText().toString().trim();
        String expDate = binding.edtExpDate.getText().toString().trim();
        String totalPriceString = binding.txtTotalPrice.getText().toString().trim();

        if (expName.isEmpty() || totalPriceString.isEmpty() || expDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Parse the expense value
            float totalPrice = Float.parseFloat(totalPriceString);

            // Get the selected quantity from the spinner
            int qtyItem = Integer.parseInt(binding.spnQty.getSelectedItem().toString());

            // Calculate the total price
            float calculatedTotal = qtyItem * totalPrice;

            // Create an Expense object
            Expense expense = new Expense(expName, expDate, calculatedTotal, qtyItem);

            // Insert the expense into the local SQLite database
            new Thread(() -> {
                expensesDAO.insertExpense(expense);
                runOnUiThread(() -> Toast.makeText(this, "Expense saved locally", Toast.LENGTH_SHORT).show());
            }).start();

            // Send the expense data to the remote PHP server
            new Thread(() -> sendExpenseToServer(expense)).start();

            // Refresh the RecyclerView after inserting the expense
            loadExpenses();

            Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values for the expense", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void sendExpenseToServer(Expense expense) {
        try {
            // Define the URL of your PHP script
            String urlString = "http://192.168.0.17/Mobile/lab10/insert_expense.php"; // Change this to your actual server URL

            // Create a JSON object with the expense data
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("exp_name", expense.getExpName());
            jsonObject.put("exp_date", expense.getExpDate());
            jsonObject.put("exp_value", expense.getExpValue());
            jsonObject.put("exp_qty", expense.getExpQty());

            // Open a connection to the server
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json");

            // Write the JSON data to the output stream
            OutputStream os = connection.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Handle the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Success - Optionally read the server response if needed
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Process the response if necessary
            } else {
                // Error - Handle the failure case
                runOnUiThread(() -> Toast.makeText(this, "Failed to save to server", Toast.LENGTH_SHORT).show());
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }




    // Method to load all expenses into the RecyclerView
    private void loadExpenses() {
        new Thread(() -> {
            List<Expense> expenseList = expensesDAO.getAllExpense();

            runOnUiThread(() -> {
                expenseAdapter = new ExpenseAdapter(expenseList);
                recyclerView.setAdapter(expenseAdapter);
            });
        }).start();
    }

    // Method to show the date picker
    private void fnInvokeDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(ExpenseActivity.this, (datePicker, year1, monthOfYear, dayOfMonth) ->
                binding.edtExpDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
        pickerDialog.show();
    }

    // Handle the result of the image capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            binding.imgExp.setImageBitmap(bitmap);
        }
    }
}