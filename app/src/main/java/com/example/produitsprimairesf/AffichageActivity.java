package com.example.produitsprimairesf;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.produitsprimairesf.Adapter.MyAdapter;
import com.example.produitsprimairesf.database.AppDatabase;
import com.example.produitsprimairesf.entities.ProductP;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import android.widget.ArrayAdapter;


public class AffichageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ProductP> productPList = new ArrayList<>();
    List<ProductP> filteredList = new ArrayList<>();
    AppDatabase database;
    MyAdapter myAdapter;
    EditText etSearch;
    Spinner spinnerMinPrice, spinnerMaxPrice;
    Button btnFilter;
    Spinner spinnerSortPrice;

    private void loadData() {
        productPList.clear();
        productPList.addAll(database.productDAO().getAllProducts());
        filteredList.clear();
        filteredList.addAll(productPList);
        myAdapter.notifyDataSetChanged();
    }

    private void searchProduct(String query) {
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(productPList);
        } else {
            for (ProductP productP : productPList) {
                if (productP.getNom().toLowerCase().contains(query.toLowerCase()) ||
                        productP.getCode().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(productP);
                }
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    private void filterByPrice() {
        double minPrice = Double.parseDouble(spinnerMinPrice.getSelectedItem().toString());
        double maxPrice = Double.parseDouble(spinnerMaxPrice.getSelectedItem().toString());
        filteredList.clear();

        for (ProductP productP : productPList) {
            if (productP.getPrix_unitaire() >= minPrice && productP.getPrix_unitaire() <= maxPrice) {
                filteredList.add(productP);
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);


        // Initialize sort spinner
        spinnerSortPrice = findViewById(R.id.spinner_sort_order);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortPrice.setAdapter(sortAdapter);

        // Set listener for sort spinner
        spinnerSortPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortProductList(position);  // Call sort method based on selected option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // Button Back
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AffichageActivity.this, GestionStockActivity.class);
            startActivity(intent);
            finish();
        });

        // Initialize Views
        etSearch = findViewById(R.id.et_search);
        spinnerMinPrice = findViewById(R.id.spinner_min_price);
        spinnerMaxPrice = findViewById(R.id.spinner_max_price);
        btnFilter = findViewById(R.id.btn_filter);

        // Load data and initialize RecyclerView
        database = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, filteredList);
        recyclerView.setAdapter(myAdapter);
        loadData();

        // Set up price range spinner adapters
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_range, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinPrice.setAdapter(priceAdapter);
        spinnerMaxPrice.setAdapter(priceAdapter);

        // Set search functionality
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchProduct(charSequence.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        // Set filter button click listener
        btnFilter.setOnClickListener(v -> filterByPrice());
    }
    private void sortProductList(int sortOption) {
        if (sortOption == 0) {
            // Tri ascendant par prix
            filteredList.sort(Comparator.comparingDouble(ProductP::getPrix_unitaire));
        } else {
            // Tri descendant par prix
            filteredList.sort((p1, p2) -> Double.compare(p2.getPrix_unitaire(), p1.getPrix_unitaire()));
        }
        myAdapter.notifyDataSetChanged();  // Mettre à jour l'adaptateur
    }

}
