package my.edu.utem.ftmk.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import my.edu.utem.ftmk.lab4.databinding.ActivityloginBinding;

public class MainActivity extends AppCompatActivity {

    ActivityloginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(this::fnLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void fnLogin(View view) {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString(); // Get the password

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password); // Pass the password to ActivityRegister
        startActivity(intent);
    }

}