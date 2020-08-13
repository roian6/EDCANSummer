package com.david0926.edcansummer;

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
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.david0926.edcansummer.databinding.FragmentMemoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemoFragment extends Fragment {

    public static MemoFragment newInstance() {
        return new MemoFragment();
    }

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ObservableArrayList<MemoModel> items = new ObservableArrayList<>();

    private Context mContext;
    private FragmentMemoBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo, container, false);

        binding.recyclerMemo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        MemoAdapter adapter = new MemoAdapter();
        binding.recyclerMemo.setAdapter(adapter);

        binding.setItems(items);

        binding.fabMemo.setOnClickListener(view -> startActivity(new Intent(mContext, NewMemoActivity.class)));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMemos();
    }

    private void getMemos() {
        items.clear();
        firebaseFirestore
                .collection("memo")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot d : queryDocumentSnapshots){
                        items.add(d.toObject(MemoModel.class));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }
}
