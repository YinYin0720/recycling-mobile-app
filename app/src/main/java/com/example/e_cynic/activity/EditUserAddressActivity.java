package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.utils.ValidationUtil;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

public class EditUserAddressActivity extends AppCompatActivity {

    private Integer addressId;
    private Address address;

    private EditText et_address_line1;
    private EditText et_address_line2;
    private EditText et_address_line3;
    private EditText et_postcode;
    private EditText et_city;
    private Spinner spinner_state;

    private Button btn_editAddress;

    private boolean acceptMessage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_address);
        setUpViewComponents();

        Intent intent = getIntent();
        addressId = Integer.valueOf(intent.getStringExtra("addressId"));
        try {
            address = AddressDatabase.getAddressByAddressId(addressId);
            updateAddressFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAddressFields() {
        et_address_line1.setText(address.firstLine);
        if (!address.secondLine.equals("") || address.secondLine != null) {
            et_address_line2.setText(address.secondLine);
        }
        if (!address.thirdLine.equals("") || address.thirdLine != null) {
            et_address_line3.setText(address.thirdLine);
        }
        et_postcode.setText(String.valueOf(address.postcode));
        et_city.setText(address.city);
        spinner_state.setSelection(getStateIdInSpinner(address.state));
    }

    private void setUpViewComponents() {
        et_address_line1 = findViewById(R.id.addressLine1);
        et_address_line2 = findViewById(R.id.addressLine2);
        et_address_line3 = findViewById(R.id.addressLine3);
        et_postcode = findViewById(R.id.postcode);
        et_city = findViewById(R.id.city);
        spinner_state = findViewById(R.id.state);

        btn_editAddress = findViewById(R.id.btn_editAddress);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EditUserAddressActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(myAdapter);

        btn_editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAddressFromFields();
                if (validateAddress() == true) {
                    Intent intent = new Intent();
                    boolean result = false;
                    try {
                        result = AddressDatabase.editAddressByAddressId(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (result == true) {
                        setResult(RESULT_OK, intent);
                    } else {
                        setResult(RESULT_CANCELED, intent);
                    }
                    finish();
                }
                else {
                    SnackbarCreator.createNewSnackbar(btn_editAddress, "Please enter valid address");
                }
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });
    }

    private void updateAddressFromFields() {
        address.firstLine = et_address_line1.getText().toString();
        address.secondLine = et_address_line2.getText().toString();
        address.thirdLine = et_address_line3.getText().toString();
        address.postcode = Integer.valueOf(!et_postcode.getText().toString().equals("") ? et_postcode.getText().toString() : "0");
        address.state = spinner_state.getSelectedItem().toString();
        address.city = et_city.getText().toString();
    }

    private boolean validateAddress() {
        boolean complete = true;
        if (address.firstLine.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(et_address_line1, "Line 1 field is empty");
        } else {
            resetField(et_address_line1);
        }
        if (address.postcode == 0) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(et_postcode, "Postcode field is empty");
        }
        else if (!ValidationUtil.validatePostcode(String.valueOf(address.postcode))){
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(et_postcode, "Please ensure postcode is in correct format");
        }
        else {
            resetField(et_postcode);
        }
        if (address.city.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(et_city, "City field is empty");
        }
        else if(!ValidationUtil.validateCity(address.city)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(et_city, "Please ensure city field contains only alphabets");
        }
        else {
            resetField(et_city);
        }
        return complete;
    }

    private int getStateIdInSpinner(String state) {
        for (int i = 0; i < spinner_state.getCount(); i++) {
            if (spinner_state.getItemAtPosition(i).equals(state)) {
                return i;
            }
        }
        return -1;
    }
    private void setErrorFieldAndDisplaySnackBarMessage(EditText et, String message) {
        setErrorField(et);
        if(acceptMessage) {
            SnackbarCreator.createNewSnackbar(btn_editAddress, message);
            acceptMessage = false;
        }
    }

    private void setErrorField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.error_background));
    }

    private void resetField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.grey));
    }
}