package com.david0926.edcansummer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david0926.edcansummer.databinding.RowMemoBinding;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoHolder> {

    private List<MemoModel> list = new ArrayList<>();

    public void setItem(List<MemoModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemoHolder(RowMemoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemoHolder holder, int position) {
        MemoModel model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MemoHolder extends RecyclerView.ViewHolder {

        private RowMemoBinding binding;

        public MemoHolder(RowMemoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MemoModel model) {
            binding.setMemo(model);
        }

    }
}
