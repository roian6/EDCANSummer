package com.david0926.edcansummer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.david0926.edcansummer.databinding.ActivityNewMemoBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewMemoActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private ActivityNewMemoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_memo);
        binding.setMemo(getIntent().getStringExtra("memo_text"));
        binding.setIsEdit(getIntent().getBooleanExtra("is_edit", false));

        binding.toolbarNewMemo.setNavigationOnClickListener(view -> finish());

        binding.btnNewMemoUpload.setOnClickListener(view -> {
            if (binding.getMemo().isEmpty()) {
                Toast.makeText(this, "빈칸을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            MemoModel model = new MemoModel();
            model.setEmail(UserCache.getUser(this).getEmail());
            model.setText(binding.getMemo());
            model.setTime(getTime());

            if(!getIntent().getBooleanExtra("is_edit", false)){
                //게시물 업로드
                firebaseFirestore
                        .collection("memo")
                        .document()
                        .set(model)
                        .addOnSuccessListener(runnable -> {
                            Toast.makeText(this, "정상적으로 등록되었습니다!", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            }
            else{
                //게시물 수정
                Toast.makeText(this, "수정은 안돼요..ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getTime() {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm aa", Locale.ENGLISH).format(new Date());
    }
}