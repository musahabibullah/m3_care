package com.kelompok4.uksapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SuratIzinFragment extends Fragment {

    private EditText etNis, etNama, etKelas, etSuhu, etKeluhan, etDiagnosis, etPertolonganPertama;
    private RadioGroup rgStatus;
    private Button btnRequest;
    private String status;
    private static final String TAG = "SuratIzinFragment";
    private static final String SHARED_PREF_NAME = "user_pref";
    private static final String KEY_SESSION_ID = "session_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surat_izin, container, false);

        // Initialize the EditText and RadioGroup views
        etNis = view.findViewById(R.id.etNis);
        etNama = view.findViewById(R.id.etNama);
        etKelas = view.findViewById(R.id.etKelas);
        etSuhu = view.findViewById(R.id.etSuhu);
        etKeluhan = view.findViewById(R.id.etKeluhan);
        etDiagnosis = view.findViewById(R.id.etDiagnosis);
        etPertolonganPertama = view.findViewById(R.id.etPertolonganPertama);
        rgStatus = view.findViewById(R.id.rgStatus);
        btnRequest = view.findViewById(R.id.btnRequest);

        // Set listener untuk RadioGroup
        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = view.findViewById(checkedId);
            status = radioButton.getText().toString();
        });

        // Set listener untuk button
        btnRequest.setOnClickListener(v -> {
            if (validateInputs()) {
                sendRequest();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (etNis.getText().toString().isEmpty() ||
                etNama.getText().toString().isEmpty() ||
                etKelas.getText().toString().isEmpty() ||
                etSuhu.getText().toString().isEmpty() ||
                etKeluhan.getText().toString().isEmpty() ||
                etDiagnosis.getText().toString().isEmpty() ||
                etPertolonganPertama.getText().toString().isEmpty() ||
                status == null || status.isEmpty()) {
            Toast.makeText(getContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendRequest() {
        String url = Db_Contract.urlRequestSuratIzin;

        // Ambil session_id dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String sessionId = sharedPreferences.getString(KEY_SESSION_ID, "");

        if (sessionId.isEmpty()) {
            Toast.makeText(getContext(), "Session ID tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.e(TAG, "Raw server response: " + response);
                    if (response.contains("success")) {
                        Toast.makeText(getActivity(), "Request sent successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Unexpected server response: " + response);
                        Toast.makeText(getActivity(), "Failed to save data. Check server response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    if (error.networkResponse != null) {
                        Log.e(TAG, "Error Status Code: " + error.networkResponse.statusCode);
                        Log.e(TAG, "Error Response Data: " + new String(error.networkResponse.data));
                    }
                    Log.e(TAG, "Volley error: ", error);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nis", etNis.getText().toString());
                params.put("nama", etNama.getText().toString());
                params.put("kelas", etKelas.getText().toString());
                params.put("suhu", etSuhu.getText().toString());
                params.put("status", status);
                params.put("keluhan", etKeluhan.getText().toString());
                params.put("diagnosis", etDiagnosis.getText().toString());
                params.put("pertolongan_pertama", etPertolonganPertama.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", "PHPSESSID=" + sessionId); // Menambahkan session_id di header Cookie
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}
