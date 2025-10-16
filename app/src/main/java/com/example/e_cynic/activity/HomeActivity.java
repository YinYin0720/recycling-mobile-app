package com.example.e_cynic.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_cynic.R;
import com.example.e_cynic.adapter.HomeArticleAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity
{
    // Views
    private ImageView info,iv_slideshow;
    private RecyclerView article;

    //article array
    private String s1[], s2[], s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setViewComponent();
        slideShow();
        articles();

        info.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HomeActivity.this, AppInfoActivity.class);
                startActivity(i);
            }
        });

        bottomNavBar();
    }

    private void setViewComponent()
    {
        info = findViewById(R.id.info);
        iv_slideshow = findViewById(R.id.slideshow);
        article = findViewById(R.id.ArticleView);
    }

    private void slideShow()
    {
        //homepage header slide show
        AnimationDrawable ad = (AnimationDrawable) iv_slideshow.getDrawable();
        ad.start();
    }

    private void articles()
    {
        //get resources from the array (string.xml)
        s1 = getResources().getStringArray(R.array.Articles);
        s2 = getResources().getStringArray (R.array.A_description);
        s3 = getResources().getStringArray(R.array.A_link);

        HomeArticleAdapter adapter = new HomeArticleAdapter(this, s1, s2, s3);
        article.setAdapter(adapter);
        article.setLayoutManager(new LinearLayoutManager(this));
    }

    public void bottomNavBar()
    {
        // Initiate & assign variable
        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // Set selected layout
        btmNav.setSelectedItemId(R.id.home);

        // Perform item selected listener
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home:
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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}