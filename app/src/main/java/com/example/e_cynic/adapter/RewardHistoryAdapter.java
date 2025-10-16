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
import com.example.e_cynic.entity.UserReward;
import com.example.e_cynic.entity.Voucher;
import com.example.e_cynic.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class RewardHistoryAdapter extends RecyclerView.Adapter<RewardHistoryAdapter.ViewHolder>
{
    private Context context;
    private List<UserReward> userRewardList;

    public RewardHistoryAdapter(Context context, List<UserReward> userRewardList)
    {
        this.context = context;
        this.userRewardList = userRewardList;
    }

    @NonNull
    @Override
    public RewardHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rewards_history_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardHistoryAdapter.ViewHolder holder, int position)
    {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(new Voucher(R.drawable.aeon));
        voucherList.add(new Voucher(R.drawable.tng_50));
        voucherList.add(new Voucher(R.drawable.shell));
        voucherList.add(new Voucher(R.drawable.baskin));
        voucherList.add(new Voucher(R.drawable.secret));
        voucherList.add(new Voucher(R.drawable.lazada));
        voucherList.add(new Voucher(R.drawable.tealive));
        voucherList.add(new Voucher(R.drawable.tng_10));
        voucherList.add(new Voucher(R.drawable.spca));
        voucherList.add(new Voucher(R.drawable.unicef));

        try
        {
            if (userRewardList != null)
            {
                switch (userRewardList.get(position).rewardItem)
                {
                    case "Aeon RM50 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(0).voucherImage);
                        break;

                    case "TNG RM50 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(1).voucherImage);
                        break;

                    case "Shell RM50 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(2).voucherImage);
                        break;

                    case "Baskin Robbins RM20 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(3).voucherImage);
                        break;

                    case "Secret Recipe RM20 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(4).voucherImage);
                        break;

                    case "Lazada RM10 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(5).voucherImage);
                        break;

                    case "Tealive RM10 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(6).voucherImage);
                        break;

                    case "TNG RM10 Cash Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(7).voucherImage);
                        break;

                    case "SPCA RM10 Donation Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(8).voucherImage);
                        break;

                    case "Unicef RM5 Donation Voucher":
                        holder.redeemImgList.setImageResource(voucherList.get(9).voucherImage);
                        break;
                }
            }

            holder.redeemNameList.setText(userRewardList.get(position).rewardItem);
            holder.redeemPointList.setText(String.valueOf(userRewardList.get(position).points));
            holder.redeemDateList.setText(String.valueOf(DateUtil.getDateTimeByTimestamp(userRewardList.get(position).date)));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return userRewardList != null ? userRewardList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView redeemImgList;
        TextView redeemNameList,redeemPointList,redeemDateList;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            redeemImgList = itemView.findViewById(R.id.redeemImgList);
            redeemNameList = itemView.findViewById(R.id.redeemNameList);
            redeemPointList = itemView.findViewById(R.id.redeemPointList);
            redeemDateList = itemView.findViewById(R.id.redeemDateList);
        }
    }
}
