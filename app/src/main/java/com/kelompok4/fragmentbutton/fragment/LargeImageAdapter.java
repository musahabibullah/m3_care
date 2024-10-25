package com.kelompok4.fragmentbutton.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelompok4.fragmentbutton.R;

public class LargeImageAdapter extends RecyclerView.Adapter<LargeImageAdapter.ImageViewHolder> {

    private int[] largeImages;

    public LargeImageAdapter(int[] largeImages) {
        this.largeImages = largeImages;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.large_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(largeImages[position]);
    }

    @Override
    public int getItemCount() {
        return largeImages.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.large_image);
        }
    }
}
