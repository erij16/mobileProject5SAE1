package com.example.livraison.api;




import com.example.livraison.model.livraison;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LivraisonApi {

    @POST("livraison")
    Call<Void> onCreate(@Body livraison livraison);

@PUT("livraison/{id}")
Call<Void> updateLivraison(@Path("id") int id, @Body livraison livraison);
}

