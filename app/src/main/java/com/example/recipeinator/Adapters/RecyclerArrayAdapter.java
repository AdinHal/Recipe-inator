package com.example.recipeinator.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.R;

import java.util.List;

public class RecyclerArrayAdapter<T> extends RecyclerView.Adapter<RecyclerArrayAdapter.ViewHolder> {
    @LayoutRes private final int layoutId;
    private final List<T> list;

    public RecyclerArrayAdapter(@LayoutRes int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerArrayAdapter.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.list_entry);
        textView.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
