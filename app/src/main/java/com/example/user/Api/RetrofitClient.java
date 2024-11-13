package com.example.user.Api;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Remplacez par l'adresse IP ou le nom de domaine de votre serveur
    private static final String BASE_URL = "http://127.0.0.1:8082/";
    private static Retrofit retrofit;

    // Méthode pour obtenir l'instance de Retrofit
    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}