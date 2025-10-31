package com.fit2081.assignment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogInPage extends AppCompatActivity {

    EditText newUsernameInput;
    EditText newPasswordInput;

    public void onNewRegisterButton(View view) {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void onNewLogInButton(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String storedUsername = sharedPreferences.getString("username", "");
        String storedPassword = sharedPreferences.getString("password", "");

        String enteredUsername = newUsernameInput.getText().toString();
        String enteredPassword = newPasswordInput.getText().toString();

        if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        } else {
            Toast.makeText(LogInPage.this, "Authentication failure: Username or Password incorrect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        newUsernameInput = findViewById(R.id.newUsernameInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String lastSavedUsername = sharedPreferences.getString("username", "");
        newUsernameInput.setText(lastSavedUsername);
    }
}