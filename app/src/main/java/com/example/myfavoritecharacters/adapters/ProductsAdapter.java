package com.example.myfavoritecharacters.adapters;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfavoritecharacters.activities.MainActivity;
import com.example.myfavoritecharacters.R;
import com.example.myfavoritecharacters.fragments.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.myViewHolder> {

    private ArrayList<Product> arr;

    public ProductsAdapter(ArrayList<Product> arr) {
        this.arr = arr;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice;
        ImageView productImage;
        CardView productCard;
        Button addToCartButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.ProductNameTextView);
            productPrice = itemView.findViewById(R.id.ProductPriceTextView);
            productImage = itemView.findViewById(R.id.ProductimageView);
            addToCartButton = itemView.findViewById(R.id.AddToCartButton);
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false);
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Product product = arr.get(position);

        holder.productName.setText(arr.get(position).getProductName());
        holder.productPrice.setText(arr.get(position).getPrice());

        int imageResId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(product.getImage(), "drawable", holder.itemView.getContext().getPackageName());

        holder.productImage.setImageResource(imageResId);
        holder.addToCartButton.setOnClickListener(v -> {
            // Save product to Firebase Realtime Database
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");

            // Set default quantity = 1
//            product.setQuantity(1);

            // Push product to Firebase under a unique key
            cartRef.child(product.getProductName()).setValue(product)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(holder.itemView.getContext(), "Added to cart", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(holder.itemView.getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() { return arr.size();}

}


