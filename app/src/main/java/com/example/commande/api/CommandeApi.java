package com.example.commande.api;

import com.example.commande.model.commande;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommandeApi {
    @POST("commandes")
    Call<Void> addCommande(@Body commande commande);

    @PUT("commande/{id}")
    Call<Void> updateCommande(@Path("id") int id, @Body commande updatedCommande);
    @DELETE("/commandes/{id}")
    Call<Void> deleteCommande(@Path("id") int id);
}
