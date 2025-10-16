package com.example.e_cynic.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.adapter.RecycleAddItemAdapter;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.db.ItemDatabase;
import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.entity.Item;
import com.example.e_cynic.entity.Order;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.ImageUtil;
import com.example.e_cynic.utils.ValidationUtil;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity {
    // Views
    private LinearLayout ll_new_address;
    private LinearLayout ll_existing_address;
    private ImageView example;
    private Button pinLocation;
    private Button submitRecycleBtn, addItem;
    private RecyclerView recycler_view;
    private RecycleAddItemAdapter rvAdapter;
    private SessionManager sessionManager;
    private Button btn_selectExistingAddress;

    //new address fields
    private EditText et_addLine1;
    private EditText et_addLine2;
    private EditText et_addLine3;
    private Spinner spinner_state;
    private EditText et_postcode;
    private EditText et_city;

    //existing address fields
    private TextView tv_address_line1;
    private TextView tv_address_line2;
    private TextView tv_address_line3;
    private TextView tv_address_postcode_city;
    private TextView tv_address_state;

    public ArrayList<Item> items;
    public Address address;

    public int clickedItem = -1;
    private boolean acceptMessage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        setViewComponent();
        bottomNavBar();
        RV_AddItem();
        sessionManager = new SessionManager(getApplicationContext());

        submitRecycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = sessionManager.getUsername();
                Integer userId = UserDatabase.getUserIdByUsername(username);

                try {

                    updateAddressFromRecycleForm(userId);

                    if (validateOrderInput() == false) {
                        return;
                    }

                    long addressId = 0;
                    //insert address to db
                    if (address.addressId == null) {
                        addressId = AddressDatabase.insertAddressAndGetAddressId(address);
                        if (addressId <= 0) {
                            SnackbarCreator.createNewSnackbar(submitRecycleBtn, "Please try again");
                            return;
                        }
                    } else {
                        addressId = address.addressId;
                    }

                    //create order and get orderid
                    long orderId = OrderDatabase.insertOrderAndGetOrderId(new Order(null, userId, (int) addressId,
                            DateUtil.getCurrentTimestamp(), null));
                    if (orderId <= 0) {
                        SnackbarCreator.createNewSnackbar(submitRecycleBtn, "Please try again");
                        return;
                    }

                    //insert items to order
                    for (Item item : items) {
                        item.orderId = (int) orderId;
                        boolean result = ItemDatabase.insertItem(item);
                        if (result == false) {
                            SnackbarCreator.createNewSnackbar(submitRecycleBtn, "Please try again");
                            return;
                        }
                    }


                    //direct to order detail activity, pass orderid
                    Intent intent = new Intent(RecycleActivity.this, OrderDetailActivity.class);
                    intent.putExtra("orderId", String.valueOf(orderId));
                    startActivityForResult(intent, RequestCode.VIEW_ORDER_DETAILS_ACTIVITY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        pinLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecycleActivity.this, PinLocationActivity.class);
                startActivityForResult(i, RequestCode.PIN_LOCATION_ACTIVITY);
            }
        });

        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecycleActivity.this, ElectronicAppliancesExampleActivity.class);
                startActivity(i);
            }
        });

        btn_selectExistingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecycleActivity.this, SelectAddressActivity.class);
                startActivityForResult(intent, RequestCode.SELECT_ADDRESS_ACTIVITY);
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(myAdapter);
    }


    private void RV_AddItem() {

        //add item recycler view
        addItem = findViewById(R.id.btn_addItem);
        items = new ArrayList<>();
        items.add(new Item());

        //set click
        addItem.setOnClickListener(new View.OnClickListener() {
            int i = 1;

            @Override
            public void onClick(View view) {
                i++;
                items.add(new Item());
                rvAdapter.notifyItemInserted(i);
            }
        });
        updateRecyclerView();
    }

    public void updateRecyclerView() {
        recycler_view = findViewById(R.id.enterItemDetail);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new RecycleAddItemAdapter(this, items, RecycleActivity.this);
        recycler_view.setAdapter(rvAdapter);
    }

    private void setViewComponent() {
        ll_new_address = findViewById(R.id.ll_new_address);
        ll_existing_address = findViewById(R.id.ll_existing_address);

        ll_existing_address.setVisibility(View.GONE);

        example = findViewById(R.id.example);
        submitRecycleBtn = findViewById(R.id.btn_submitRecycle);
        pinLocation = findViewById(R.id.pinLocation);
        btn_selectExistingAddress = findViewById(R.id.btn_selectExistingAddress);

        et_addLine1 = findViewById(R.id.addressLine1);
        et_addLine2 = findViewById(R.id.addressLine2);
        et_addLine3 = findViewById(R.id.addressLine3);
        spinner_state = findViewById(R.id.spinner_state);
        et_postcode = findViewById(R.id.postcode);
        et_city = findViewById(R.id.city);

        tv_address_line1 = findViewById(R.id.tv_address_line1);
        tv_address_line2 = findViewById(R.id.tv_address_line2);
        tv_address_line3 = findViewById(R.id.tv_address_line3);
        tv_address_postcode_city = findViewById(R.id.tv_address_postcode_city);
        tv_address_state = findViewById(R.id.tv_address_state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case RequestCode.SNAP_PHOTO:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap capturedImg = (Bitmap) data.getExtras().get("data");
                        items.get(clickedItem).image = ImageUtil.bitmapToByteArray(capturedImg);
                        updateRecyclerView();
                    }
                    break;

                case RequestCode.CHOOSE_FROM_GALLERY:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};

                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePath, null, null, null);

                            if (cursor != null) {
                                cursor.moveToFirst();

                                int colInd = cursor.getColumnIndex(filePath[0]);
                                String imgPath = cursor.getString(colInd);
//                                items.get(clickedItem).image = ImageUtil.imagePathToByteArray(imgPath);
                                try {
                                    items.get(clickedItem).image = ImageUtil.bitmapToByteArray(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                updateRecyclerView();
                                cursor.close();
                            }
                        }
                    }
                    break;

                case RequestCode.PIN_LOCATION_ACTIVITY:
                    if (resultCode == RESULT_OK && data != null) {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        String username = sessionManager.getUsername();
                        Integer userId = UserDatabase.getUserIdByUsername(username);
                        address = new Address(null,
                                userId,
                                data.getStringExtra("firstLine"),
                                data.getStringExtra("secondLine"),
                                null,
                                data.getStringExtra("city"),
                                data.getStringExtra("state"),
                                Integer.parseInt(data.getStringExtra("postcode")));
                        ll_new_address.setVisibility(View.VISIBLE);
                        ll_existing_address.setVisibility(View.GONE);
                        updateNewAddressField();
                    }
                    break;

                case RequestCode.SELECT_ADDRESS_ACTIVITY:
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            address = AddressDatabase.getAddressByAddressId(Integer.parseInt(data.getStringExtra("addressId")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ll_new_address.setVisibility(View.GONE);
                        ll_existing_address.setVisibility(View.VISIBLE);
                        updateExistingAddressField();
                    }
                    break;

                case RequestCode.VIEW_ORDER_DETAILS_ACTIVITY:
                    if(resultCode == RESULT_OK) {
                        Intent intent = new Intent(RecycleActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
            }
        }
    }

    private void updateNewAddressField() {
        et_addLine1.setText(address.firstLine);
        et_addLine2.setText(address.secondLine);
        et_addLine3.setText(address.thirdLine);
        spinner_state.setSelection(getStateIdInSpinner(address.state));
        et_postcode.setText(String.valueOf(address.postcode));
        et_city.setText(address.city);
    }

    private void updateExistingAddressField() {
        tv_address_line1.setText(address.firstLine);
        if (!address.secondLine.equals("")) {
            tv_address_line2.setText(address.secondLine);
            tv_address_line2.setVisibility(View.VISIBLE);
        } else {
            tv_address_line2.setText("");
            tv_address_line2.setVisibility(View.GONE);
        }

        if (!address.thirdLine.equals("")) {
            tv_address_line3.setText(address.thirdLine);
            tv_address_line3.setVisibility(View.VISIBLE);
        } else {
            tv_address_line3.setText("");
            tv_address_line3.setVisibility(View.GONE);
        }
        tv_address_line3.setText(address.thirdLine);
        tv_address_postcode_city.setText(String.valueOf(address.postcode) + " " + address.city);
        tv_address_state.setText(address.state);
    }

    private void updateAddressFromRecycleForm(Integer userId) {
        if (address == null) {
            address = new Address();
        }
        address.userId = userId;
        address.firstLine = et_addLine1.getText().toString();
        address.secondLine = et_addLine2.getText().toString();
        address.thirdLine = et_addLine3.getText().toString();
        address.state = spinner_state.getSelectedItem().toString();
        address.postcode = Integer.parseInt(et_postcode.getText().toString().equals("") ? "0" : et_postcode.getText().toString());
        address.city = et_city.getText().toString();
    }

    private int getStateIdInSpinner(String state) {
        for (int i = 0; i < spinner_state.getCount(); i++) {
            if (spinner_state.getItemAtPosition(i).equals(state)) {
                return i;
            }
        }
        return -1;
    }

    private boolean validateOrderInput() {
        acceptMessage = true;
        for (Item item : items) {
            if (item.image == null) {
                SnackbarCreator.createNewSnackbar(submitRecycleBtn, "Please upload one image for each item");
                return false;
            }
        }

        if (ll_new_address.getVisibility() == View.VISIBLE) {
            boolean complete = true;
            if(address.firstLine.equals("")) {
                complete = complete && false;
                setErrorFieldAndDisplaySnackBarMessage(et_addLine1, "Address line 1 field is empty");
            }
            else {
                resetField(et_addLine1);
            }
            if(address.postcode == 0) {
                complete = complete && false;
                setErrorFieldAndDisplaySnackBarMessage(et_postcode, "Postcode field is empty");
            }
            else if(!ValidationUtil.validatePostcode(String.valueOf(address.postcode))) {
                complete = complete && false;
                setErrorFieldAndDisplaySnackBarMessage(et_postcode, "Please ensure postcode is in correct format");
            }
            else {
                resetField(et_postcode);
            }
            if(address.city.equals("")) {
                complete = complete && false;
                setErrorFieldAndDisplaySnackBarMessage(et_city, "City field is empty");
            }
            else if(!ValidationUtil.validateCity(address.city)) {
                complete = complete && false;
                setErrorFieldAndDisplaySnackBarMessage(et_city, "City name should contain only alphabets");
            }
            else {
                resetField(et_city);
            }

            return complete;
        }

        if (ll_existing_address.getVisibility() == View.VISIBLE) {
            return true;
        }
        return true;
    }

    private void setErrorFieldAndDisplaySnackBarMessage(EditText et, String message) {
        setErrorField(et);
        if(acceptMessage) {
            SnackbarCreator.createNewSnackbar(submitRecycleBtn, message);
            acceptMessage = false;
        }
    }

    private void setErrorField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.error_background));
    }

    private void resetField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    private void bottomNavBar() {
        // Initiate & assign variable
        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // Set selected layout
        btmNav.setSelectedItemId(R.id.recycle);

        // Perform item selected listener
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.recycle:
                        return true;

                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}