package com.kelompok4.fragmentbutton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView, emailTextView, phoneTextView;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize TextViews
        usernameTextView = view.findViewById(R.id.tvUsername);
        emailTextView = view.findViewById(R.id.tvEmail);
        phoneTextView = view.findViewById(R.id.tvPhone);

        // Load profile data
        loadProfileData();

        return view;
    }

    private void loadProfileData() {
        String url = "http://192.168.1.5/login2/mobileconnect/api.php"; // Ganti "apalah" dengan username yang sesuai

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject userData = response.getJSONObject("data");
                                String username = userData.getString("username");
                                String email = userData.getString("email");
                                String phone = userData.getString("nomor_telepon");

                                usernameTextView.setText(username);
                                emailTextView.setText(email);
                                phoneTextView.setText(phone);
                            } else {
                                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Failed to load profile data", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}

