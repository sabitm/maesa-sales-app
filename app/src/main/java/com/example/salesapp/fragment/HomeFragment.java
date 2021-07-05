package com.example.salesapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.salesapp.R;
import com.example.salesapp.activity.CartActivity;
import com.example.salesapp.activity.LoginActivity;
import com.example.salesapp.adapter.AllProductAdapter;
import com.example.salesapp.api.ApiService;
import com.example.salesapp.api.RetrofitBuilder;
import com.example.salesapp.api.TokenManager;
import com.example.salesapp.model.GetResponseProduct;
import com.example.salesapp.model.Product;
import com.example.salesapp.util.Converter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AllProductAdapter.CallBackUs, AllProductAdapter.HomeCallBack {

    private static final String TAG = "HomeFragment";

    public static int cart_count = 0;
    private ImageView ivCart;

    private View mProgressBar;
    private ProgressBar mCycleProgressBar;

    private RecyclerView recyclerView;
    private AllProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseProduct> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        ivCart = view.findViewById(R.id.image_view_cart);
        mProgressBar = view.findViewById(R.id.progress_bar_login);
        mCycleProgressBar = mProgressBar.findViewById(R.id.progress_bar_cycle);
        ivCart.setImageDrawable(Converter.convertLayoutToImage(getContext(), cart_count, R.drawable.ic_keranjang));

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        getDataProduct();

        ivCart.setOnClickListener(view1 -> {
            if (cart_count < 1) {
                Toast.makeText(getContext(), "Tidak ada item di keranjang", Toast.LENGTH_SHORT).show();
            } else {
                Intent intentToCart = new Intent(getContext(), CartActivity.class);
                startActivity(intentToCart);
            }
        });

        return view;
    }

    private void getDataProduct() {
        mProgressBar.setVisibility(View.VISIBLE);
        mCycleProgressBar.setVisibility(View.VISIBLE);
        call = service.getProduct();
        call.enqueue(new Callback<GetResponseProduct>() {
            @Override
            public void onResponse(Call<GetResponseProduct> call, Response<GetResponseProduct> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    ArrayList<Product> productList = response.body().getProduct();
                    adapter = new AllProductAdapter(productList, getContext(), HomeFragment.this);
                    recyclerView.setAdapter(adapter);
                }
                mProgressBar.setVisibility(View.GONE);
                mCycleProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetResponseProduct> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                mProgressBar.setVisibility(View.GONE);
                mCycleProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void addCartItemView() {

    }

    @Override
    public void updateCartCount(Context context) {
        getActivity().invalidateOptionsMenu();
    }
}