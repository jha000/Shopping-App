package com.fresh.milkaggregatorapplication;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<User> list;
    private Context context;


    public MyAdapter(Context context, ArrayList<User> list) {

        this.context = context;

        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.courseNameTV.setText(user.getCourseName());
        holder.courseDescTV.setText(user.getCourseDescription());
        holder.courseDurationTV.setText(user.getCourseDuration());
        holder.name.setText(user.getNamett());

        holder.mobile.setText(user.getPhonett());

        holder.address.setText(user.getAddresstt());


        String pin3 = "FreshYard Country Eggs - Pack Of 6";
        String pin4 = "FreshYard Poultry Eggs - Pack Of 6";

        if (user.getCourseName().equals(pin3)) {
            holder.productimage.setImageResource(R.drawable.country);
        } else if (user.getCourseName().equals(pin4)) {
            holder.productimage.setImageResource(R.drawable.protein);
        }

        int total = Integer.parseInt(user.getCourseDuration());
        int qty = Integer.parseInt(user.getCourseDescription());

        holder.grand.setText(String.valueOf(total + 1.80));
        holder.pricex.setText(String.valueOf(total / qty));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), orderDetails.class);
                intent.putExtra("title", user.getCourseName());
                intent.putExtra("qty", user.getCourseDescription());
                intent.putExtra("price", user.getCourseDuration());
                view.getContext().startActivity(intent);
            }
        });












    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void searchDataList(ArrayList<User> searchList){
        list = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView courseDescTV,courseDurationTV,courseNameTV,name,mobile,address, pricex, grand ;
        ImageView productimage;
        CardView card;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            courseDescTV = itemView.findViewById(R.id.tvfirstName);

            courseDurationTV = itemView.findViewById(R.id.tvage);

            courseNameTV = itemView.findViewById(R.id.nameid);

            name = itemView.findViewById(R.id.nameget);

            mobile = itemView.findViewById(R.id.mobileget);
            address = itemView.findViewById(R.id.addressget);

            pricex = itemView.findViewById(R.id.pricex);

            grand = itemView.findViewById(R.id.grand);
            productimage = itemView.findViewById(R.id.productimage);

        }
    }

}
