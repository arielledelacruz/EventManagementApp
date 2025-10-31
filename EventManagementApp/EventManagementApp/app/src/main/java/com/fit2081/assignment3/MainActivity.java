package com.fit2081.assignment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;
    EditText confirmPasswordInput;

    public void onRegisterButton(View view) {


        String usernameInputString = usernameInput.getText().toString();
        String passwordInputString = passwordInput.getText().toString();
        String confirmPasswordInputString = confirmPasswordInput.getText().toString();


        if (passwordInputString.equals(confirmPasswordInputString)) {
            Intent intent = new Intent(this, LogInPage.class);

            intent.putExtra("name", usernameInputString);
            intent.putExtra("password", passwordInputString);
            intent.putExtra("confirm", confirmPasswordInputString);

            saveDataToSharedPreferences(usernameInputString, passwordInputString, confirmPasswordInputString);

            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLogInButton(View view) {

        Intent intent = new Intent(this, LogInPage.class);

        startActivity(intent);
    }

    private void saveDataToSharedPreferences(String usernameInput,String passwordInput,String confirmPasswordInput){
        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", usernameInput);
        editor.putString("password", passwordInput);
        editor.putString("confirm", confirmPasswordInput);
        editor.apply();

        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
    }
}