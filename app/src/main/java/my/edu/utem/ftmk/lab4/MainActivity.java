package my.edu.utem.ftmk.lab4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import my.edu.utem.ftmk.lab4.databinding.ActivityloginBinding;

public class MainActivity extends AppCompatActivity {

    ActivityloginBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the Login button click listener
        binding.btnLogin.setOnClickListener(this::fnLogin);

        // Initialize DrawerLayout and ActionBarDrawerToggle
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

    public void fnLogin(View view) {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
