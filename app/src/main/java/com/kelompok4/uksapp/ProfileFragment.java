package com.kelompok4.uksapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;

public class ProfileFragment extends Fragment {

    private static final String SHARED_PREF_NAME = "user_pref";
    private static final String KEY_USER_ID = "user_id";

    private TextView tvUsername, tvEmail;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        if (userId != null) {

            loadUserProfile();
        } else {
            Toast.makeText(getContext(), "User ID not found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadUserProfile() {
        String url = Db_Contract.urlProfile + "?user_id=" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                JSONObject data = response.getJSONObject("data");
                                String username = data.getString("username");
                                String email = data.getString("email");

                                tvUsername.setText(username);
                                tvEmail.setText(email);
                            } else {
                                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Failed to load profile: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }

                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}