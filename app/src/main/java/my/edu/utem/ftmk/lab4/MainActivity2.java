package my.edu.utem.ftmk.lab4;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import my.edu.utem.ftmk.lab4.databinding.ActivityregisterBinding;

public class MainActivity2 extends AppCompatActivity {

    ActivityregisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityregisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
