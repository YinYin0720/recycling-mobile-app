package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_cynic.R;
import com.example.e_cynic.adapter.RewardHistoryAdapter;
import com.example.e_cynic.db.UserRewardDatabase;
import com.example.e_cynic.entity.UserReward;
import com.example.e_cynic.session.SessionManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RewardHistoryActivity extends AppCompatActivity
{

    private ImageView backBtn;
    private RecyclerView rewardsRecyclerView;
    private RewardHistoryAdapter rewardHistoryAdapter;
    private List<UserReward> userRewardList = new ArrayList<>();
    private LinearLayout LL_rewardHistory;
    private LinearLayout LL_noRewardHistory;
    private SessionManager sm;
    private Button redeemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward);

        setViewComponent();
        sm = new SessionManager(getApplicationContext());

        try
        {
            storeRewardDataIntoList();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        setUpLinearLayout();
        setUpAdapter();

        if (userRewardList != null)
        {
            LL_noRewardHistory.setVisibility(View.GONE);
            LL_rewardHistory.setVisibility(View.VISIBLE);
        }

        else
        {
            LL_noRewardHistory.setVisibility(View.VISIBLE);
            LL_rewardHistory.setVisibility(View.GONE);

            redeemBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent redeemPage = new Intent(RewardHistoryActivity.this,RedeemPointsActivity.class);
                    startActivity(redeemPage);
                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(RewardHistoryActivity.this,
                        ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void setViewComponent()
    {
        backBtn = findViewById(R.id.backBtn);
        rewardsRecyclerView = findViewById(R.id.rewardsRecyclerView);
        redeemBtn = findViewById(R.id.redeemBtn);
    }

    private void storeRewardDataIntoList() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        String uname = sm.getUsername();
        userRewardList = UserRewardDatabase.getUserRewardsByUsername(uname);
    }

    private void setUpAdapter()
    {
        rewardHistoryAdapter = new RewardHistoryAdapter(getApplicationContext(),userRewardList);
        rewardsRecyclerView.setAdapter(rewardHistoryAdapter);
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(RewardHistoryActivity.this));
    }

    private void setUpLinearLayout()
    {
        LL_rewardHistory = findViewById(R.id.LL_RedeemHistory);
        LL_noRewardHistory = findViewById(R.id.LL_noRedeemHistory);
    }
}