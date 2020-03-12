package com.example.stormy.adapters;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stormy.databinding.HourlyListItemBinding;

public class hourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Binding Variables
        public HourlyListItemBinding hourlyListItemBinding;



        ///Construictors

        public ViewHolder(HourlyListItemBinding hourlyListItemBinding){
            super(hourlyLayoutBinding.getRoot());
        }

    }
}
