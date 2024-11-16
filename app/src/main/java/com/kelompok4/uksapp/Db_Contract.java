package com.kelompok4.uksapp;

public class Db_Contract {

    public static String ip = "10.0.2.2"; // Local IP for Android emulator

    // Corrected URLs with a single slash after IP
    public static final String urlRegister = "http://"+ip+"/my_api_android/api-register.php";
    public static final String urlLogin = "http://"+ip+"/my_api_android/api-login.php";
    public static final String urlProfile = "http://"+ip+"/my_api_android/profile.php";
    public static final String urlRequestSuratIzin = "http://"+ip+"/my_api_android/request_surat_izin.php";
}
