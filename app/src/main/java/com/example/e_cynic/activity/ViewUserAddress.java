package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.adapter.ViewAddressListAdapter;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

import java.util.List;

public class ViewUserAddress extends AppCompatActivity {

    private RecyclerView rv_addresses;
    private List<Address> addressList;

    private String username;
    private Integer userId;

    private ViewAddressListAdapter adapter;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_address);

        sessionManager = new SessionManager(ViewUserAddress.this);
        userId = UserDatabase.getUserIdByUsername(sessionManager.getUsername());

        rv_addresses = findViewById(R.id.rv_addresses);


        updateAddressList();
        adapter = new ViewAddressListAdapter(this, ViewUserAddress.this, addressList);
        setUpRecyclerView();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent();
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });
    }

    public void setUpRecyclerView() {
        updateAddressList();
        ViewAddressListAdapter adapter = new ViewAddressListAdapter(this, ViewUserAddress.this, addressList);
        rv_addresses.setAdapter(adapter);
        rv_addresses.setLayoutManager(new LinearLayoutManager(ViewUserAddress.this));
    }

    private void updateAddressList() {
        try {
            addressList = AddressDatabase.getAddressesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.EDIT_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    SnackbarCreator.createNewSnackbar(rv_addresses, "Address has been updated");
                    setUpRecyclerView();
                } else if (resultCode == RESULT_CANCELED) {
                    SnackbarCreator.createNewSnackbar(rv_addresses, "Address was not updated");
                }
        }
    }
}