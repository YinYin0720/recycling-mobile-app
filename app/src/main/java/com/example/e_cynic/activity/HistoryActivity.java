package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.adapter.HistoryOrderListAdapter;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.entity.Order;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.comparator.OrderComparator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity
{

    private RecyclerView historyRecyclerView;
    private HistoryOrderListAdapter historyOrderListAdapter;
    private String[] itemInSortList;
    private Spinner sortList;
    private LinearLayout LL_recycleHistory;
    private LinearLayout LL_noRecycleHistory;

    private SessionManager sessionManager;

    // items list
    List<Order> historyOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        sessionManager = new SessionManager(this);

        setViewComponent();
        setSortList();

        try
        {
            storeDataIntoList();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        setUpLinearLayout();
        setUpRecyclerView();

        if(historyOrders != null) {

            LL_noRecycleHistory.setVisibility(View.GONE);
            LL_recycleHistory.setVisibility(View.VISIBLE);
            sortList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    int index = adapterView.getSelectedItemPosition();

                    switch (index)
                    {
                        case 0:
                            historyOrders.sort(OrderComparator.NewestOrder);
                            historyOrderListAdapter.notifyDataSetChanged();
                            break;

                        case 1:
                            historyOrders.sort(OrderComparator.OldestOrder);
                            historyOrderListAdapter.notifyDataSetChanged();
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        else {
            LL_recycleHistory.setVisibility(View.GONE);
            LL_noRecycleHistory.setVisibility(View.VISIBLE);

            findViewById(R.id.btn_goRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HistoryActivity.this, RecycleActivity.class);
                    startActivity(intent);
                }
            });
        }

        bottomNavBar();
    }

    private void setViewComponent()
    {
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        sortList = (Spinner) findViewById(R.id.sortOrders);
    }

    private void setSortList()
    {
        //Sort drop down list
        itemInSortList = getResources().getStringArray(R.array.sortListEg);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HistoryActivity.this,
                android.R.layout.simple_list_item_1, itemInSortList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortList.setAdapter(myAdapter); // show data
    }

    private void storeDataIntoList() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        historyOrders = OrderDatabase.getOrdersByUsername(sessionManager.getUsername());
    }

    private void setUpRecyclerView()
    {
        historyOrderListAdapter = new HistoryOrderListAdapter(getApplicationContext(),historyOrders,HistoryActivity.this);
        historyRecyclerView.setAdapter(historyOrderListAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    private void setUpLinearLayout() {
        LL_recycleHistory = findViewById(R.id.LL_recycleHistory);
        LL_noRecycleHistory = findViewById(R.id.LL_noRecycleHistory);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case RequestCode.VIEW_ORDER_DETAILS_ACTIVITY:
                onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void bottomNavBar()
    {
        // Initiate & assign variable
        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // Set selected layout
        btmNav.setSelectedItemId(R.id.history);

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
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.history:
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