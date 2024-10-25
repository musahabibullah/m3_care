package com.kelompok4.fragmentbutton.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    // Endpoint untuk registrasi
    @FormUrlEncoded
    @POST("register.php")  // Jalur relatif terhadap BASE_URL
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> registerUser(
            @Field("id") String id,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("request_surat_dokter.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> requestSuratDokter(
            @Field("nama") String nama,
            @Field("nis") String nis,
            @Field("keluhan") String keluhan,
            @Field("diagnosis") String diagnosis,
            @Field("pertolongan_pertama") String pertolonganPertama,
            @Field("tanggal") String tanggal
    );


    // Endpoint untuk login
    @FormUrlEncoded
    @POST("login.php")  // Jalur relatif terhadap BASE_URL
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
