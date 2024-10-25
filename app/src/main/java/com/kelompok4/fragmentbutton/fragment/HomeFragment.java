package com.kelompok4.fragmentbutton.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.kelompok4.fragmentbutton.R;
import com.kelompok4.fragmentbutton.adapter.LargeImageAdapter;
import com.kelompok4.fragmentbutton.adapter.PosterAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;
    private RecyclerView recyclerViewNew;
    private PosterAdapter posterAdapterNew;
    private ViewPager2 viewPager;
    private LargeImageAdapter largeImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // Find RecyclerViews and set up adapters
        recyclerView = view.findViewById(R.id.recycler_view_posters);
        recyclerViewNew = view.findViewById(R.id.recycler_view_posters_new);
        viewPager = view.findViewById(R.id.view_pager_large);

        // Add sample large image resources
        int[] largeImages = {
                R.drawable.large_image_1,
                R.drawable.large_image_2,
                R.drawable.large_image_3
        };

        largeImageAdapter = new LargeImageAdapter(largeImages);
        viewPager.setAdapter(largeImageAdapter);

        // Add sample poster image resources for the first RecyclerView
        int[] posterImages = {
                R.drawable.poster_1,
                R.drawable.poster_6,
                R.drawable.poster_3,
                R.drawable.poster_4,
                R.drawable.poster_5
        };

        posterAdapter = new PosterAdapter(posterImages);
        recyclerView.setAdapter(posterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Add sample poster image resources for the new RecyclerView
        int[] newPosterImages = {
                R.drawable.poster_1,
                R.drawable.poster_2,
                R.drawable.poster_3,
                R.drawable.poster_4,
                R.drawable.poster_5
        };

        posterAdapterNew = new PosterAdapter(newPosterImages);
        recyclerViewNew.setAdapter(posterAdapterNew);
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }
}
