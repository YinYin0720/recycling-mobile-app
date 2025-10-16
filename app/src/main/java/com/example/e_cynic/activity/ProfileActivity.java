package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.User;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private Button about, editProfile, btn_addresses;
    private ImageView redeemPointsBtn, myRewardBtn, logoutBtn;
    private TextView profileUname, profileEmail, profilePhone;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        sm = new SessionManager(getApplicationContext());

        setViewComponent();
        setUpProfileText();
        bottomNavBar();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(loginPage);
                sm.setLogin(false);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(i, RequestCode.EDIT_PROFILE_ACTIVITY);
            }
        });

        redeemPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, RedeemPointsActivity.class);
                startActivity(i);
            }
        });

        btn_addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ViewUserAddress.class);
                startActivity(i);
            }
        });

        myRewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, RewardHistoryActivity.class);
                startActivity(i);
            }
        });
    }

    private void setViewComponent() {
        redeemPointsBtn = findViewById(R.id.redeemPointsBtn);
        myRewardBtn = findViewById(R.id.myRewardBtn);
        about = findViewById(R.id.aboutBtn);
        editProfile = findViewById(R.id.editBtn);
        btn_addresses = findViewById(R.id.btn_addresses);
        logoutBtn = findViewById(R.id.logoutBtn);
        profileUname = findViewById(R.id.profileUname);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
    }

    public void setUpProfileText() {
        String uname = sm.getUsername();
        User user = new User();

        try {
            user = UserDatabase.getUserInfoByUsername(uname);
        } catch (Exception e) {
            e.printStackTrace();
        }

        profileUname.setText(uname);
        profileEmail.setText(user.email);
        profilePhone.setText(user.phoneNumber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.EDIT_PROFILE_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    SnackbarCreator.createNewSnackbar(editProfile, "Profile has been updated");
                    setUpProfileText();
                }
                else if (resultCode == RESULT_CANCELED) {
                    SnackbarCreator.createNewSnackbar(editProfile, "Profile was not updated");
                }
        }
    }

    public void bottomNavBar() {
        // Initiate & assign variable
        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // Set selected layout
        btmNav.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(), RecycleActivity.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }
                return false;
            }
        });
    }
}