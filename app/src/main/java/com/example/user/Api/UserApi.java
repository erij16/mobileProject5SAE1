package com.example.user.Api;

import com.example.user.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    // Ajouter un utilisateur
    @POST("users")
    Call<Void> addUser(@Body User user); // Paramètre est 'UserModel'

    // Mettre à jour un utilisateur existant
    @PUT("users/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body User updatedUser); // Paramètre est 'UserModel'

    // Supprimer un utilisateur
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    // Récupérer les utilisateurs (si vous voulez obtenir tous les utilisateurs)
    @GET("users")
    Call<List<User>> getAllUsers(); // Cette méthode récupère tous les utilisateurs
}
