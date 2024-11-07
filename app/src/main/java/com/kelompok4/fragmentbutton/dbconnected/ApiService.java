package com.kelompok4.fragmentbutton.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    // Endpoint untuk registrasi pengguna
    @FormUrlEncoded
    @POST("register.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> registerUser(
            @Field("id") String id,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

    // Endpoint untuk meminta surat dokter
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

    // Endpoint untuk login pengguna
    @FormUrlEncoded
    @POST("login.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    // Endpoint untuk mengambil username berdasarkan user_id
    @FormUrlEncoded
    @POST("get_username.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> getUsername(
            @Field("user_id") String userId
    );

    // Endpoint untuk mendapatkan data profil pengguna
    @FormUrlEncoded
    @POST("get_user_profile.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> getUserProfile(
            @Field("user_id") String userId
    );

    // Endpoint untuk memperbarui profil pengguna
    @FormUrlEncoded
    @POST("update_profile.php")
    Call<com.kelompok4.fragmentbutton.network.ApiResponse> updateUserProfile(
            @Field("user_id") String userId,
            @Field("username") String username,
            @Field("email") String email,
            @Field("nomor_telepon") String nomorTelepon
    );
}
