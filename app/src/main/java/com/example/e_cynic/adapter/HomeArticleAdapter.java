package com.example.e_cynic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cynic.R;

public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleAdapter.MyViewHolder> {
    //retrieve the data from array
    private String[] d1, d2, d3;
    private Context context;

    public HomeArticleAdapter(Context ct, String[] s1, String[] s2, String[] s3) {
        context = ct;
        d1 = s1;
        d2 = s2;
        d3 = s3;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowarticle, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(d1[position]);
        holder.description.setText(d2[position]);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(d3[holder.getAdapterPosition()]));
                context.startActivity(intent);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(d3[holder.getAdapterPosition()]));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //item card
        return d1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_articleTitle);
            description = itemView.findViewById(R.id.tv_articleDescription);
        }
    }
}
