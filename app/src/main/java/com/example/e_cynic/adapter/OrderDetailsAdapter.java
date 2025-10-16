package com.example.e_cynic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.entity.Item;
import com.example.e_cynic.utils.ImageUtil;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    private Context context;
    private List<Item> itemList;

    private boolean orderInProcess;

    public OrderDetailsAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.orderInProcess = (OrderDatabase.getOrderStatusByOrderId(itemList.get(0).orderId)).toLowerCase().equals("processing");
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_details_list, parent, false);
        return new OrderDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.MyViewHolder holder, int position) {
        holder.tv_itemIndex.setText(String.valueOf(position + 1));
        holder.tv_typeOfAppliances.setText(itemList.get(position).itemName);
        holder.tv_itemPoint.setText(orderInProcess ? "Points: To be confirmed" :
                String.valueOf(itemList.get(position).point) + " Points");
        holder.iv_appliancesImg.setImageBitmap(ImageUtil.byteArrayToBitmap(itemList.get(position).image));
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemIndex;
        TextView tv_typeOfAppliances;
        TextView tv_itemPoint;
        ImageView iv_appliancesImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_itemIndex = itemView.findViewById(R.id.tv_itemIndex);
            tv_typeOfAppliances = itemView.findViewById(R.id.tv_typeofAppliances);
            tv_itemPoint = itemView.findViewById(R.id.tv_item_point);
            iv_appliancesImg = itemView.findViewById(R.id.iv_appliancesImg);
        }
    }
}
