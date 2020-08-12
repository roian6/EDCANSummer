package com.david0926.edcansummer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.david0926.edcansummer.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoreFragment extends Fragment {

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private Context mContext;
    private FragmentMoreBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        binding.setUser(UserCache.getUser(mContext));

        binding.btnMoreLogout.setOnClickListener(view -> logout());

        binding.btnMorePw.setOnClickListener(view -> {
            firebaseAuth.sendPasswordResetEmail(UserCache.getUser(mContext).getEmail());
            Toast.makeText(mContext, "재설정 이메일이 전송되었습니다!", Toast.LENGTH_SHORT).show();
            logout();
        });

        binding.btnMoreDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("회원 탈퇴");
            builder.setMessage("정말로 탈퇴하시겠습니까?");
            builder.setPositiveButton("탈퇴하기", (dialogInterface, i) -> {
                firebaseAuth.getCurrentUser().delete().addOnSuccessListener(runnable -> {
                    firebaseFirestore
                            .collection("users")
                            .document(UserCache.getUser(mContext).getEmail())
                            .delete()
                            .addOnSuccessListener(runnable1 -> {
                                Toast.makeText(mContext, "정상적으로 탈퇴되었습니다!", Toast.LENGTH_SHORT).show();
                                logout();
                            })
                            .addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
                }).addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("취소", (dialogInterface, i) -> {
            });
            builder.show();
        });

        return binding.getRoot();
    }

    private void logout() {
        UserCache.clear(mContext);
        firebaseAuth.signOut();
        startActivity(new Intent(mContext, LoginActivity.class));
        getActivity().finish();
    }
}
