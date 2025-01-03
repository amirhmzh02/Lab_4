package my.edu.utem.ftmk.lab4;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import my.edu.utem.ftmk.lab4.databinding.ActivityexpenseBinding;
import my.edu.utem.ftmk.lab4.sqlite.DatabaseExpense;

public class ExpenseActivity extends AppCompatActivity {

    ActivityexpenseBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DatabaseExpense databaseExpense;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityexpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database object
        databaseExpense = new DatabaseExpense(this);

        // Set up the quantity spinner (15 items from 1 to 15)
        Integer[] numbers = new Integer[15];
        for (int i = 0; i < 15; i++) {
            numbers[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnQty.setAdapter(adapter);

        // Set listeners for buttons
        binding.btnSave.setOnClickListener(this::fnSaveExp);
        binding.imgExp.setOnClickListener(this::fnTakePic);

        // Date picker for expense date
        binding.edtExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnInvokeDatePicker();
            }
        });

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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });
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
        // Collect the data from the UI
        String expName = binding.edtExpValue.getText().toString();
        String expDate = binding.edtExpDate.getText().toString();
        float expValue = Float.parseFloat(binding.txtTotalPrice.getText().toString());
        int qtyItem = (int) binding.spnQty.getSelectedItem();

        // Create a new Expense object
        binding.txtTotalPrice.setText(""+qtyItem*Float.parseFloat(binding.edtExpValue.getText().toString()));
        try {
            int ResCode = databaseExpense.fnInsertExpense(expenseAdapter);
            if (ResCode > 0)
                Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }
        // Insert the expense into the database
    }

    // Method to show the date picker
    private void fnInvokeDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(ExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                binding.edtExpDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);
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
