package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.adapter.VoucherListAdapter;
import com.example.e_cynic.entity.Voucher;
import com.example.e_cynic.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class RedeemPointsActivity extends AppCompatActivity
{
    private ImageView backBtn;
    private TextView availablePoints;
    private SessionManager sm;
    private List<Voucher> voucherList;
    private RecyclerView voucherRecyclerView;
    private VoucherListAdapter redeemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_points);

        sm = new SessionManager(getApplicationContext());
        setViewComponent();
        setAvailablePoints();
        storeDataIntoVoucherList();
        setUpAdapter();

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(RedeemPointsActivity.this,
                        ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void setViewComponent()
    {
        backBtn = findViewById(R.id.backBtn);
        availablePoints = findViewById(R.id.availablePoints);
        voucherRecyclerView = findViewById(R.id.rewardsRecyclerView);
    }

    private void setUpAdapter()
    {
        redeemListAdapter = new VoucherListAdapter(getApplicationContext(),RedeemPointsActivity.this, voucherList);
        voucherRecyclerView.setAdapter(redeemListAdapter);
        voucherRecyclerView.setLayoutManager(new LinearLayoutManager(RedeemPointsActivity.this));
    }

    private void storeDataIntoVoucherList()
    {
        voucherList = new ArrayList<>();
        voucherList.add(new Voucher(R.drawable.aeon,"Aeon RM50 Cash Voucher",5000));
        voucherList.add(new Voucher(R.drawable.tng_50,"TNG RM50 Cash Voucher",5000));
        voucherList.add(new Voucher(R.drawable.shell,"Shell RM50 Cash Voucher",5000));
        voucherList.add(new Voucher(R.drawable.baskin,"Baskin Robbins RM20 Cash Voucher",2000));
        voucherList.add(new Voucher(R.drawable.secret,"Secret Recipe RM20 Cash Voucher",2000));
        voucherList.add(new Voucher(R.drawable.lazada,"Lazada RM10 Cash Voucher",1000));
        voucherList.add(new Voucher(R.drawable.tealive,"Tealive RM10 Cash Voucher",1000));
        voucherList.add(new Voucher(R.drawable.tng_10,"TNG RM10 Cash Voucher",1000));
        voucherList.add(new Voucher(R.drawable.spca,"SPCA RM10 Donation Voucher",1000));
        voucherList.add(new Voucher(R.drawable.unicef,"Unicef RM5 Donation Voucher",500));
    }

    public void setAvailablePoints()
    {
        int p = sm.getTotalPoints();
        availablePoints.setText(String.valueOf(p));
    }
}