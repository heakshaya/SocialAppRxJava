package com.example.socialappjava.presentation.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.socialappjava.data.model.post.Data;
import com.example.socialappjava.databinding.PostItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private final ArrayList<Data> posts= new ArrayList<>();

    public void setList(List<Data> items) {
        posts.addAll(items);
        notifyDataSetChanged();
    }
public void clearList(){
        posts.clear();
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemBinding binding = PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data post = posts.get(position);
        holder.binding.tags.setText("Tags:");
        for (String tag : post.getTags()) {
            holder.binding.tags.append("\n" + tag);
        }
        Glide.with(holder.binding.image.getContext())
                .load(post.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.image);
        holder.binding.description.setText(post.getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private PostItemBinding binding;

        public MyViewHolder(PostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
