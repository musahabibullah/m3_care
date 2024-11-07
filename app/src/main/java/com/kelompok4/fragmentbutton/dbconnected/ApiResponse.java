package com.kelompok4.fragmentbutton.network;

public class ApiResponse {
    private String status;   // Status dari API (success atau error)
    private String message;  // Pesan yang berasal dari server (misalnya sukses atau kesalahan)
    private com.kelompok4.fragmentbutton.network.UserProfile data; // Menyimpan data profil pengguna

    // Getter untuk status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter untuk message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter untuk data
    public com.kelompok4.fragmentbutton.network.UserProfile getData() {
        return data;
    }

    public void setData(com.kelompok4.fragmentbutton.network.UserProfile data) {
        this.data = data;
    }

    // Override toString untuk mempermudah debugging
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
