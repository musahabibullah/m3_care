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

    private TextView tvNamaLengkap, tvNIS, tvEmail, tvTelepon, tvKelas, tvTanggalLahir;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi TextView
        tvNamaLengkap = view.findViewById(R.id.tvNamaLengkap);
        tvNIS = view.findViewById(R.id.tvNIS);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTelepon = view.findViewById(R.id.tvTelepon);
        tvKelas = view.findViewById(R.id.tvKelas);
        tvTanggalLahir = view.findViewById(R.id.tvTanggalLahir);

        // Ambil user_id dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        if (userId != null) {
            // Load data profil
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

                                // Ambil data dari response
                                String namaLengkap = data.getString("nama_lengkap");
                                String nis = data.getString("nis");
                                String email = data.getString("email");
                                String telepon = data.getString("telepon");
                                String kelas = data.getString("kelas");
                                String tanggalLahir = data.getString("tanggal_lahir");

                                // Tampilkan data ke TextView
                                tvNamaLengkap.setText(namaLengkap);
                                tvNIS.setText(nis);
                                tvEmail.setText(email);
                                tvTelepon.setText(telepon);
                                tvKelas.setText(kelas);
                                tvTanggalLahir.setText(tanggalLahir);
                            } else {
                                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
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
