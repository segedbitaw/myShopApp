package com.example.myfavoritecharacters.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myfavoritecharacters.R;
import com.example.myfavoritecharacters.activities.MainActivity;
import com.example.myfavoritecharacters.adapters.ProductsAdapter;
import com.example.myfavoritecharacters.classes.myProducts;
import com.example.myfavoritecharacters.fragments.models.Product;

import java.util.ArrayList;

public class AllProductsFragment extends Fragment {

    private ArrayList<Product> arr;
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private LinearLayoutManager layoutManager;

    public AllProductsFragment() {
        // Required empty public constructor
    }


    public static AllProductsFragment newInstance(String param1, String param2) {
        AllProductsFragment fragment = new AllProductsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        recyclerView = view.findViewById(R.id.shopRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arr = new ArrayList<Product>();

        for (int i = 0; i < myProducts.nameArray.length ; i++){
            String imageName = getResources().getResourceEntryName(myProducts.drawableArray[i]);
            arr.add ( new Product(
                    myProducts.nameArray[i],
                    myProducts.priceArray[i],
                    imageName
            ) );
        }
        adapter = new ProductsAdapter(arr);
        recyclerView.setAdapter(adapter);

        Button cartBtn = view.findViewById(R.id.GoToCart);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_allProductsFragment_to_myCart);
            }
        });

        return view;
    }
}