package com.example.myfavoritecharacters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfavoritecharacters.R;
import com.example.myfavoritecharacters.fragments.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class myCartAdapter extends FirebaseRecyclerAdapter<Product,myCartAdapter.viewHolder> {

    public myCartAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myCartAdapter.viewHolder holder, int position, @NonNull Product model) {

        holder.tvName.setText(model.getProductName());
        holder.tvPrice.setText(model.getPrice() +" INS ");
        holder.tvQuantity.setText(String.valueOf(model.getQuantity())+" Kilo ");
        int imageResId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(model.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageView.setImageResource(imageResId);

        holder.numberPicker.setMinValue(1); // Minimum quantity
        holder.numberPicker.setMaxValue(10); // Maximum quantity

        holder.numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Update quantity in Firebase
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");

            cartRef.orderByChild("productName").equalTo(model.getProductName())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                // Update the quantity
                                    child.getRef().child("quantity").setValue(newVal)
                                            .addOnSuccessListener(aVoid -> {
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(holder.itemView.getContext(), "Failed to update quantity", Toast.LENGTH_SHORT).show();
                                            });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(holder.itemView.getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        holder.deletButton.setOnClickListener(v -> {
            // Reference to the cart in Firebase
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");

            // Find the product in the cart based on productName
            cartRef.orderByChild("productName").equalTo(model.getProductName())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    // Remove the product from the cart
                                    child.getRef().removeValue()
                                            .addOnSuccessListener(aVoid ->
                                                    Toast.makeText(holder.itemView.getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e ->
                                                    Toast.makeText(holder.itemView.getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show());
                                }
                            } else {
                                Toast.makeText(holder.itemView.getContext(), "Item not found in cart", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(holder.itemView.getContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @NonNull
    @Override
    public myCartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false));
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName,tvPrice,  tvQuantity;
        NumberPicker numberPicker;
        Button deletButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            numberPicker = itemView.findViewById(R.id.quantityPicker);
            deletButton = itemView.findViewById(R.id.DeletButton);
        }
    }
}
