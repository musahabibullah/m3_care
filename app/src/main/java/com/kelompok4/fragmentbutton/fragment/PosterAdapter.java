package com.kelompok4.fragmentbutton.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelompok4.fragmentbutton.R;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private int[] posterImages;

    public PosterAdapter(int[] posterImages) {
        this.posterImages = posterImages;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.posterImage.setImageResource(posterImages[position]);
    }

    @Override
    public int getItemCount() {
        return posterImages.length;
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImage;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.poster_image);
        }
    }
}
