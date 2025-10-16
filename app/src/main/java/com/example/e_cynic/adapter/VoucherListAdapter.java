package com.example.e_cynic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_cynic.R;
import com.example.e_cynic.activity.RedeemPointsActivity;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.db.UserRewardDatabase;
import com.example.e_cynic.entity.UserReward;
import com.example.e_cynic.entity.Voucher;
import com.example.e_cynic.session.SessionManager;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.userInteraction.ToastCreator;
import java.util.List;

public class VoucherListAdapter extends RecyclerView.Adapter<VoucherListAdapter.MyViewHolder>
{
    private Context context;
    private AppCompatActivity activity;
    private List<Voucher> voucherList;
    private SessionManager sm;

    public VoucherListAdapter(Context context, AppCompatActivity activity, List<Voucher> voucherList)
    {
        this.context = context;
        this.activity = activity;
        this.voucherList = voucherList;
        sm = new SessionManager(context);
    }

    @NonNull
    @Override
    public VoucherListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.voucher_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.voucherImgList.setImageResource(voucherList.get(position).voucherImage);
        holder.voucherNameList.setText(voucherList.get(position).voucherName);
        holder.voucherPointList.setText(String.valueOf(voucherList.get(position).voucherPoints));

        holder.redeemBtnList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int p = voucherList.get(position).voucherPoints;
                String name = voucherList.get(position).voucherName;
                ToastCreator toastCreator = new ToastCreator();

                if (sm.getTotalPoints() < p)
                {
                    toastCreator.createToast(context,"Not enough points.");
                }

                else
                {
                    int userId = UserDatabase.getUserIdByUsername(sm.getUsername());
                    Long date = DateUtil.getCurrentTimestamp();

                    UserReward userReward = new UserReward(null,userId,date,name,p);

                    boolean insertReward = false;

                    try
                    {
                        insertReward = UserRewardDatabase.insertUserReward(userReward);
                    }

                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }

                    if (insertReward == true)
                    {
                        toastCreator.createToast(context,"Redeem successfully!");
                        sm.setTotalPoints((sm.getTotalPoints()-p));
                        ((RedeemPointsActivity)activity).setAvailablePoints();
                    }

                    else
                    {
                        toastCreator.createToast(context,"Fail to redeem, please try again.");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return voucherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView voucherImgList;
        TextView voucherNameList,voucherPointList;
        Button redeemBtnList;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            voucherImgList = itemView.findViewById(R.id.voucherImgList);
            voucherNameList = itemView.findViewById(R.id.voucherNameList);
            voucherPointList = itemView.findViewById(R.id.voucherPointList);
            redeemBtnList = itemView.findViewById(R.id.redeemBtnList);
        }
    }
}
