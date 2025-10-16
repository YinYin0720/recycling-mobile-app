package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.e_cynic.R;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.session.AppSharedPreferences;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

public class LoginActivity extends AppCompatActivity
{
    // views
    private Button loginBtn, signUpBtn, forgotPwd;
    private EditText username,password;

    // text of view
    private String usernameTxt,passwordTxt;

    // delay
    private int signUpDelay = 1000;

    // session manager
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        String loggedInUsername = "";
        try {
            loggedInUsername = AppSharedPreferences.getLoggedInUsername(LoginActivity.this) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!loggedInUsername.equals("")) {
            usernameTxt = loggedInUsername;
            loginUser();
            finish();
        }

        setViewComponent();

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateViewText();
                if (!fieldDataIsComplete())
                {
                    SnackbarCreator.createNewSnackbar(view,"Please enter all field.");
                }

                else
                {
                    boolean checkUserExistence = UserDatabase.checkUsernameExistence(usernameTxt);

                    if (checkUserExistence == false)
                    {
                        SnackbarCreator.createNewSnackbar(view,"Not an existing user, please sign up.");

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Intent signUp = new Intent(LoginActivity.this,SignUpActivity.class);
                                startActivity(signUp);
                            }
                        },signUpDelay);
                    }

                    else
                    {
                        boolean verify = UserDatabase.verifyUser(usernameTxt,passwordTxt);

                        if (verify == true)
                        {
                            loginUser();
                        }

                        else
                        {
                            SnackbarCreator.createNewSnackbar(view,"Invalid username or password!");
                        }
                    }
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        forgotPwd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser() {
        AppSharedPreferences.updateUser(usernameTxt);
        sm = new SessionManager(LoginActivity.this);

        //Store login in session
        sm.setLogin(true);

        // Store username
        sm.setUsername(usernameTxt);

        // Redirect to home page
        Intent homePage = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(homePage);
    }

    private void setViewComponent()
    {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgotPwd = findViewById(R.id.forgotPwd);
    }

    private void updateViewText()
    {
        usernameTxt = username.getText().toString();
        passwordTxt = password.getText().toString();
    }

    private boolean fieldDataIsComplete()
    {
        boolean complete = true;
        if(usernameTxt.equals("")) {
            setErrorField(username);
            complete = complete && false;
        }
        else {
            resetField(username);
        }
        if(passwordTxt.equals("")) {
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