package com.example.canifa_shop.Selling;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Product.Object.Product;

import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;

import com.example.canifa_shop.Selling.Adapter.OnClickItem;
import com.example.canifa_shop.Selling.Adapter.SellingAdapter;
import com.example.canifa_shop.Selling.Adapter.SellingAdapterGrid;
import com.example.canifa_shop.databinding.FragmentSellingBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellingFragment extends Fragment {
    FragmentSellingBinding binding;
    SQLHelper sqlHelper;
    List<Product> productList;
    SellingAdapter sellingAdapter;
    SellingAdapterGrid sellingAdapterGrid;

    public static SellingFragment newInstance() {

        Bundle args = new Bundle();

        SellingFragment fragment = new SellingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selling, container, false);
        initialization();
        setAdapter();
        binding.imvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imvGrid.setVisibility(View.VISIBLE);
                binding.imvList.setVisibility(View.INVISIBLE);
                setAdapter();
            }
        });
        binding.imvGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imvGrid.setVisibility(View.INVISIBLE);
                binding.imvList.setVisibility(View.VISIBLE);
                setAdapter();
            }
        });
        binding.btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.insertProduct(new Product(1, "Hi", 123, 123, 123, "hi", "hi", "hi", "hi"));
                sellingAdapter.notifyDataSetChanged();
                sellingAdapterGrid.notifyDataSetChanged();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.deleteOrderProduct();
                binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
            }
        });
        return binding.getRoot();
    }

    public void initialization() {
        sqlHelper = new SQLHelper(getContext());
        productList = new ArrayList<>();
        sqlHelper.insertProduct(new Product());
        sqlHelper.insertProduct(new Product());
        productList = sqlHelper.getAllPrduct();
        sellingAdapter = new SellingAdapter(productList, getContext(), new OnClickItem() {
            @Override
            public void onClickItem() {
                binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
            }
        });
        sellingAdapterGrid = new SellingAdapterGrid(productList, getContext(), new OnClickItem() {
            @Override
            public void onClickItem() {
                binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
            }
        });
        binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
    }

    public void setAdapter() {
        if (binding.imvGrid.getVisibility() == View.VISIBLE) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
            binding.rvProduct.setLayoutManager(gridLayoutManager);
            binding.rvProduct.setAdapter(sellingAdapterGrid);

        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
            binding.rvProduct.setLayoutManager(gridLayoutManager);
            binding.rvProduct.setAdapter(sellingAdapter);
        }
    }
}
