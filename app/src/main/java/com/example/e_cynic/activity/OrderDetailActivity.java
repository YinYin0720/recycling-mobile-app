package com.example.e_cynic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.adapter.OrderDetailsAdapter;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.db.ItemDatabase;
import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.db.PointsDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.entity.Item;
import com.example.e_cynic.entity.Order;
import com.example.e_cynic.entity.Point;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.userInteraction.ToastCreator;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity
{
    private Intent intent;

    //Views
    private RecyclerView rv_itemList;
    private TextView tv_noOfDevice;
    private TextView tv_address;
    private TextView tv_point;
    private TextView tv_status;
    private ImageView backBtn;

    ToastCreator toastCreator = new ToastCreator();
    SessionManager sm;
    //data
    private Integer orderId; //to be passed by intent

    private Order order;
    private List<Item> itemList;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setViewComponents();
        intent = getIntent();

       sm = new SessionManager(getApplicationContext());

        orderId = Integer.valueOf(intent.getStringExtra("orderId"));

        try
        {
            order = OrderDatabase.getOrderByOrderId(orderId);
            itemList = ItemDatabase.getItemsByOrderId(orderId);

            address = AddressDatabase.getAddressByAddressId(order.addressId);

            if (itemList != null)
            {
                updateView();
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        updateStatus();
    }

    private void updateView() {
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(getApplicationContext(), itemList);
        rv_itemList.setAdapter(adapter);
        rv_itemList.setLayoutManager(new LinearLayoutManager(this));

        tv_noOfDevice.setText(String.valueOf(itemList.size()));
        tv_address.setText(address.getAddressString());
        String status = OrderDatabase.getOrderStatusByOrderId(orderId);
        tv_status.setText(status != "" ? status : "Order not available");
        Integer points = ItemDatabase.getTotalPointByOrderId(orderId);
        tv_point.setText(status != "" ? (points >= 0 ? String.valueOf(points) : "To be confirmed") : "Order not available");
    }

    private void updateStatus()
    {
        orderId = Integer.valueOf(intent.getStringExtra("orderId"));
        String orderDateTime = DateUtil.getDateFromTimestamp(Long.valueOf(OrderDatabase.getOrderDateTimeByOrderId(orderId)));
        String currentDateTime = DateUtil.getCurrentDate();
        String duration = DateUtil.getDuration(currentDateTime,orderDateTime);

        if (Integer.parseInt(duration) >= 3)
        {
            // update point in item db
            for (int i=0; i<itemList.size(); i++)
            {
                ItemDatabase.editItemPointsByItemId(itemList.get(i).itemId,50);
            }

            // get total points of order
            int totalPoints = (itemList.size()*50);

            // insert total points into point db
            insertPointsIntoDb(totalPoints);

            // update points in sp
            int tp = sm.getTotalPoints();;
            tp += totalPoints;
            sm.setTotalPoints(tp);

            // display in textview
            tv_point.setText(String.valueOf(totalPoints));
            tv_status.setText("collected");
        }
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

    private void setViewComponents()
    {
        rv_itemList = findViewById(R.id.rv_itemList);
        tv_noOfDevice = findViewById(R.id.noOfDevice);
        tv_address = findViewById(R.id.pinnedAddress);
        tv_status = findViewById(R.id.status);
        tv_point = findViewById(R.id.point);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}