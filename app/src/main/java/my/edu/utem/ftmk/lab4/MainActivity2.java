package my.edu.utem.ftmk.lab4;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import my.edu.utem.ftmk.lab4.databinding.ActivityregisterBinding;

public class MainActivity2 extends AppCompatActivity {

    ActivityregisterBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityregisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        binding.editTextTextPersonName2.setText(intent.getStringExtra("username"));
        binding.txtpassword.setText(intent.getStringExtra("password")); // Populate password EditText

        fnLoadInput();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        fnSaveState();
        super.onPause();
    }

    public void fnSaveState(){

    }

    public void fnLoadInput(){

    }

    public void fnCalculateAge(View view) {
        String dobYearStr = binding.edtExpDate.getText().toString();

        if (!dobYearStr.isEmpty()) {
            int dobYear = Integer.parseInt(dobYearStr);
            int currentYear = 2024;
            int age = currentYear - dobYear;

            binding.edtExpDate.setText("You are is " + age + " years old");
        } else {
            binding.edtExpDate.setError("Please enter a valid year");
        }
    }


}
