package com.example.e_cynic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.activity.OrderDetailActivity;
import com.example.e_cynic.db.ItemDatabase;
import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.entity.Order;
import com.example.e_cynic.utils.DateUtil;

import java.util.List;

public class HistoryOrderListAdapter extends RecyclerView.Adapter<HistoryOrderListAdapter.MyViewHolder>
{
    private Context context;
    private List<Order> historyOrders;
    private AppCompatActivity activity;

    public HistoryOrderListAdapter(Context context, List<Order> historyOrders, AppCompatActivity activity)
    {
        this.context = context;
        this.historyOrders = historyOrders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HistoryOrderListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_orders_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        int orderId = Integer.valueOf(historyOrders.get(position).orderId);
        String orderDateTime = DateUtil.getDateFromTimestamp(Long.valueOf(OrderDatabase.getOrderDateTimeByOrderId(orderId)));
        String currentDateTime = DateUtil.getCurrentDate();
        String duration = DateUtil.getDuration(currentDateTime,orderDateTime);

        if (Integer.parseInt(duration) >= 3)
        {
            // update order status to "collected"
            OrderDatabase.editOrderStatusByOrderId(orderId,"collected");
        }

        try
        {
            holder.orderImageList.setImageBitmap(ItemDatabase.getFirstItemImageByOrderId(historyOrders.get(position).orderId));
            holder.orderList.setText("Id: " + String.valueOf(historyOrders.get(position).orderId));
            holder.orderStatusList.setText(String.valueOf(historyOrders.get(position).status));
            holder.orderDateList.setText(String.valueOf(DateUtil.getDateTimeByTimestamp(historyOrders.get(position).date)));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        holder.orderCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int id = historyOrders.get(position).orderId;
                Intent orderDetail = new Intent(view.getContext(), OrderDetailActivity.class).putExtra("orderId",String.valueOf(id));
                activity.startActivity(orderDetail);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return historyOrders != null ? historyOrders.size():0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView orderImageList;
        TextView orderList,orderStatusList,orderDateList;
        CardView orderCard;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            orderCard = itemView.findViewById(R.id.orderCard);
            orderImageList = itemView.findViewById(R.id.orderImageList);
            orderList = itemView.findViewById(R.id.orderList);
            orderStatusList = itemView.findViewById(R.id.orderStatusList);
            orderDateList = itemView.findViewById(R.id.orderDateList);
        }
    }
}
