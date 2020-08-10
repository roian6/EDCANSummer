package com.david0926.edcansummer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.david0926.edcansummer.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setEmail("");
        binding.setPw("");

        binding.btnLoginSignin.setOnClickListener(view -> {
            login(binding.getEmail(), binding.getPw());
        });

        binding.btnLoginSignup.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login(String email, String pw){
        Toast.makeText(this, email+" "+pw, Toast.LENGTH_SHORT).show();
    }
}