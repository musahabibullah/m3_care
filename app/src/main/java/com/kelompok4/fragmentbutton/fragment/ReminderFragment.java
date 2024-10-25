package com.kelompok4.fragmentbutton.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kelompok4.fragmentbutton.R;
import com.kelompok4.fragmentbutton.adapter.HistoryAdapter;

public class ReminderFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    private String[] movieTitles = {
            "Kuch Kuch Hota Hai",
            "Dilwale Dulhania Le Jayenge",
            "Kabhi Khushi Kabhie Gham",
            "3 Idiots",
            "Lagaan"
    };

    private int[] movieImages = {
            R.drawable.poster_4,
            R.drawable.poster_3,
            R.drawable.poster_6,
            R.drawable.poster_2,
            R.drawable.large_image
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_history);

        // Set up RecyclerView
        historyAdapter = new HistoryAdapter(movieTitles, movieImages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(historyAdapter);

        return view;
    }
}
