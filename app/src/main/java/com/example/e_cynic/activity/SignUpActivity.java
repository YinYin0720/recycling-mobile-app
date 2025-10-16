package com.example.e_cynic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.entity.User;
import com.example.e_cynic.session.AppSharedPreferences;
import com.example.e_cynic.utils.ValidationUtil;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity
{

    public final long DELAY = 1000;
    // views
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText postcode;
    private EditText city;
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText addressLine3;
    private Spinner state;
    private Button signUpBtn;

    // text of views
    private String usernameTxt;
    private String emailTxt;
    private String phoneTxt;
    private String passwordTxt;
    private String postcodeTxt;
    private String cityTxt;
    private String addressLine1Txt;
    private String addressLine2Txt;
    private String addressLine3Txt;
    private String stateTxt;

    private boolean acceptMessage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        setViewComponent();

        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateViewText();

                if (!fieldDataIsComplete())
                {
                    return;
                }

                if(UserDatabase.checkUsernameExistence(usernameTxt) == true)
                {
                    SnackbarCreator.createNewSnackbar(view,"Username already exist, please try another one.");
                    setErrorField(username);
                    return;
                }

                if(UserDatabase.checkEmailExistence(emailTxt) == true) {
                    SnackbarCreator.createNewSnackbar(view,"Account with the given email already exist");
                    setErrorField(email);
                    return;
                }

                User user = new User(null,usernameTxt,emailTxt,passwordTxt,phoneTxt);
                boolean insertUser = false;
                try {
                    insertUser = UserDatabase.insertUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (insertUser == true)
                {
                    Integer userId = -1;

                    try {
                        userId = UserDatabase.getUserIdByUsername(usernameTxt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (userId > 0)
                    {
                        Address address = new Address(null,userId,addressLine1Txt,
                                addressLine2Txt, addressLine3Txt,cityTxt,stateTxt,
                                !postcodeTxt.equals("")?Integer.parseInt(postcodeTxt):0);
                        boolean insertAddress = false;
                        try {
                            insertAddress = AddressDatabase.insertAddress(address);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        if (insertAddress)
                        {
                            SnackbarCreator.createNewSnackbar(view,"Successfully sign up!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AppSharedPreferences.updateUser(usernameTxt);
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                }
                            }, DELAY);
                        }
                    }
                }
            }
        });
    }

    private void setViewComponent() {
        username = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        state = findViewById(R.id.state);
        postcode = findViewById(R.id.postcode);
        city = findViewById(R.id.city);
        addressLine1 = findViewById(R.id.addressLine1);
        addressLine2 = findViewById(R.id.addressLine2);
        addressLine3 = findViewById(R.id.addressLine3);
        signUpBtn = findViewById(R.id.signUpBtn);

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(myAdapter);
    }

    private boolean fieldDataIsComplete() {
        boolean complete = true;
        acceptMessage = true;
        if(usernameTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(username, "Username field is empty");
        }
        else if(!ValidationUtil.validateUsername(usernameTxt)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(username, "Please ensure your username contains 6 to 12 characters of alphabets and numbers");
        }
        else {
            resetField(username);
        }
        if(emailTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(email, "Email field is empty");
        }
        else if(!ValidationUtil.validateEmail(emailTxt))  {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(email, "Please ensure your email is valid");
        }
        else {
            resetField(email);
        }
        if(phoneTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(phone, "Phone number field is empty");
        }
        else if(!ValidationUtil.validatePhoneNumber(phoneTxt)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(phone, "Please ensure your phone number is in correct format");
        }
        else {
            resetField(phone);
        }
        if(passwordTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(password, "Password field is empty");
        }
        else if(!ValidationUtil.validatePassword(passwordTxt)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(password, "Please ensure your password fulfils the requirement");
        }
        else {
            resetField(password);
        }
        if(addressLine1Txt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(addressLine1, "Address line 1 field is empty");
        }
        else {
            resetField(addressLine1);
        }
        if(postcodeTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(postcode, "Postcode field is empty");
        }
        else if(!ValidationUtil.validatePostcode(postcodeTxt)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(postcode, "Please ensure postcode is in correct format");
        }
        else {
            resetField(postcode);
        }
        if (cityTxt.equals("")) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(city, "City field is empty");
        }
        else if(!ValidationUtil.validateCity(cityTxt)) {
            complete = complete && false;
            setErrorFieldAndDisplaySnackBarMessage(city, "Please ensure city field contains only alphabets");
        }
        else {
            resetField(city);
        }
        return complete;
    }

    private void setErrorFieldAndDisplaySnackBarMessage(EditText et, String message) {
        setErrorField(et);
        if(acceptMessage) {
            SnackbarCreator.createNewSnackbar(signUpBtn, message);
            acceptMessage = false;
        }
    }

    private void setErrorField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.error_background));
    }

    private void resetField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    private void updateViewText() {
        usernameTxt = username.getText().toString();
        emailTxt = email.getText().toString();
        phoneTxt = phone.getText().toString();
        passwordTxt = password.getText().toString();
        postcodeTxt = postcode.getText().toString();
        cityTxt = city.getText().toString();
        addressLine1Txt = addressLine1.getText().toString();
        addressLine2Txt = addressLine2.getText().toString();
        addressLine3Txt = addressLine3.getText().toString();
        stateTxt = state.getSelectedItem().toString();
    }
}