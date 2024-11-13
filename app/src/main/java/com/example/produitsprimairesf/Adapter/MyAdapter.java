package com.example.produitsprimairesf.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.produitsprimairesf.R;
import com.example.produitsprimairesf.ScanQRCodeActivity;
import com.example.produitsprimairesf.database.AppDatabase;
import com.example.produitsprimairesf.entities.ProductP;
import com.example.produitsprimairesf.entities.QRCodeUtil;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<ProductP> productPList;


    public MyAdapter(Context context, List<ProductP> productPList) {
        this.context = context;
        this.productPList = productPList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_code, tv_nom, tv_quantite, tv_prix_u, tv_seuil_min;
        private Button btn_delete, btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_nom = itemView.findViewById(R.id.tv_nom);
            tv_quantite = itemView.findViewById(R.id.tv_quantite);
            tv_prix_u = itemView.findViewById(R.id.tv_prix_u);
            tv_seuil_min = itemView.findViewById(R.id.tv_seuil_min);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit); // Récupérer le bouton modifier
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemStockView = LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false);
        return new ViewHolder(mItemStockView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductP productP = productPList.get(position);

        // Remplir les informations du produit
        holder.tv_code.setText("Code du produit : " + productP.getCode());
        holder.tv_nom.setText("Nom du produit : " + productP.getNom());
        holder.tv_quantite.setText("Quantité : " + productP.getQuantite());
        holder.tv_prix_u.setText("Prix : " + productP.getPrix_unitaire());
        holder.tv_seuil_min.setText("Seuil : " + productP.getSeuil_min());

        // Afficher l'image du produit (si disponible)
        ImageView imgProduct = holder.itemView.findViewById(R.id.img_product);
        if (productP.getImageUri() != null) {
            imgProduct.setImageURI(Uri.parse(productP.getImageUri()));
        } else {
            imgProduct.setImageResource(R.drawable.default_image);  // Image par défaut
        }

        // Masquer l'image du QR code au début
        ImageView imgQrCode = holder.itemView.findViewById(R.id.img_qr_code);
        imgQrCode.setVisibility(View.GONE); // Masquer l'image du QR code par défaut

        // Ajouter un listener pour générer le QR code après le clic
        Button btnGenerateQr = holder.itemView.findViewById(R.id.btn_generate_qr);
        btnGenerateQr.setOnClickListener(v -> {
            // Calculer la différence (quantité minimale - quantité)
            int difference = productP.getSeuil_min() - productP.getQuantite();

            // Créer un texte à inclure dans le QR code
            String qrCodeText = "Code: " + productP.getCode() + "\n" +
                    "Vous avez " + difference + " pieces restantes par rapport au seuil minimal";

            // Générer le QR code avec le texte modifié
            Bitmap qrCodeBitmap = QRCodeUtil.generateQRCode(qrCodeText);
            imgQrCode.setImageBitmap(qrCodeBitmap);
            imgQrCode.setVisibility(View.VISIBLE); // Afficher l'ImageView du QR code après génération
        });

        // Ajouter un listener pour scanner le QR code
        imgQrCode.setOnClickListener(v -> {
            Intent intent = new Intent(context, ScanQRCodeActivity.class);
            context.startActivity(intent);
        });

        // Bouton Modifier
        holder.btn_edit.setOnClickListener(v -> {
            editItem(position);  // Appelle la méthode de modification
        });

        // Bouton Supprimer
        holder.btn_delete.setOnClickListener(v -> {
            deleteItem(position);  // Appelle la méthode de suppression
        });
    }


    private void deleteItem(int position) {
        ProductP productP = productPList.get(position);

        // Supprimer le produit de la base de données
        AppDatabase.getAppDatabase(context).productDAO().deleteProduct(productP);

        // Retirer l'élément de la liste et mettre à jour l'adaptateur
        productPList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productPList.size());  // Mettre à jour la liste
    }







    // Méthode pour modifier un item
    // Méthode pour modifier un item
    private void editItem(int position) {
        ProductP productP = productPList.get(position);

        // Créer une boîte de dialogue pour les modifications
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modifier le produit");

        // Créer la vue de la boîte de dialogue à partir d’un layout XML personnalisé
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_product, null);
        builder.setView(dialogView);

        // Récupérer les champs de saisie pour chaque attribut
        EditText et_code = dialogView.findViewById(R.id.et_code);
        EditText et_nom = dialogView.findViewById(R.id.et_nom);
        EditText et_quantite = dialogView.findViewById(R.id.et_quantite);
        EditText et_prix_u = dialogView.findViewById(R.id.et_prix_u);
        EditText et_seuil_min = dialogView.findViewById(R.id.et_seuil_min);
        ImageView imgProduct = dialogView.findViewById(R.id.img_product_edit); // Image du produit

        // Remplir les champs avec les valeurs actuelles de l'item
        et_code.setText(productP.getCode());
        et_nom.setText(productP.getNom());
        et_quantite.setText(String.valueOf(productP.getQuantite()));
        et_prix_u.setText(String.valueOf(productP.getPrix_unitaire()));
        et_seuil_min.setText(String.valueOf(productP.getSeuil_min()));
        if (productP.getImageUri() != null) {
            imgProduct.setImageURI(Uri.parse(productP.getImageUri()));
        }

        // Boutons de la boîte de dialogue
        builder.setPositiveButton("Enregistrer", (dialog, which) -> {
            // Mettre à jour l’item avec les nouvelles valeurs
            productP.setCode(et_code.getText().toString());
            productP.setNom(et_nom.getText().toString());
            productP.setQuantite(Integer.parseInt(et_quantite.getText().toString()));
            productP.setPrix_unitaire(Double.parseDouble(et_prix_u.getText().toString()));
            productP.setSeuil_min(Integer.parseInt(et_seuil_min.getText().toString()));

            // Mettre à jour le produit dans la base de données
            AppDatabase.getAppDatabase(context).productDAO().updateProduct(productP);

            // Notifier l’adaptateur de la modification de l’item
            notifyItemChanged(position);
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        // Afficher la boîte de dialogue
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return productPList.size();
    }
}
