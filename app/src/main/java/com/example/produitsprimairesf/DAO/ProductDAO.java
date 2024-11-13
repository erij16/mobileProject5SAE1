package com.example.produitsprimairesf.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.produitsprimairesf.entities.ProductP;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    public void createProduct(ProductP productP);

    @Update
    public void updateProduct(ProductP productP);

    @Delete
    public void deleteProduct(ProductP productP);

    @Query("SELECT * FROM ProductP")
    public List<ProductP> getAllProducts();

    @Query("SELECT * FROM ProductP WHERE id = :id")
    public ProductP getProductById(int id);

    // Méthode pour chercher un produit par son code
    @Query("SELECT * FROM ProductP WHERE code = :code")
    public ProductP getProductByCode(String code);

    @Query("DELETE FROM ProductP")
    void deleteAllProducts();
}
