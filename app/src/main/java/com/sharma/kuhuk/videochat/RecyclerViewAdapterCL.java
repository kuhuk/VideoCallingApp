package com.sharma.kuhuk.videochat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterCL extends RecyclerView.Adapter<RecyclerViewAdapterCL.CLViewHolder> {

    private final ArrayList<String> arrayList;
    RecyclerViewAdapterCL(ArrayList<String> strings) {
        arrayList = strings;
    }

    //inner class - ViewHolder
    class CLViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivOnline;
        private TextView tvContactName;
        private Button btnInviteCall;

        CLViewHolder(View v) {
            super(v);

            ivOnline = v.findViewById(R.id.ivOnline);
            tvContactName = v.findViewById(R.id.tvContactName);
            btnInviteCall = v.findViewById(R.id.btnInviteCall);
        }
    }

    @NonNull
    @Override
    public CLViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View viewUD = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_cl, viewGroup, false);

        viewUD.setOnClickListener(FragmentHomeScreen.myOnClickListener);
        return new RecyclerViewAdapterCL.CLViewHolder(viewUD);
    }

    @Override
    public void onBindViewHolder(@NonNull CLViewHolder clViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
