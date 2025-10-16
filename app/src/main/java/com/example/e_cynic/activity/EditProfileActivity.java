package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.User;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.ValidationUtil;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

public class EditProfileActivity extends AppCompatActivity
{
    private ImageView backBtn;
    private EditText uname,email,phone,password;
    private SessionManager sm;
    private String unameTxt,emailTxt,phoneTxt,passwordTxt;
    private Button confirmBtn;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        sm = new SessionManager(getApplicationContext());

        setUpViewComponent();
        displayProfileData();

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(EditProfileActivity.this,
                        ProfileActivity.class);
                startActivity(i);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewText();
                updateUserInformation();

                if(validateProfile() == true) {
                    boolean result = false;
                    try {
                        result = UserDatabase.editUserByUserId(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent();
                    if(result == true) {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else {
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                } else {
                    SnackbarCreator.createNewSnackbar(confirmBtn, "Please enter valid information for all fields");
                }
            }
        });
    }

    private void updateUserInformation() {
        user.email = emailTxt;
        user.phoneNumber = phoneTxt;
        user.password = passwordTxt;
    }

    private void setUpViewComponent()
    {
        backBtn = findViewById(R.id.backBtn);
        uname = findViewById(R.id.uname);
        uname.setEnabled(false);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmBtn = findViewById(R.id.confirmBtn);
    }

    private void getViewText()
    {
        unameTxt = uname.getText().toString();
        emailTxt = email.getText().toString();
        phoneTxt = phone.getText().toString();
        passwordTxt = password.getText().toString();
    }

    private void displayProfileData()
    {
        String username = sm.getUsername();

        try
        {
            user = UserDatabase.getUserInfoByUsername(username);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        uname.setText(username);
        email.setText(user.email);
        phone.setText(user.phoneNumber);
        password.setText(user.password);
    }

    private boolean validateProfile() {
        boolean complete = true;
        if(emailTxt.equals("") || !ValidationUtil.validateEmail(emailTxt)) {
            setErrorField(email);
            complete = complete && false;
        }
        else {
            resetField(email);
        }
        if(phoneTxt.equals("") || !ValidationUtil.validatePhoneNumber(phoneTxt)) {
            setErrorField(phone);
            complete = complete && false;
        }
        else {
            resetField(phone);
        }
        if(passwordTxt.equals("") || !ValidationUtil.validatePassword(passwordTxt)) {
            setErrorField(password);
            complete = complete && false;
        }
        else {
            resetField(password);
        }
        return complete;
    }

    private void setErrorField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.error_background));
    }

    private void resetField(EditText et) {
        et.setBackgroundColor(getResources().getColor(R.color.grey));
    }

}