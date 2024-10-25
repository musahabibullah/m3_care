package com.kelompok4.fragmentbutton.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelompok4.fragmentbutton.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private String[] movieTitles; // Daftar judul film
    private int[] movieImages; // Daftar gambar film

    public SearchAdapter(String[] movieTitles, int[] movieImages) {
        this.movieTitles = movieTitles;
        this.movieImages = movieImages;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.title.setText(movieTitles[position]);
        holder.image.setImageResource(movieImages[position]);
    }

    @Override
    public int getItemCount() {
        return movieTitles.length;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            image = itemView.findViewById(R.id.movie_image);
        }
    }
}
