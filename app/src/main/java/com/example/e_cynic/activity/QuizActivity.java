package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.DateUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizActivity extends AppCompatActivity
{
    private Button playBtn;
    private TextView noOfChance;
    private String currentDate,date;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        sm = new SessionManager(getApplicationContext());

        setViewComponent();
        bottomNavBar();
        updateQuizChance();

        playBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sm.setCurrentDate(currentDate);
                updateQuizChance();
                Intent i = new Intent(QuizActivity.this,PlayQuizActivity.class);
                startActivityForResult(i, RequestCode.PLAY_QUIZ_ACTIVITY);
            }
        });
    }

    private void setViewComponent()
    {
        playBtn = findViewById(R.id.playBtn);
        noOfChance = findViewById(R.id.noOfChance);
    }

    private void updateQuizChance()
    {
        date = sm.getDate();
        currentDate = DateUtil.getCurrentDate();

        if (currentDate.equals(date))
        {
            playBtn.setEnabled(false);
            noOfChance.setText("0");
        }

        else
        {
            playBtn.setEnabled(true);
            noOfChance.setText("1");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.PLAY_QUIZ_ACTIVITY:
                break;
        }
    }

    public void bottomNavBar()
    {
        // Initiate & assign variable
        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // Set selected layout
        btmNav.setSelectedItemId(R.id.quiz);

        // Perform item selected listener
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.recycle:
                        startActivity(new Intent(getApplicationContext(), RecycleActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.quiz:
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