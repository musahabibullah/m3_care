package com.kelompok4.fragmentbutton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RequestSuratDokter extends AppCompatActivity {

    private EditText etNama, etNis, etKeluhan, etDiagnosis, etPertolonganPertama;
    private Button btnTanggal, btnSubmit;
    private Calendar calendar;
    private String selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_surat_dokter);

        // Inisialisasi komponen UI
        etNama = findViewById(R.id.etNama);
        etNis = findViewById(R.id.etNis);
        etKeluhan = findViewById(R.id.etKeluhan);
        etDiagnosis = findViewById(R.id.etDiagnosis);
        etPertolonganPertama = findViewById(R.id.etPertolonganPertama);
        btnTanggal = findViewById(R.id.btnTanggal);
        btnSubmit = findViewById(R.id.btnSubmit);

        calendar = Calendar.getInstance();

        // Set up Button to show DatePicker and TimePicker for selecting date and time
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validasi apakah semua field telah diisi
                if (etNama.getText().toString().isEmpty() ||
                        etNis.getText().toString().isEmpty() ||
                        etKeluhan.getText().toString().isEmpty() ||
                        etDiagnosis.getText().toString().isEmpty() ||
                        etPertolonganPertama.getText().toString().isEmpty() ||
                        selectedDateTime == null) {

                    // Tampilkan pesan Toast jika ada field yang kosong
                    Toast.makeText(RequestSuratDokter.this, "Tolong masukkan semua data", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika semua data sudah diisi, kirim ke server
                    new SubmitData().execute(
                            etNama.getText().toString(),
                            etNis.getText().toString(),
                            etKeluhan.getText().toString(),
                            etDiagnosis.getText().toString(),
                            etPertolonganPertama.getText().toString(),
                            selectedDateTime
                    );
                }
            }
        });
    }

    private void showDateTimePicker() {
        // Show DatePickerDialog first
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RequestSuratDokter.this,
                (view, year, month, dayOfMonth) -> {
                    // Set the chosen date in Calendar
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Show TimePickerDialog after date is selected
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            RequestSuratDokter.this,
                            (timeView, hourOfDay, minute) -> {
                                // Set the chosen time in Calendar
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                // Format the date and time as "yyyy-MM-dd HH:mm:ss"
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                selectedDateTime = sdf.format(calendar.getTime());

                                // Set the button text to the selected date and time
                                btnTanggal.setText(selectedDateTime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private class SubmitData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.1.5/login2/mobileconnect/request_surat_dokter.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Menyiapkan data untuk dikirim
                String data = "nama=" + params[0] +
                        "&nis=" + params[1] +
                        "&keluhan=" + params[2] +
                        "&diagnosis=" + params[3] +
                        "&pertolongan_pertama=" + params[4] +
                        "&tanggal=" + params[5];

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();

                // Baca respon dari server
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                if (status.equals("success")) {
                    Toast.makeText(RequestSuratDokter.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RequestSuratDokter.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RequestSuratDokter.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
