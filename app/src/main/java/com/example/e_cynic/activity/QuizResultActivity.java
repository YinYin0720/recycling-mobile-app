package com.example.e_cynic.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.db.PointsDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.Point;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.userInteraction.ToastCreator;

public class QuizResultActivity extends AppCompatActivity
{
    private TextView scoreValue, tp;
    private Button returnBtn;
    private int totalPoints;

    // session manager
    private SessionManager sm;
    private ToastCreator toastCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);

        setViewComponent();
        setUpClass();

        int score = getIntent().getIntExtra("CorrectAns",0);
        scoreValue.setText(score + " / 5");

        returnBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        // update text view of total score
        // one score represent 3 points
        int p = score * 3;
        tp.setText(String.valueOf(p));

        // Store total points into session manager
        totalPoints = sm.getTotalPoints();;
        totalPoints += (score * 3);
        sm.setTotalPoints(totalPoints);

        insertPointsIntoDb(p);
    }

    private void setViewComponent()
    {
        scoreValue = findViewById(R.id.scoreValue);
        returnBtn = findViewById(R.id.returnBtn);
        tp = findViewById(R.id.totalPoints);
    }

    private void setUpClass()
    {
        sm = new SessionManager(getApplicationContext());
        toastCreator = new ToastCreator();
    }

    private void insertPointsIntoDb(int p)
    {
        int userId = UserDatabase.getUserIdByUsername(sm.getUsername());
        Long date = DateUtil.getCurrentTimestamp();
        Point point = new Point(null,userId,p,date);

        boolean insertPoints = false;

        try
        {
            insertPoints = PointsDatabase.insertPoint(point);
        }

        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        if (insertPoints == true)
        {
            toastCreator.createToast(this,"Store points successfully!");
        }

        else
        {
            toastCreator.createToast(this,"Fail to store points");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}