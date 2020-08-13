package com.david0926.edcansummer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.david0926.edcansummer.databinding.ActivityNewMemoBinding;

public class NewMemoActivity extends AppCompatActivity {

    private ActivityNewMemoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_memo);
        binding.setMemo("");

        binding.toolbarNewMemo.setNavigationOnClickListener(view -> finish());

        binding.btnNewMemoUpload.setOnClickListener(view -> {
            //
        });

    }
}