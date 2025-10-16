package com.example.e_cynic.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;
import com.example.e_cynic.activity.EditUserAddressActivity;
import com.example.e_cynic.activity.ViewUserAddress;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.utils.userInteraction.SnackbarCreator;

import java.util.List;

public class ViewAddressListAdapter extends RecyclerView.Adapter<ViewAddressListAdapter.MyViewHolder> {
    private Context context;
    private AppCompatActivity activity;
    private List<Address> addressList;

    public ViewAddressListAdapter(Context context, AppCompatActivity activity, List<Address> addressList) {
        this.context = context;
        this.activity = activity;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewAddressListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_edit_address_list, parent, false);
        return new ViewAddressListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAddressListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgBtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditUserAddressActivity.class);
                intent.putExtra("addressId", String.valueOf(addressList.get(position).addressId));
                activity.startActivityForResult(intent, RequestCode.EDIT_ADDRESS_ACTIVITY);
            }
        });
        holder.imgBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = AddressDatabase.removeAddressByAddressId(addressList.get(position).addressId);
                if(result == true) {
                    SnackbarCreator.createNewSnackbar(holder.imgBtn_delete, "Address has been deleted");
                    ((ViewUserAddress)activity).setUpRecyclerView();
                }
                else {
                    SnackbarCreator.createNewSnackbar(holder.imgBtn_delete, "Address deletion failed. Please try again");
                }
            }
        });

        holder.tv_address_id.setText(String.valueOf(addressList.get(position).addressId));
        holder.tv_address_index.setText(String.valueOf(position + 1));
        holder.tv_address_line1.setText(addressList.get(position).firstLine);
        if (!addressList.get(position).secondLine.equals("")) {
            holder.tv_address_line2.setText(addressList.get(position).secondLine);
        } else {
            holder.tv_address_line2.setVisibility(View.GONE);
        }
        if (!addressList.get(position).thirdLine.equals("")) {
            holder.tv_address_line3.setText(addressList.get(position).thirdLine);
        } else {
            holder.tv_address_line3.setVisibility(View.GONE);
        }
        holder.tv_address_postcode_city.setText(String.valueOf(addressList.get(position).postcode) + " " + addressList.get(position).city);
        holder.tv_address_state.setText(addressList.get(position).state);
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address_id;
        TextView tv_address_index;
        TextView tv_address_line1;
        TextView tv_address_line2;
        TextView tv_address_line3;
        TextView tv_address_postcode_city;
        TextView tv_address_state;

        ImageButton imgBtn_edit;
        ImageButton imgBtn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_address_id = itemView.findViewById(R.id.tv_address_id);
            tv_address_index = itemView.findViewById(R.id.tv_address_index);
            tv_address_line1 = itemView.findViewById(R.id.tv_address_line1);
            tv_address_line2 = itemView.findViewById(R.id.tv_address_line2);
            tv_address_line3 = itemView.findViewById(R.id.tv_address_line3);
            tv_address_postcode_city = itemView.findViewById(R.id.tv_address_postcode_city);
            tv_address_state = itemView.findViewById(R.id.tv_address_state);

            imgBtn_edit = itemView.findViewById(R.id.imgBtn_edit);
            imgBtn_delete = itemView.findViewById(R.id.imgBtn_delete);
        }
    }
}
